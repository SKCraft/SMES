package com.skcraft.smes.recipes;

import net.minecraft.item.ItemStack;

public interface IRecipe {
    public boolean isInputValid(ItemStack input);
    public ItemStack getInput();
    public ItemStack getOutput();
    public boolean useOreDictionary();
}
