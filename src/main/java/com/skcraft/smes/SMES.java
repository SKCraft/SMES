package com.skcraft.smes;

import com.skcraft.smes.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SMES.MOD_ID, name = SMES.NAME)
public class SMES {
    public static final String MOD_ID = "smes";
    public static final String NAME = "SKCraft - SMES";
    public static final String PREFIX = "smes.";
    public static final String RSRC_PREFIX = "smes:";

    @Mod.Instance("smes")
    public static SMES instance;

    @SidedProxy(clientSide = "com.skcraft.smes.proxy.client.ClientProxy", serverSide="com.skcraft.smes.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger log;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        proxy.preInit(evt);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        proxy.init(evt);
    }

    public void postInit(FMLPostInitializationEvent evt) {
        proxy.postInit(evt);
    }
}
