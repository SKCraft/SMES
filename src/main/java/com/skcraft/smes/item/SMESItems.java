package com.skcraft.smes.item;

import net.minecraft.item.Item;

public class SMESItems {
    public static Item itemYttriumDust;
    
    public static void preInit() {
        itemYttriumDust = new ItemPurifiable().setUnlocalizedName("yttriumDust");
    }
}
