package com.skcraft.smes.recipes;

import java.util.ArrayList;

import cofh.lib.inventory.ComparableItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExtractorDictionaryRecipe implements IEnergyRecipe {
    private final ComparableItemStack input;
    private ComparableItemStack query = new ComparableItemStack(new ItemStack(Blocks.stone));
    private ItemStack output;
    private int energy;
    
    public ExtractorDictionaryRecipe(ItemStack input, ItemStack output, int energy) {
        this.input = new ComparableItemStack(input);
        this.output = output;
        this.energy = energy;
    }
    
    public int getInputSize() {
        return this.input.stackSize;
    }
    
    public boolean isInputValid(ItemStack input) {
        return this.input.isItemEqual(query.set(input));
    }
    
    public ItemStack getInput() {
        return this.input.toItemStack();
    }
    
    public ItemStack getOutput() {
        return this.output;
    }
    
    public int getEnergy() {
        return this.energy;
    }
}
