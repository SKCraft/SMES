package com.skcraft.smes.proxy.client;

import net.minecraftforge.common.MinecraftForge;

import com.skcraft.smes.client.event.ToolTipHandler;
import com.skcraft.smes.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerHandlers() {
        super.registerHandlers();
        
        MinecraftForge.EVENT_BUS.register(new ToolTipHandler());
    }
}
