package com.skcraft.smes.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.skcraft.smes.SMES;
import com.skcraft.smes.SMESCreativeTab;
import com.skcraft.smes.impl.IToolTipProvider;
import com.skcraft.smes.util.StringUtils;

public class ItemBase extends Item {
    public ItemBase() {
        setCreativeTab(SMESCreativeTab.tab);
    }
    
    @Override
    public Item setUnlocalizedName(String name) {
        super.setUnlocalizedName(SMES.PREFIX + name);
        return super.setTextureName(SMES.RSRC_PREFIX + name);
    }
}
