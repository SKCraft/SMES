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

        dustYttrium = itemDusts.addMetaItem(0, new ItemMeta.MetaItem("dustYtrrium", null, EnumRarity.common, true), true, false);
        dustBarium = itemDusts.addMetaItem(1, new ItemMeta.MetaItem("dustBarium", null, EnumRarity.common, true), true, false);
        dustNiobium = itemDusts.addMetaItem(2, new ItemMeta.MetaItem("dustNiobium", null, EnumRarity.common, true), true, false);

        SMES.log.info("Items created");
    }

    private static void registerItems() {
        GameRegistry.registerItem(itemDusts, SMES.PREFIX + "dust");
    }

    private static void registerRecipes() {}
//    public static Item itemYttriumDust;
//    public static Item itemBariumDust;
//    public static Item itemNiobiumDust;
//
//    public static void preInit() {
//        SMES.log.info("Initializing items...");
//        itemYttriumDust = new ItemSMESDust().setUnlocalizedName("dustYttrium");
//        registerItem(itemYttriumDust);
//        itemBariumDust = new ItemSMESDust().setUnlocalizedName("dustBarium");
//        registerItem(itemBariumDust);
//        itemNiobiumDust = new ItemSMESDust().setUnlocalizedName("dustNiobium");
//        registerItem(itemNiobiumDust);
//        SMES.log.info("Items initialized");
//    }
//
//    private static void registerItem(Item item, String suffix) {
//        String name = item.getUnlocalizedName().replace("item." + SMES.PREFIX, "") + suffix;
//        GameRegistry.registerItem(item, name);
//    }
//
//    private static void registerItem(Item item) {
//        registerItem(item, "");
//    }
}
