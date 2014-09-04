package com.skcraft.smes.tileentity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.tileentity.IReconfigurableSides;
import cofh.api.transport.IItemDuct;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.ItemHelper;

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
    
    private int processedItem = 0;
    private ItemStack currentlyProcessed;
    
    public TileEntityRareMetalExtractor() {
        super(batteryCapacity, 3);
        this.resetSides();
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
            outputResult();
        }
    }
    
    private void outputResult() {
        // Check all sides if output side
        for (int i = 0; i < 6; i++) {
            if (this.getSideConfig(i) == 2) {
                // Grab the adjecent tile at the output side
                TileEntity adjecent = BlockHelper.getAdjacentTileEntity(this, i);
                
                // Check if the adjecent tile entity is an inventory (IIventory, ISidedInventory or IItemDuct)
                if (adjecent instanceof IInventory || adjecent instanceof ISidedInventory || adjecent instanceof IItemDuct) {
                    // Try to output into the inventory
                    if (InventoryHelper.isInsertion(adjecent)) {
                        ItemStack notInserted = InventoryHelper.addToInsertion(adjecent, i, this.getInventory()[1]);
                        this.getInventory()[1] = notInserted;
                    }
                }
            }
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        this.currentEnergyRequired = tagCompound.getInteger("EnergyRequired");
        this.processingEnergy = tagCompound.getInteger("ProcessingEnergy");
        this.processedItem = tagCompound.getInteger("ProcessedItem");
        this.currentlyProcessed = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("CurrentlyProcessed"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("EnergyRequired", this.currentEnergyRequired);
        tagCompound.setInteger("ProcessingEnergy", this.processingEnergy);
        tagCompound.setInteger("ProcessedItem", this.processedItem);
        if (this.currentlyProcessed != null) {
            NBTTagCompound itemStackTagCompound = new NBTTagCompound();
            this.currentlyProcessed.writeToNBT(itemStackTagCompound);
            tagCompound.setTag("CurrentlyProcessed", itemStackTagCompound);
        }
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
            // If no result exists for the current input return false
            ItemStack result = RareMetalExtractorRecipes.getOutput(this.getInventory()[0]);
            if (result == null) {
                return false;
            }
            
            // If the current item being processed is 0 set the current item being processed to the item in the input slot
            if (this.processedItem == 0 || this.currentlyProcessed == null) {
                this.currentlyProcessed = this.getInventory()[0];
            } else if (!ItemHelper.isOreNameEqual(this.getInventory()[0], ItemHelper.getOreName(this.currentlyProcessed))) {
               // Ore dictionary check on the item vs the item being processed to allow other items of same ore dict to be processed
               return false;
            }
           
            // Add the stacksize to the amount already processed and remove the itemstack
            this.processedItem += this.getInventory()[0].stackSize;
            this.getInventory()[0] = null;
        }
        
        if (this.currentlyProcessed != null) {
            // If no result exists for the current input return false
            ItemStack result = RareMetalExtractorRecipes.getOutput(this.currentlyProcessed);
            if (result == null) {
                return false;
            }
            
            // If there isn't enough processed yet return false
            if (processedItem < RareMetalExtractorRecipes.getInputSize(this.currentlyProcessed)) {
                return false;
            }
            
            // Check if there is space to process
            if (this.getInventory()[1] != null) {
                if ((this.getInventory()[1].stackSize + result.stackSize) > this.getInventoryStackLimit()
                  || !ItemHelper.isOreNameEqual(this.getInventory()[1], ItemHelper.getOreName(result))) {
                    return false;
                }
            }
            // Everything is okay! Return true
            return true;
        }
        return false;
    }
    
    private void processItem() {
        if (this.canProcess()) {
            ItemStack result = RareMetalExtractorRecipes.getOutput(this.currentlyProcessed);
            if (this.getInventory()[1] == null) {
                this.getInventory()[1] = result.copy();
            } else if (ItemHelper.isOreNameEqual(this.getInventory()[1], ItemHelper.getOreName(result))) {
                this.getInventory()[1].stackSize += result.stackSize;
            }

            this.processedItem -= RareMetalExtractorRecipes.getInputSize(this.currentlyProcessed);

            this.processingEnergy = -1;
            if (this.processedItem <= 0) {
                this.currentlyProcessed = null;
            }
        }
    }
    
    private int getEnergyRequired() {
        if (this.currentlyProcessed != null) {
            return RareMetalExtractorRecipes.getEnergyRequired(this.currentlyProcessed);
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
    
    public int getSideConfig(int side) {
        return this.sideSlots[side];
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
     * Can return the slot configuration - 1 as this'd reflect the slot id
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        int sideConfig = sideSlots[side];
        switch(sideConfig) {
        case 0:
            return new int[] {};
        case 1:
            // Input slot
            return new int[] { 0 };
        case 2:
            // Output slot
            return new int[] { 1 };
        default:
            return new int[] {};
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return sideSlots[side] == 1 && this.getContainer(null).getSlot(0).isItemValid(item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return sideSlots[side] == 2;
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
        this.sideSlots[BlockHelper.getRightSide(getFacing().ordinal())] = 1;
        this.sideSlots[BlockHelper.getLeftSide(getFacing().ordinal())] = 2;
        return true;
    }

    /**
     * Returns the number of possible config settings for a given side.
     * 
     * In this case the following options are available for all sides:
     * 0: None
     * 1: Input
     * 2: Output
     */
    @Override
    public int getNumConfig(int side) {
        return 3;
    }
}
