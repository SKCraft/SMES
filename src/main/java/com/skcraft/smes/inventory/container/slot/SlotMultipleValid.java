package com.skcraft.smes.inventory.container.slot;

import java.util.ArrayList;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cofh.lib.gui.slot.ISlotValidator;
import cofh.lib.gui.slot.SlotValidated;

public class SlotMultipleValid extends SlotValidated {
    private ArrayList<ISlotValidator> validators = new ArrayList<ISlotValidator>();
    private boolean and = false;
    
    public SlotMultipleValid(IInventory inventory, int index, int x, int y, boolean and) {
        super(null, inventory, index, x, y);
        this.and = and;
    }
    
    public SlotMultipleValid addValidator(ISlotValidator validator) {
        if (validator != null) {
            this.validators.add(validator);
        }
        return this;
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        for(ISlotValidator validator : this.validators) {
            if (validator.isItemValid(stack)) {
                if (!and) {
                    return true;
                }
            } else if (this.and) {
                return false;
            }
        }
        return true;
    }
}
