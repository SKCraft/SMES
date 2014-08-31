package com.skcraft.smes.recipes;

import net.minecraft.item.ItemStack;

public class ExtractorRecipe implements IEnergyRecipe {
    private ItemStack input;
    private ItemStack output;
    private int energy;
    
    public ExtractorRecipe(ItemStack input, ItemStack output, int energy) {
        this.input = input;
        this.output = output;
        this.energy = energy;
    }
    
    public boolean isInputValid(ItemStack itemStack) {
        return ItemStack.areItemStacksEqual(this.input, itemStack);
    }
    
    public ItemStack getInput() {
        return input;
    }
    
    public int getInputSize() {
        return input.stackSize;
    }
    
    public ItemStack getOutput() {
        return output;
    }
    
    public int getEnergy() {
        return energy;
    }
}
