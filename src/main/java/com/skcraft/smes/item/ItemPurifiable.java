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
        // Prepare itemStack and NBTTagCompound
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        StringBuilder sb = new StringBuilder(SMES.PREFIX).append("purity");
        ItemStack itemStack = new ItemStack(item, 1, 0);
        
        // First NBT version with 55.0000000% purity
        nbttagcompound.setDouble(sb.toString(), 0.550000000f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Second NBT version with 70.9677419% purity
        nbttagcompound.setDouble(sb.toString(), 0.709677419f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Third NBT version with 83.0188679% purity
        nbttagcompound.setFloat(sb.toString(), 0.830188679f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Fourth NBT version with 90.7216495% purity
        nbttagcompound.setFloat(sb.toString(), 0.907216495f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Fifth NBT version with 95.1351351% purity
        nbttagcompound.setFloat(sb.toString(), 0.951351351f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());

        // Sixth NBT version with 97.5069252% purity
        nbttagcompound.setFloat(sb.toString(), 0.975069252f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Seventh NBT version with 98.7377279% purity
        nbttagcompound.setFloat(sb.toString(), 0.987377279f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Eight NBT version with 99.3648553% purity
        nbttagcompound.setFloat(sb.toString(), 0.993648553f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Ninth NBT version with 99.6814159% purity
        nbttagcompound.setFloat(sb.toString(), 0.996814159f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Tenth NBT version with 99.8404538% purity
        nbttagcompound.setFloat(sb.toString(), 0.998404538f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Eleventh NBT version with 99.9201632% purity
        nbttagcompound.setFloat(sb.toString(), 0.999201632f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Twelfth NBT version with 99.9600657% purity
        nbttagcompound.setFloat(sb.toString(), 0.999600657f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Thirteenth NBT version with 99.9800288% purity
        nbttagcompound.setFloat(sb.toString(), 0.999800288f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Fourteenth NBT version with 99.9900134% purity
        nbttagcompound.setFloat(sb.toString(), 0.999900134f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());

        // Fifteenth NBT version with 99.9950065% purity
        nbttagcompound.setFloat(sb.toString(), 0.999950065f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());

        // Sixteenth NBT version with 99.9975032% purity
        nbttagcompound.setFloat(sb.toString(), 0.999975032f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
        
        // Seventeenth NBT version with 99.9987516% purity
        nbttagcompound.setFloat(sb.toString(), 0.999987516f);
        itemStack.setTagCompound(nbttagcompound);
        list.add(itemStack.copy());
    }
}