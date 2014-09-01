package com.skcraft.smes.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cofh.lib.gui.slot.SlotRemoveOnly;

import com.skcraft.smes.inventory.container.slot.SlotMultipleValid;
import com.skcraft.smes.inventory.container.slot.validators.ValidatorOreDictionary;
import com.skcraft.smes.tileentity.TileEntityRareMetalExtractor;

public class ContainerRareMetalExtractor extends Container {
    private TileEntityRareMetalExtractor tileEntity;
    
    public ContainerRareMetalExtractor(TileEntityRareMetalExtractor tileEntity, InventoryPlayer inventory) {
        this.tileEntity = tileEntity;
        this.addSlotToContainer(new SlotMultipleValid(inventory, 0, 10, 10, false)
                                .addValidator(new ValidatorOreDictionary(new ItemStack(Blocks.sapling)))
                                .addValidator(new ValidatorOreDictionary(new ItemStack(Blocks.cobblestone))));
        this.addSlotToContainer(new SlotRemoveOnly(inventory, 1, 20, 20));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }
    
    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex < 9) {
                if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
