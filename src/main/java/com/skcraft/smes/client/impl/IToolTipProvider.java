package com.skcraft.smes.client.impl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IToolTipProvider {
    @SideOnly(Side.CLIENT)
    public void provideToolTip(ItemStack itemStack, EntityPlayer player, List<String> toolTip);
}
