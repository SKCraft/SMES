package com.skcraft.smes.inventory.container.slot.validators;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.inventory.ComparableItemStack;

public class ValidatorOreDictionary implements ISlotValidator {
    private final ComparableItemStack itemStack;
    private ComparableItemStack query = new ComparableItemStack(new ItemStack(Blocks.stone));
    
    public ValidatorOreDictionary(ItemStack itemStack) {
        this.itemStack = new ComparableItemStack(itemStack);
    }
    
    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return this.itemStack.isItemEqual(query.set(itemStack));
    }

}
