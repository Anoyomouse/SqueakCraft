/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.network.PacketHandler;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityPlacementTank;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityPlacementTank extends TileEntitySqueakCraft
{
	private final int EVENT_MASTER_UPDATE = 1;
	private final int EVENT_LAYER_UPDATE = 2;

	// Which sides of the tank are connected to other blocks
	private byte tankConnections;
	// Which layer of the tank is this block
	private int layer = 1;
	// Is this entity a master entity (i.e. it controls the block object)
	private boolean isMasterEntity = false;

	// Where is the master object, made this point to itself if it's the master
	private int masterEntityX;
	private int masterEntityY;
	private int masterEntityZ;

	// Has this block been deleted
	private boolean deleted;

	/* THESE SHOULD ONLY EXIST ON THE SERVER */
	// Reference to the master tile entity
	private TileEntityPlacementTank masterEntity;

	private boolean masterUpdated;
	private List<TileEntityPlacementTank> tileList;

	public TileEntityPlacementTank()
	{
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		this.layer = nbtTagCompound.getInteger(Names.NBT.LAYER);
		this.tankConnections = nbtTagCompound.getByte(Names.NBT.CONNECTED_SIDES);
		this.isMasterEntity = nbtTagCompound.getBoolean(Names.NBT.IS_MASTER);

		this.masterEntityX = nbtTagCompound.getInteger(Names.NBT.MASTER_X);
		this.masterEntityY = nbtTagCompound.getInteger(Names.NBT.MASTER_Y);
		this.masterEntityZ = nbtTagCompound.getInteger(Names.NBT.MASTER_Z);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setInteger(Names.NBT.LAYER, this.layer);
		nbtTagCompound.setByte(Names.NBT.CONNECTED_SIDES, this.tankConnections);
		nbtTagCompound.setBoolean(Names.NBT.IS_MASTER, this.isMasterEntity);

		nbtTagCompound.setInteger(Names.NBT.MASTER_X, this.masterEntityX);
		nbtTagCompound.setInteger(Names.NBT.MASTER_Y, this.masterEntityY);
		nbtTagCompound.setInteger(Names.NBT.MASTER_Z, this.masterEntityZ);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		IMessage msg = new MessageTileEntityPlacementTank(this);
		Packet packet = PacketHandler.INSTANCE.getPacketFrom(msg);
		LogHelper.info(this.toString() + " Got data packet: " + msg.toString());
		return packet;
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int eventData)
	{
		if (eventID == EVENT_MASTER_UPDATE)
		{
			LogHelper.info("Master Update, set master to " + eventData);
			this.setIsMaster(eventData == 1);
		}
		else if (eventID == EVENT_LAYER_UPDATE)
		{
			LogHelper.info("Layer Update, set layer to " + eventData);
			this.setLayer(eventData);
		}

		return super.receiveClientEvent(eventID, eventData);
	}

	public boolean isMaster()
	{
		return this.isMasterEntity;
	}

	public void setIsMaster(boolean newValue)
	{
		if (this.isMasterEntity != newValue)
		{
			this.isMasterEntity = newValue;

			if (newValue)
			{
				this.masterEntityX = this.xCoord;
				this.masterEntityY = this.yCoord;
				this.masterEntityZ = this.zCoord;
			}

			if (this.hasWorldObj() && !this.getWorldObj().isRemote)
			{
				this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), EVENT_MASTER_UPDATE, newValue ? 1 : 0);
			}

			this.markDirty();
		}
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		if (this.layer != layer)
		{
			this.layer = layer;

			if (this.hasWorldObj() && !this.getWorldObj().isRemote)
			{
				this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), EVENT_LAYER_UPDATE, layer);
			}

			this.markDirty();
		}
	}

	public byte getConnectedSides()
	{
		return this.tankConnections;
	}

	public void setConnectedSides(byte sides)
	{
		if (this.tankConnections != sides)
		{
			this.tankConnections = sides;
			this.markDirty();
		}
	}

	public int[] getMasterEntityLocation()
	{
		return new int[] { this.masterEntityX, this.masterEntityY, this.masterEntityZ };
	}

	public void setMasterEntityLocation(int x, int y, int z)
	{
		if (this.masterEntityX != x || this.masterEntityY != y || this.masterEntityZ != z)
		{
			this.masterEntityX = x;
			this.masterEntityY = y;
			this.masterEntityZ = z;

			this.markDirty();
		}
	}

	public void markDeleted()
	{
		this.deleted = true;
		this.markDirty();
	}

	public boolean isDeleted()
	{
		return this.deleted;
	}

	@Override
	public String toString()
	{
		String data = "TileEntityPlacementTank ";
		if (this.hasWorldObj())
		{
			data += " (" + (this.worldObj.isRemote ? "C" : "S") + ")";
		}

		data = String.format("%s (%3d,%3d,%3d)-%s", data, this.xCoord, this.yCoord, this.zCoord, (this.isMasterEntity ? "Y" : "N"));
		data = String.format("%s M@(%3d,%3d,%3d)", data, this.masterEntityX, this.masterEntityY, this.masterEntityZ);

		return data;
	}

	public TileEntityPlacementTank getMasterEntity()
	{
		if (this.masterEntity == null)
		{
			LogHelper.info(this.toString() + " Getting master Entity");
			if (this.isMaster())
			{
				this.masterEntity = this;
			}
			else
			{
				if (this.hasWorldObj())
				{
					TileEntity te = this.getWorldObj().getTileEntity(this.masterEntityX, this.masterEntityY,  this.masterEntityZ);
					LogHelper.info(this.toString() + " master TE: " + te);
					if (te == null || !(te instanceof TileEntityPlacementTank))
					{
						LogHelper.info(this.toString() + " CAN'T FIND MASTER TE");
						this.masterEntity = null;
					}
					else
						this.masterEntity = (TileEntityPlacementTank)te;
				}
			}
		}

		return this.masterEntity;
	}

	@Override
	public void markDirty()
	{
		this.masterEntity = null;
		this.masterUpdated = false;
		super.markDirty();
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (this.hasWorldObj() && !this.getWorldObj().isRemote && !this.masterUpdated && this.masterEntity == null)
		{
			TileEntity te = this.getMasterEntity();
			if (te != null)
			{
				this.masterUpdated = true;
				this.masterEntity.addChildToMaster(this);
			}
		}
	}

	public void addChildToMaster(TileEntityPlacementTank child)
	{
		if (this.tileList == null)
			this.tileList = new ArrayList<TileEntityPlacementTank>();

		if (!this.tileList.contains(child))
			this.tileList.add(child);
	}

	public void removeChildFromMaster(TileEntityPlacementTank child)
	{
		if (this.tileList.contains(child))
			this.tileList.remove(child);
	}

	public void takeOverMaster(TileEntityPlacementTank other)
	{
		if (worldObj.isRemote)
		{
			LogHelper.error(this.toString() + " request to take over master from client!");
			return;
		}

		LogHelper.info(this.toString() + " taking over master from " + other.toString());
		if (!this.isMasterEntity)
		{
			LogHelper.info("=====> I'm not a master but you want me to be a master .. check me! <=====");
			return;
		}

		for (TileEntityPlacementTank child : other.getChildren())
		{
			child.setIsMaster(false);
			child.setMasterEntityLocation(this.xCoord, this.yCoord, this.zCoord);
			child.markDirty();
		}

		other.setIsMaster(false);
		other.setMasterEntityLocation(this.xCoord, this.yCoord, this.zCoord);
		other.markDirty();
	}

	private List<TileEntityPlacementTank> getChildren()
	{
		return this.tileList;
	}
}
