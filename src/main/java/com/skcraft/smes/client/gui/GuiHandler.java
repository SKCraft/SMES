package com.skcraft.smes.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.skcraft.smes.tileentity.TileEntityBase;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int MACHINE = 0;
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MACHINE) { 
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityBase) {
                return ((TileEntityBase) tile).getContainer(player.inventory);
            }
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MACHINE) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityBase) {
                return ((TileEntityBase) tile).getGui(player.inventory);
            }
        }
        return null;
    }
}
