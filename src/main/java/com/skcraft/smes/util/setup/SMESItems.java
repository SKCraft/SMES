package com.skcraft.smes.util.setup;

import com.skcraft.smes.SMES;
import com.skcraft.smes.item.ItemMeta;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class SMESItems {
    public static ItemMeta itemDusts;

    public static ItemStack dustYttrium = null;
    public static ItemStack dustBarium = null;
    public static ItemStack dustNiobium = null;

    public static ItemStack componentYBCO = null;
    public static ItemStack componentNbSn = null;
    public static ItemStack componentSuperconductiveWire = null;

    public static ItemStack itemSupermagnet = null;

    public static void preInit() {
        initItems();
        registerItems();
    }

    public static void init() {
        registerRecipes();
    }

    private static void initItems() {
        SMES.log.info("Composing items...");

        itemDusts = new ItemMeta("dust");

        dustYttrium = itemDusts.addMetaItem(0, new ItemMeta.MetaItem("dustYttrium", null, EnumRarity.common, true), true, true);
        dustBarium = itemDusts.addMetaItem(1, new ItemMeta.MetaItem("dustBarium", null, EnumRarity.common, true), true, true);
        dustNiobium = itemDusts.addMetaItem(2, new ItemMeta.MetaItem("dustNiobium", null, EnumRarity.common, true), true, true);

        SMES.log.info("Items created");
    }

    private static void registerItems() {
        GameRegistry.registerItem(itemDusts, SMES.PREFIX + "dust");
    }

    private static void registerRecipes() {}
}
