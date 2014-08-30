package com.skcraft.smes.item;

import net.minecraft.item.Item;

import com.skcraft.smes.SMES;

import cpw.mods.fml.common.registry.GameRegistry;

public class SMESItems {
    public static Item itemYttriumDust;
    
    public static void preInit() {
        SMES.log.info("Initializing items...");
        itemYttriumDust = new ItemPurifiable().setUnlocalizedName("yttriumDust");
        registerItem(itemYttriumDust);
        SMES.log.info("Items initialized");
    }
    
    private static void registerItem(Item item, String suffix) {
        String name = item.getUnlocalizedName().replace("item." + SMES.PREFIX, "") + suffix;
        GameRegistry.registerItem(item, name);
    }
    
    private static void registerItem(Item item) {
        registerItem(item, "");
    }
}
