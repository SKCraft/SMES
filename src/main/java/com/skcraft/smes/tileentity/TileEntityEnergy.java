package com.skcraft.smes.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileEntityEnergy extends TileEntityBase implements IEnergyHandler {
    private EnergyStorage battery;

    public TileEntityEnergy(int energyCapacity) {
        this.battery = new EnergyStorage(energyCapacity);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        this.battery.readFromNBT(tagCompound.getCompoundTag("Energy"));
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setTag("Energy", this.battery.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return this.battery.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return this.battery.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return this.battery.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return this.battery.getMaxEnergyStored();
    }
    
    public void setEnergyStored(int energy) {
        this.battery.setEnergyStored(energy);
    }
    
    public EnergyStorage getBattery() {
        return this.battery;
    }

    @SideOnly(Side.CLIENT)
    public int getEnergyScaled(int scale) {
        int energyScaled = (int) Math.floor(((double) this.battery.getEnergyStored()) / ((double) this.battery.getMaxEnergyStored()) * scale);
        if (energyScaled == 0 && this.battery.getEnergyStored() > 0) {
            energyScaled++;
        }
        return energyScaled;
    }
}
