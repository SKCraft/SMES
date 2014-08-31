package com.skcraft.smes.tileentity;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityBase extends TileEntity {
    // [rotation][side]
    // down, up, north, south, west, east
    private static final int[][] rotatedTextureIndexes = new int[][] { { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 3, 4, 5 }, { 0, 1, 2, 3, 4, 5 }, { 0, 1, 3, 2, 5, 4 }, { 0, 1, 5, 4, 2, 3 }, { 0, 1, 4, 5, 3, 2 }, };

    private ForgeDirection facing;

    private boolean wasActive = false;

    public TileEntityBase() {
        this.facing = ForgeDirection.SOUTH;
    }
    
    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!this.worldObj.isRemote && this.wasActive != this.isActive()) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.wasActive = this.isActive();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        int rotation = tagCompound.getInteger("rotation");
        this.rotateTo(rotation);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setInteger("rotation", this.getFacing().ordinal());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        this.readFromNBT(packet.func_148857_g());
    }

    public ForgeDirection getFacing() {
        return this.facing;
    }

    public int getRotatedSide(int side) {
        return rotatedTextureIndexes[this.getFacing().ordinal()][side];
    }

    public boolean isActive() {
        return false;
    }

    public void rotateTo(ForgeDirection rotation) {
        this.facing = rotation;
        if (this.worldObj != null) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    public void rotateTo(int rotation) {
        this.rotateTo(ForgeDirection.getOrientation(rotation));
    }

    public void rotate() {
        if (!this.worldObj.isRemote) {
            switch (this.facing) {
            case NORTH:
                this.facing = ForgeDirection.EAST;
                break;
            case EAST:
                this.facing = ForgeDirection.SOUTH;
                break;
            case SOUTH:
                this.facing = ForgeDirection.WEST;
                break;
            case WEST:
            default:
                this.facing = ForgeDirection.NORTH;
            }
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
    }
    
    public ArrayList<ItemStack> getDropItems() {
        return null;
    }
    
    public Container getContainer(InventoryPlayer invPlayer) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public GuiContainer getGui(InventoryPlayer invPlayer) {
        return null;
    }
}
