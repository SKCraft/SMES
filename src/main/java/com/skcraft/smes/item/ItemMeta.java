package com.skcraft.smes.item;

import com.skcraft.smes.SMES;
import com.skcraft.smes.client.impl.IToolTipProvider;
import com.skcraft.smes.item.impl.IPurifiable;
import com.skcraft.smes.util.StringUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMeta extends ItemBase implements IToolTipProvider, IPurifiable {
    private Map<Integer, MetaItem> metaItems = new HashMap<Integer, MetaItem>();
    private int highestMeta = 0;
    private Map<Integer, IIcon> icons = new HashMap<Integer, IIcon>();

    public ItemMeta(String fallbackName) {
        this.setUnlocalizedName(fallbackName);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(SMES.tabSMES);
    }

    public ItemStack addMetaItem(int index, MetaItem item, boolean registerCustomItemStack, boolean registerOreDict) {
        if (item == null)
            return null;

        this.metaItems.put(index, item);
        ItemStack itemStack = new ItemStack(this, 1, index);

        if (item.purifiable)
            enablePurity(itemStack);
        if (index > this.highestMeta)
            this.highestMeta = index;
        if (registerCustomItemStack)
            GameRegistry.registerCustomItemStack(item.name, itemStack);
        if (registerOreDict)
            OreDictionary.registerOre(item.name, itemStack);

        return itemStack;
    }

    public MetaItem getMetaItem(ItemStack itemStack) {
        return this.metaItems.get(itemStack.getItemDamage());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        MetaItem metaItem = this.getMetaItem(itemStack);
        return metaItem != null ? "item." + SMES.PREFIX + metaItem.name : super.getUnlocalizedName();
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (Map.Entry<Integer, MetaItem> metaItemEntry : metaItems.entrySet()) {
            if (metaItemEntry.getValue() != null) {
                ItemStack itemStack = new ItemStack(item, 1, metaItemEntry.getKey());
                if (metaItemEntry.getValue().purifiable) {
                    enablePurity(itemStack);
                }
                list.add(itemStack);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        MetaItem metaItem = this.getMetaItem(itemStack);
        if (metaItem != null)
            return metaItem.rarity;
        return super.getRarity(itemStack);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void provideToolTip(ItemStack itemStack, EntityPlayer player, List toolTipLines) {
        MetaItem metaItem = this.getMetaItem(itemStack);
        if (metaItem == null)
            return;
        if (metaItem.toolTipLines.length > 0) {
            for (String toolTip : metaItem.toolTipLines) {
                toolTipLines.add(StringUtils.translate(toolTip));
            }
        }
        if (metaItem.purifiable && itemStack.hasTagCompound())
            toolTipLines.add(String.format(StringUtils.translate("purifiable.desc", true), itemStack.getTagCompound().getDouble("purity")));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        for (Map.Entry<Integer, MetaItem> metaItemEntry : metaItems.entrySet()) {
            if (metaItemEntry.getValue() != null) {
                this.icons.put(metaItemEntry.getKey(), register.registerIcon(SMES.RSRC_PREFIX + metaItemEntry.getValue().name));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage) {
        return this.icons.get(damage);
    }

    @Override
    public ItemStack enablePurity(ItemStack itemStack) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setDouble("purity", 0.550000000D);
        itemStack.setTagCompound(nbtTagCompound);
        return itemStack;
    }

    public static class MetaItem {
        public String name;
        public String[] toolTipLines;
        public EnumRarity rarity;
        public boolean purifiable;

        public MetaItem(String name, String[] toolTipLines, EnumRarity rarity, boolean purifiable) {
            this.name = name;
            this.toolTipLines = toolTipLines != null ? toolTipLines : new String[0];
            this.rarity = rarity;
            this.purifiable = purifiable;
        }
    }
}
