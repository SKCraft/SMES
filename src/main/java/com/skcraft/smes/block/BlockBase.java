package com.skcraft.smes.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.skcraft.smes.SMES;

public class BlockBase extends Block {
    public BlockBase(Material material) {
        super(material);
        setCreativeTab(SMES.tabSMES);
    }

    @Override
    public Block setBlockName(String name) {
        super.setBlockName(SMES.PREFIX + name);
        return super.setBlockTextureName(SMES.RSRC_PREFIX + name);
    }
}
