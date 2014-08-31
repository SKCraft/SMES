package com.skcraft.smes.client.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IToolTipProvider {
    public void provideTooltip(ItemStack itemStack, EntityPlayer player, List<String> toolTip);
}
