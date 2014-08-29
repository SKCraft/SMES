package com.skcraft.smes.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.skcraft.smes.SMES;
import com.skcraft.smes.impl.IToolTipProvider;
import com.skcraft.smes.util.StringUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPurifiable extends Item implements IToolTipProvider {
    public ItemPurifiable() {
        setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public void provideTooltip(ItemStack itemStack, EntityPlayer player, List<String> toolTip) {
        toolTip.add(String.format(StringUtils.translate("purifiable.desc", true), itemStack.getTagCompound().getDouble(SMES.PREFIX + "purity")));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        // First NBT version with 55.0000000% purity
        list.add(createSubType(item, 0.550000000f));
        
        // Second NBT version with 80.0000000% purity
        list.add(createSubType(item, 0.800000000f));
        
        // Third NBT version with 95.0000000% purity
        list.add(createSubType(item, 0.950000000f));
    }
    
    private ItemStack createSubType(Item item, float purity) {
        // Prepare itemStack and NBTTagCompound
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        StringBuilder sb = new StringBuilder(SMES.PREFIX).append("purity");
        ItemStack itemStack = new ItemStack(item, 1, 0);
        
        nbttagcompound.setFloat(sb.toString(), purity);
        itemStack.setTagCompound(nbttagcompound);
        
        return itemStack;
    }
}