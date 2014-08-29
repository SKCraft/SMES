package com.skcraft.smes.item;

import com.skcraft.smes.impl.IToolTipProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemPurifiable extends Item implements IToolTipProvider {
    @Override
    public void provideTooltip(ItemStack itemStack, EntityPlayer player, List<String> toolTip) {
    }
}
