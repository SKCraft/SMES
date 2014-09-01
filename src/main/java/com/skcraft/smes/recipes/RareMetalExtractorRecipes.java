package com.skcraft.smes.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.skcraft.smes.util.setup.SMESItems;

public class RareMetalExtractorRecipes {
    private static List<IEnergyRecipe> recipes = new ArrayList<IEnergyRecipe>();
    private static boolean didPostInit = false;

    public static void addRecipe(ExtractorRecipe recipe) {
        recipes.add(recipe);
    }
    
    public static void addRecipe(ItemStack input, ItemStack output, int energy) {
        addRecipe(new ExtractorRecipe(input, output, energy));
    }
    
    public static void addDictionaryRecipe(ExtractorDictionaryRecipe recipe) { 
       recipes.add(recipe);
    }
    
    public static void addDictionaryRecipe(String dictionaryName, int amount, ItemStack output, int energy) {
        addDictionaryRecipe(new ExtractorDictionaryRecipe(dictionaryName, amount, output, energy));
    }
    
    public static List<IEnergyRecipe> getRecipes() {
        return recipes;
    }
    
    private static IEnergyRecipe getRecipe(ItemStack input) {
        for (IEnergyRecipe recipe : recipes) {
            if (recipe.isInputValid(input)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static ItemStack getOutput(ItemStack input) {
        IEnergyRecipe recipe = getRecipe(input);
        return recipe != null ? recipe.getOutput() : null;
    }

    public static int getInputSize(ItemStack input) {
        IEnergyRecipe recipe = getRecipe(input);
        return recipe != null ? recipe.getInput().stackSize : 0;
    }
    
    public static boolean isValidInput(ItemStack input) {
        IEnergyRecipe recipe = getRecipe(input);
        return recipe != null;
    }

    public static int getEnergyRequired(ItemStack input) {
        IEnergyRecipe recipe = getRecipe(input);
        return recipe != null ? recipe.getEnergy() : 0;
    }
    
    public static void postInit() {
        if (!didPostInit) {
            // Add recipes here!
            addDictionaryRecipe("sapling", 1400, SMESItems.dustYttrium, 1400000);
            
            didPostInit = true;
        }
    }
}
