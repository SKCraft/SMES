package com.skcraft.smes.proxy;

import com.skcraft.smes.SMES;
import com.skcraft.smes.client.gui.SMESGuiHandler;
import com.skcraft.smes.item.SMESItems;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent evt) {
        SMES.log = evt.getModLog();
        SMES.log.info("Modulating pre-initialization phase...");
        
        SMESItems.preInit();
        
        SMES.log.info("Pre-initialization phase concluded");
    }
    
    public void init(FMLInitializationEvent evt) {
        SMES.log.info("Modulating initialization phase...");
        SMES.proxy.registerHandlers();
        SMES.log.info("Main intializaiton phase concluded");
    }
    
    public void registerHandlers() {
        SMES.log.info("Registering handlers...");
        NetworkRegistry.INSTANCE.registerGuiHandler(SMES.instance, new SMESGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent evt) {
        SMES.log.info("Modulating post-initializaiton phase...");
        SMES.log.info("Post-initialization phase concluded");
    }
}
