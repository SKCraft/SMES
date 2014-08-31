package com.skcraft.smes.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExtractorDictionaryRecipe implements IEnergyRecipe {
    private String dictionaryName;
    private int inputAmount;
    private ItemStack output;
    private int energy;
    
    public ExtractorDictionaryRecipe(String dictionaryName, int inputAmount, ItemStack output, int energy) {
        this.dictionaryName = dictionaryName;
        this.inputAmount = inputAmount;
        this.output = output;
        this.energy = energy;
    }
    
    public String getDictionaryName() {
        return dictionaryName;
    }
    
    public int getInputSize() {
        return inputAmount;
    }
    
    public boolean isInputValid(ItemStack input) {
        ArrayList<ItemStack> items = OreDictionary.getOres(dictionaryName);
        return items != null && items.contains(input);
    }
    
    /**
     * As there are multiple inputs return a dummy (index 0)
     * Do not trust the returned item
     */
    public ItemStack getInput() {
        ArrayList<ItemStack> items = OreDictionary.getOres(dictionaryName);
        ItemStack input = items.get(0);
        input.stackSize = inputAmount;
        return input;
    }
    
    public ItemStack getOutput() {
        return output;
    }
    
    public int getEnergy() {
        return energy;
    }
}
