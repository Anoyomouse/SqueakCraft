/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.network.PacketHandler;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntitySqueakCraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityNetworkInterface extends TileEntitySqueakCraft
{
	public TileEntityNetworkInterface()
	{
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntitySqueakCraft(this));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int eventData)
	{
		return super.receiveClientEvent(eventID, eventData);
	}
}
