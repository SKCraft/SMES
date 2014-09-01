package com.skcraft.smes.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.gui.slot.SlotEnergy;
import cofh.lib.gui.slot.SlotRemoveOnly;

import com.skcraft.smes.inventory.container.slot.SlotMultipleValid;
import com.skcraft.smes.inventory.container.slot.validators.ValidatorOreDictionary;
import com.skcraft.smes.recipes.RareMetalExtractorRecipes;
import com.skcraft.smes.tileentity.TileEntityRareMetalExtractor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerRareMetalExtractor extends Container {
    private TileEntityRareMetalExtractor tileEntity;
    
    public ContainerRareMetalExtractor(TileEntityRareMetalExtractor tileEntity, InventoryPlayer inventory) {
        this.tileEntity = tileEntity;
        // Input slot
        this.addSlotToContainer(new SlotMultipleValid(tileEntity, 0, 49, 33, false)
                                .addValidator(new ValidatorOreDictionary(new ItemStack(Blocks.sapling)))
                                .addValidator(new ValidatorOreDictionary(new ItemStack(Blocks.cobblestone))));
        // Output slot
        this.addSlotToContainer(new SlotRemoveOnly(tileEntity, 1, 109, 33));
        
        // Energy input slot
        this.addSlotToContainer(new SlotEnergy(tileEntity, 2, 8, 53));
        
        this.addPlayerInventory(inventory, 8, 84);
    }
    
    private void addPlayerInventory(InventoryPlayer inventory, int xOffset, int yOffset) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOffset + j * 18, yOffset + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventory, i, xOffset + i * 18, yOffset + 58));
        }
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        int energy = this.tileEntity.getEnergyStored(ForgeDirection.UNKNOWN);

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);

            crafting.sendProgressBarUpdate(this, 0, this.tileEntity.getCurrentRequiredEnergy());
            crafting.sendProgressBarUpdate(this, 1, this.tileEntity.getProcessingEnergy());
            crafting.sendProgressBarUpdate(this, 2, energy);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int barId, int value) {
        switch (barId) {
        case 0:
            this.tileEntity.setCurrentRequiredEnergy(value);
            break;
        case 1:
            this.tileEntity.setProcessingEnergy(value);
            break;
        case 2:
            this.tileEntity.setEnergyStored(value);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack itemStack = null;
        Slot slotObject = (Slot) this.inventorySlots.get(slot);

        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            itemStack = stackInSlot.copy();

            if (slot <= 2) {
                if (!this.mergeItemStack(stackInSlot, 3, 39, true)) {
                    return null;
                }
            } else {
                if (RareMetalExtractorRecipes.isValidInput(stackInSlot)) {
                    if (!this.mergeItemStack(stackInSlot, 0, 1, false)) {
                        return null;
                    }
                } else if (stackInSlot.getItem() instanceof IEnergyContainerItem) {
                    if (!this.mergeItemStack(stackInSlot, 2, 3, false)) {
                        return null;
                    }
                }
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == itemStack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return itemStack;
    }
}
