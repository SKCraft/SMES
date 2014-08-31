package com.skcraft.smes.item;

import net.minecraft.item.Item;
import com.skcraft.smes.SMES;

public class ItemBase extends Item {
    public ItemBase() {
        setCreativeTab(SMES.tabSMES);
    }

    @Override
    public Item setUnlocalizedName(String name) {
        super.setUnlocalizedName(SMES.PREFIX + name);
        return super.setTextureName(SMES.RSRC_PREFIX + name);
    }
}
