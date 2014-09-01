package com.skcraft.smes.tileentity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.tileentity.IReconfigurableSides;

import com.skcraft.smes.client.gui.GuiRareMetalExtractor;
import com.skcraft.smes.inventory.container.ContainerRareMetalExtractor;
import com.skcraft.smes.recipes.RareMetalExtractorRecipes;
import com.skcraft.smes.util.StringUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityRareMetalExtractor extends TileEntityEnergyInventory implements IReconfigurableSides {
    private static final int batteryCapacity = 1400000;
    private int[] sideSlots = new int[6];
    
    private int currentEnergyRequired = 0;
    private int processingEnergy = -1;
    private int energyPerTick = 1000;
    
    public TileEntityRareMetalExtractor() {
        super(batteryCapacity, 3);
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();

        boolean invChanged = false;

        if (!this.worldObj.isRemote) {
            if (this.canProcess()) {
                if (this.processingEnergy < 0) {
                    this.currentEnergyRequired = this.getEnergyRequired();
                    this.processingEnergy = this.currentEnergyRequired;
                }
                this.processingEnergy -= Math.max(this.getBattery().extractEnergy(this.energyPerTick, false), 0);
                if (this.processingEnergy <= 0) {
                    this.processItem();
                    invChanged = true;
                }
            } else {
                this.processingEnergy = -1;
            }
            
            if (this.getInventory()[2] != null && this.getInventory()[2].getItem() instanceof IEnergyContainerItem) {
                int maxExtract = this.getBattery().getMaxEnergyStored() - this.getBattery().getEnergyStored();
                int energy = ((IEnergyContainerItem) this.getInventory()[2].getItem()).extractEnergy(this.getInventory()[2], maxExtract, false);
                this.getBattery().receiveEnergy(energy, false);
            }
        }

        if (invChanged) {
            this.markDirty();
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        this.currentEnergyRequired = tagCompound.getInteger("EnergyRequired");
        this.processingEnergy = tagCompound.getInteger("ProcessingEnergy");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("EnergyRequired", this.currentEnergyRequired);
        tagCompound.setInteger("ProcessingEnergy", this.processingEnergy);
    }
    
    @Override
    public boolean isActive() {
        return this.processingEnergy >= 0;
    }

    @Override
    public Container getContainer(InventoryPlayer invPlayer) {
        return new ContainerRareMetalExtractor(this, invPlayer);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer getGui(InventoryPlayer invPlayer) {
        return new GuiRareMetalExtractor(this, invPlayer);
    }
    
    private boolean canProcess() {
        if (this.getInventory()[0] != null) {
            ItemStack result = RareMetalExtractorRecipes.getOutput(this.getInventory()[0]);
            if (result == null || this.getInventory()[0].stackSize < RareMetalExtractorRecipes.getInputSize(this.getInventory()[0])) {
                return false;
            }
            if (this.getInventory()[1] == null) {
                return true;
            } else if (this.getInventory()[1].isItemEqual(result)) {
                int resultAmount = this.getInventory()[1].stackSize + result.stackSize;
                return resultAmount <= this.getInventoryStackLimit();
            }
        }
        return false;
    }
    
    private void processItem() {
        if (this.canProcess()) {
            ItemStack result = RareMetalExtractorRecipes.getOutput(this.getInventory()[0]);
            if (this.getInventory()[1] == null) {
                this.getInventory()[1] = result.copy();
            } else if (this.getInventory()[1].isItemEqual(result)) {
                this.getInventory()[1].stackSize += result.stackSize;
            }

            this.getInventory()[0].stackSize -= RareMetalExtractorRecipes.getInputSize(this.getInventory()[0]);

            if (this.getInventory()[0].stackSize <= 0) {
                this.getInventory()[0] = null;
            }

            this.processingEnergy = -1;
        }
    }
    
    private int getEnergyRequired() {
        if (this.getInventory()[0] != null) {
            return RareMetalExtractorRecipes.getEnergyRequired(this.getInventory()[0]);
        }
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getProcessingEnergyScaled(int scale) {
        return this.processingEnergy >= 0 ? (int) Math.floor(((double) this.currentEnergyRequired - (double) this.processingEnergy) / this.currentEnergyRequired * scale) : 0;
    }
    
    @Override
    public String getInventoryName() {
        return StringUtils.translate("gui.rareMetalExtractor.name", true);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    
    public int getCurrentRequiredEnergy() {
        return this.currentEnergyRequired;
    }
    
    public int getProcessingEnergy() {
        return this.processingEnergy;
    }
    
    public void setProcessingEnergy(int energy) {
        this.processingEnergy = energy;
    }
    
    public void setCurrentRequiredEnergy(int energy) {
        this.currentEnergyRequired = energy;
    }
    
    /* ISidedInventory */

    /**
     * Return the slot indeces available from the given side.
     * Can return the slot configuration as these reflect the slots
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { sideSlots[side] };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return sideSlots[slot] == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return sideSlots[slot] == 1;
    }

    /* IReconfigurableSides */
    
    @Override
    public boolean decrSide(int side) {
        if (this.sideSlots[side] > 1) {
            this.sideSlots[side]--;
        } else if (this.sideSlots[side] == 0) {
            this.sideSlots[side] = this.getNumConfig(side);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean incrSide(int side) {
        if (this.sideSlots[side] < this.getNumConfig(side) - 1) {
            this.sideSlots[side]++;
        } else if (this.sideSlots[side] == this.getNumConfig(side)) {
            this.sideSlots[side] = 0;
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean setSide(int side, int config) {
        if (config < this.getNumConfig(side)) {
            this.sideSlots[side] = config;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reset configs on all sides to their base values.
     * 
     * Base values:
     * West of facing is input,
     * East of facing is output
     * 
     * @return True if reset was successful, false otherwise.
     */
    @Override
    public boolean resetSides() {
        this.sideSlots[getFacing().getRotation(ForgeDirection.WEST).ordinal()] = 0;
        this.sideSlots[getFacing().getRotation(ForgeDirection.EAST).ordinal()] = 1;
        return true;
    }

    /**
     * Returns the number of possible config settings for a given side.
     * 
     * In this case the following options are available for all sides:
     * 0: Input
     * 1: Output
     */
    @Override
    public int getNumConfig(int side) {
        return 2;
    }
}
