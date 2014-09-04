package com.skcraft.smes.inventory.container.slot.validators;

import net.minecraft.item.ItemStack;
import cofh.lib.gui.slot.ISlotValidator;

public class SlotValidatorItemStack implements ISlotValidator {
    private final ItemStack itemStack;
    
    public SlotValidatorItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return this.itemStack.isItemEqual(itemStack);
    }

}
