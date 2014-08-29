package com.skcraft.smes.proxy;

import com.skcraft.smes.SMES;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent evt) {
        SMES.log = evt.getModLog();
        SMES.log.info("Modulating pre-initialization phase...");
        SMES.log.info("Pre-initialization phase concluded");
    }

    public void init(FMLInitializationEvent evt) {
        SMES.log.info("Modulating initialization phase...");
        SMES.log.info("Main intializaiton phase concluded");
    }

    public void postInit(FMLPostInitializationEvent evt) {
        SMES.log.info("Modulating post-initializaiton phase...");
        SMES.log.info("Post-initialization phase concluded");
    }
}
