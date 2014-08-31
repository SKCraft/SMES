package com.skcraft.smes.item;

import com.skcraft.smes.SMES;
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
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemMeta extends ItemBase implements IPurifiable {
    private Map<Integer, MetaItem> metaItems = new HashMap<Integer, MetaItem>();
    private int highestMeta = 0;
    private final String fallbackName;
    private Map<Integer, IIcon> icons = new HashMap<Integer, IIcon>();

    public ItemMeta(String fallbackName) {
        this.fallbackName = fallbackName;

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
            SMES.log.info("Shit's purifiable, yo");
        if (index > this.highestMeta)
            this.highestMeta = index;

        return registerStackAndDict(itemStack, registerCustomItemStack, registerOreDict);
    }

    public MetaItem getMetaItem(ItemStack itemStack) {
        return this.metaItems.get(itemStack.getItemDamage());
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (Map.Entry<Integer, MetaItem> metaItemEntry : metaItems.entrySet()) {
            if (metaItemEntry.getValue() != null) {
                list.add(new ItemStack(item, 1, metaItemEntry.getKey()));
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
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        MetaItem metaItem = this.getMetaItem(itemStack);
        if (metaItem != null && metaItem.tooltipLines.length > 0) {
            for (String tooltip : metaItem.tooltipLines) {
                list.add(StringUtils.translate(tooltip));
            }
        }
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

    private ItemStack registerStackAndDict(ItemStack itemStack, boolean registerCustomItemStack, boolean registerOreDict) {
        MetaItem item = getMetaItem(itemStack);
        SMES.log.info(item.name);
        if (registerCustomItemStack)
            GameRegistry.registerCustomItemStack(item.name, itemStack);
        if (registerOreDict)
            OreDictionary.registerOre(item.name, itemStack);

        return itemStack;
    }

    public static class MetaItem {
        public String name;
        public String[] tooltipLines;
        public EnumRarity rarity;
        public boolean purifiable;

        public MetaItem(String name, String[] tooltipLines, EnumRarity rarity, boolean purifiable) {
            this.name = name;
            this.tooltipLines = tooltipLines != null ? tooltipLines : new String[0];
            this.rarity = rarity;
            this.purifiable = purifiable;
        }
    }
}
