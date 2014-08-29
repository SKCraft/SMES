package com.skcraft.smes.client.event;

import com.skcraft.smes.impl.IToolTipProvider;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ToolTipHandler {
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent evt) {
        Item item = evt.itemStack.getItem();
        if (item instanceof IToolTipProvider) {
            ((IToolTipProvider)item).provideTooltip(evt.itemStack, evt.entityPlayer, evt.toolTip);
        } else if (item instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(item);
            if (block instanceof IToolTipProvider) {
                ((IToolTipProvider)block).provideTooltip(evt.itemStack, evt.entityPlayer, evt.toolTip);
            }
        }
    }
}
