package com.skcraft.smes.util.setup;

import net.minecraft.block.Block;

import com.skcraft.smes.SMES;
import com.skcraft.smes.block.BlockMachineExtractor;
import com.skcraft.smes.tileentity.TileEntityRareMetalExtractor;

import cpw.mods.fml.common.registry.GameRegistry;

public class SMESBlocks {
    public static Block rareMetalExtractor;

    public static void preInit() {
        initBlocks();
    }

    public static void init() {
        registerBlocks();
        registerTileEntities();
        registerRecipes();
    }

    private static void initBlocks() {
        SMES.log.info("Composing blocks...");

        rareMetalExtractor = new BlockMachineExtractor().setBlockName("rareMetalExtractor");
        
        SMES.log.info("Items created");
    }

    private static void registerBlocks() {
        GameRegistry.registerBlock(rareMetalExtractor, SMES.PREFIX + "rareMetalExtractor");
    }
    
    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityRareMetalExtractor.class, "rareMetalExtractor");
    }

    private static void registerRecipes() {}
}
