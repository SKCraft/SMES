package com.skcraft.smes.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import cofh.lib.gui.slot.ISlotValidator;

import com.skcraft.smes.inventory.container.slot.validators.SlotValidatorItemStack;
import com.skcraft.smes.inventory.container.slot.validators.SlotValidatorOreDictionary;
import com.skcraft.smes.tileentity.TileEntityExtractor.ExtractorType;
import com.skcraft.smes.util.setup.SMESItems;

public class ExtractorRecipes {
    private static HashMap<ExtractorType, List<IEnergyRecipe>> recipes = 
                    new HashMap<ExtractorType, List<IEnergyRecipe>>();
    private static boolean didPostInit = false;

    public static void addRecipe(ExtractorType type, IEnergyRecipe recipe) {
        if (recipes.containsKey(type)) {
            recipes.get(type).add(recipe);
        } else {
            List<IEnergyRecipe> recipeList = new ArrayList<IEnergyRecipe>();
            recipeList.add(recipe);
            recipes.put(type, recipeList);
        }
    }
    
    public static List<IEnergyRecipe> getRecipes(ExtractorType type) {
        return recipes.containsKey(type) ? recipes.get(type) : null;
    }
    
    private static IEnergyRecipe getRecipe(ExtractorType type, ItemStack input) {
        if (!recipes.containsKey(type)) {
            return null;
        }
        
        List<IEnergyRecipe> recipeList = recipes.get(type);
        for (IEnergyRecipe recipe : recipeList) {
            if (recipe.isInputValid(input)) {
                return recipe;
            }
        }
        return null;
    }
    
    public static ItemStack getOutput(ExtractorType type, ItemStack input) {
        IEnergyRecipe recipe = getRecipe(type, input);
        return recipe != null ? recipe.getOutput() : null;
    }

    public static int getInputSize(ExtractorType type, ItemStack input) {
        IEnergyRecipe recipe = getRecipe(type, input);
        return recipe != null ? recipe.getInput().stackSize : 0;
    }
    
    public static boolean isValidInput(ExtractorType type, ItemStack input) {
        IEnergyRecipe recipe = getRecipe(type, input);
        return recipe != null;
    }

    public static int getEnergyRequired(ExtractorType type, ItemStack input) {
        IEnergyRecipe recipe = getRecipe(type, input);
        return recipe != null ? recipe.getEnergy() : 0;
    }
    
    public static void postInit() {
        if (!didPostInit) {
            // Add recipes here!
            addRecipe(ExtractorType.RARE_METAL, new ExtractorDictionaryRecipe(new ItemStack(Blocks.sapling, 10), SMESItems.dustYttrium, 1000));
            
            didPostInit = true;
        }
    }
    
    /**
     * Builds a list of slot validators for the SlotMultipleValid on the extractor GUIs
     * @param type The extractor type to get validators of
     * @return A list of validators to be added to the SlotMultipleValid based on the recipe inputs
     */
    public static List<ISlotValidator> getInputValidators(ExtractorType type) {
        if (!recipes.containsKey(type)) {
            return null;
        }
        List<ISlotValidator> validators = new ArrayList<ISlotValidator>();
        
        for (IEnergyRecipe recipe : getRecipes(type)) {
            if (recipe.useOreDictionary()) {
                validators.add(new SlotValidatorOreDictionary(recipe.getInput()));
            } else {
                validators.add(new SlotValidatorItemStack(recipe.getInput()));
            }
        }
        return validators;
    }
}
