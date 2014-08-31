package com.skcraft.smes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.skcraft.smes.item.SMESItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SMESCreativeTab extends CreativeTabs {
    protected SMESCreativeTab() {
        super("tabSMES");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return SMESItems.itemYttriumDust;
    }

}
