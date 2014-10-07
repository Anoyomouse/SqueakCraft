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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityPlacementTank extends TileEntitySqueakCraft
{
	private final int EVENT_MASTER_UPDATE = 1;
	private final int EVENT_LAYER_UPDATE = 2;

	private byte tankConnections;
	private int layer = 1;
	private boolean isMasterEntity = false;

	private int masterEntityX;
	private int masterEntityY;
	private int masterEntityZ;

	/* SERVER SIDE STUFF */
	private TileEntityPlacementTank masterEntity;
	private List<TileEntityPlacementTank> tileList;

	/* END Server Side Stuff */

	// Has this block been deleted
	private boolean deleted;

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
		Packet packet = PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityPlacementTank(this));
		LogHelper.info(this.toString() + " Got data packet");
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
			this.setIsMaster(eventData == 1);
		}
		else if (eventID == 2)
		{
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

			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), EVENT_MASTER_UPDATE, newValue ? 1 : 0);
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
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), EVENT_LAYER_UPDATE, layer);
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
				}
			}
		}

		return this.masterEntity;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		this.masterEntity = null;
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

	/*
	public void updateConnectedEntities(World world, int x, int y, int z)
	{
		if (world.isRemote)
		{
			return;
		}

		boolean foundAdjacentBlock = false;

		TileEntityPlacementTank masterEntity = null;
		for (ForgeDirection checkDir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity te = world.getTileEntity(x + checkDir.offsetX, y + checkDir.offsetY, z + checkDir.offsetZ);
			if (te instanceof TileEntityPlacementTank)
			{
				if(!((TileEntityPlacementTank) te).isDeleted())
				{
					foundAdjacentBlock = true;
					TileEntityPlacementTank tankEntity = (TileEntityPlacementTank) te;
					if (tankEntity.isMaster())
					{
						if (masterEntity != null && masterEntity != tankEntity)
						{
							masterEntity.takeOverMaster(tankEntity);
						}

						if (masterEntity == null)
						{
							masterEntity = tankEntity;
							masterEntity.getChildren().add(myTileEntity);
						}
					}
					else
					{
						if (masterEntity == null)
						{
							masterEntity = tankEntity.getMasterEntity();
							myTileEntity.setMasterEntity(masterEntity);
						}
						else if (tankEntity.getMasterEntity() != masterEntity)
						{
							tankEntity.setMasterEntity(masterEntity);
						}
					}
				}
			}
		}

		if (!foundAdjacentBlock)
		{
			myTileEntity.setIsMaster(true);
		}
	}

	public void checkLinkedBlocks(World world, int x, int y, int z)
	{
		if (world.isRemote)
		{
			// Only run on the server!
			return;
		}

		for (ForgeDirection checkDir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity te = world.getTileEntity(x + checkDir.offsetX, y + checkDir.offsetY, z + checkDir.offsetZ);
			if (te instanceof TileEntityPlacementTank)
			{
				this.updateConnectedBlocks(world, x + checkDir.offsetX, y + checkDir.offsetY, z + checkDir.offsetZ);
			}
		}
	}*/


	/*
	public void setAsMaster()
	{
		LogHelper.info(this.toString() + " setting as master tile entity");
		this.isMasterEntity = true;

		if (this.hasWorldObj())
		{
			if (!worldObj.isRemote)
			{
				this.masterEntity = null;
				if (this.tileList == null)
					this.tileList = new ArrayList<TileEntityPlacementTank>();

				this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 1); // Set as master event
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}
		else
		{
			if (this.tileList == null)
				this.tileList = new ArrayList<TileEntityPlacementTank>();
			this.masterEntity = null;
		}
	}

	public List<TileEntityPlacementTank> getChildren()
	{
		if (worldObj.isRemote)
		{
			LogHelper.error(this.toString() + " requesting children list on client");
			return null;
		}

		LogHelper.info(this.toString() + " getting children list");

		if (this.tileList == null)
		{
			LogHelper.info(this.toString() + " is not set as master, but master tile list requested");
			// I'm a master?
			this.setAsMaster();
		}

		return this.tileList;
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
			child.setMasterEntity(this);
		}

		other.setMasterEntity(this);
	}

	public void markBlockForUpdate()
	{
		if (this.hasWorldObj())
		{
			this.getWorldObj().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public TileEntityPlacementTank getMasterEntity()
	{
		if (this.hasWorldObj())
		{
			if (this.masterEntity == null && !this.isMasterEntity)
			{
				this.masterEntity = (TileEntityPlacementTank)this.getWorldObj().getTileEntity(this.masterEntityLocation[0], this.masterEntityLocation[1], this.masterEntityLocation[2]);
			}
		}
		return this.masterEntity;
	}

	public void setMasterEntity(TileEntityPlacementTank master)
	{
		if (worldObj.isRemote)
		{
			LogHelper.error(this.toString() + " request to take set master entity on client!");
			return;
		}

		// This is going to cause a null reference exception .. i need the stacktrace!
		LogHelper.info(this.toString() + " setting master to " + master.toString());
		if (this.isMasterEntity)
		{
			this.isMasterEntity = false;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, 0); // Unset as master event
			this.tileList.clear();
			this.tileList = null;
		}

		if (this.masterEntity != null && master != this.masterEntity)
		{
			// Notify old master we've detached?
		}

		if (master != this.masterEntity)
		{
			this.masterEntityLocation = new int[] { master.xCoord, master.yCoord, master.zCoord };
			this.masterEntity = master;
			if (!master.getChildren().contains(this))
			{
				master.getChildren().add(this);
			}

			this.markDirty();
		}
	}*/
}
