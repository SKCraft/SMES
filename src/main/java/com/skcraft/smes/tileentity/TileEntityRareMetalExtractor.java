package com.skcraft.smes.tileentity;

import com.skcraft.smes.util.StringUtils;

import net.minecraft.item.ItemStack;

public class TileEntityRareMetalExtractor extends TileEntityEnergyInventory {
    public TileEntityRareMetalExtractor() {
        super(1400000, 2);
    }

    @Override
    public String getInventoryName() {
        return StringUtils.translate("gui.rareMetalExtractor.name", true);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return false;
    }
}
