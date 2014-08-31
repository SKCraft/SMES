package com.skcraft.smes.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityEnergyInventory extends TileEntityEnergy implements IInventory, ISidedInventory {
    private ItemStack[] inventory;
    
    public TileEntityEnergyInventory(int energyCapacity, int slots) {
        super(energyCapacity);
        this.inventory = new ItemStack[slots];
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length) {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        this.getBattery().readFromNBT(tagCompound.getCompoundTag("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < this.inventory.length; i++) {
            ItemStack stack = this.inventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("Inventory", itemList);
        tagCompound.setTag("Energy", this.getBattery().writeToNBT(new NBTTagCompound()));
    }
    
    protected ItemStack[] getInventory() {
        return this.inventory;
    }

    /* IInventory */
    
    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = this.getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                this.setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0) {
                    this.setInventorySlotContents(slot, null);
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack itemStack = this.getStackInSlot(slot);
        if (itemStack != null) {
            this.setInventorySlotContents(slot, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.inventory[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 64;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return true;
    }

    @Override
    public abstract String getInventoryName();

    @Override
    public abstract boolean hasCustomInventoryName();

    @Override
    public void openInventory() {};

    @Override
    public void closeInventory() {};

    /* ISidedInventory */
    
    @Override
    public abstract int[] getAccessibleSlotsFromSide(int side);

    @Override
    public abstract boolean canInsertItem(int slot, ItemStack item, int side);

    @Override
    public abstract boolean canExtractItem(int slot, ItemStack item, int side);

}
