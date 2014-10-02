/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.api.ITubeConnectable;
import com.anoyomouse.squeakcraft.block.BlockTransportPipe;
import com.anoyomouse.squeakcraft.network.PacketHandler;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityTransportPipe;
import com.anoyomouse.squeakcraft.reference.Names;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityTransportPipe extends TileEntitySqueakCraft implements ITubeConnectable
{
	/**
	 * Server sync counter (once per 20 ticks)
	 */
	private int ticksSinceSync = -1;

	private boolean isConnectableOnSide[] = {true, true, true, true, true, true};
	private boolean isConnectedOnSide[] = new boolean[6];

	public TileEntityTransportPipe()
	{
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		this.setConnectedSidesByte(nbtTagCompound.getByte(Names.NBT.CONNECTED_SIDES));

		if (worldObj != null && !worldObj.isRemote)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityTransportPipe(this), new NetworkRegistry.TargetPoint(worldObj.getWorldInfo().getVanillaDimension(), xCoord, yCoord, zCoord, 20));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setByte(Names.NBT.CONNECTED_SIDES, getConnectedSidesByte());
	}

	/* ** CALLED FROM PACKETHELPER ** */
	public byte getConnectedSidesByte()
	{
		byte sides = 0;
		for (int i = 0; i < 6; i++)
		{
			if (isConnectedOnSide[i]) sides |= (1 << i);
		}

		return sides;
	}

	public void setConnectedSidesByte(byte sides)
	{
		// Read in the ItemStacks in the inventory from NBT
		for (int i = 0; i < 6; i++)
		{
			if ((sides & (1 << i)) != 0)
			{
				isConnectedOnSide[i] = true;
			}
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityTransportPipe(this));
	}

	@Override
	public boolean AcceptsItemsOnSide(ForgeDirection side)
	{
		return isConnectedOnSide[side.ordinal()];
	}

	@Override
	public boolean IsConnectedOnSide(ForgeDirection side)
	{
		return isConnectedOnSide[side.ordinal()];
	}

	@Override
	public Boolean CanRoute()
	{
		return true;
	}

	@Override
	public void SetConnectionOnSide(ForgeDirection side, boolean connected)
	{
		isConnectedOnSide[side.ordinal()] = connected;
	}

	@Override
	public boolean IsConnectableOnSide(ForgeDirection side)
	{
		return isConnectableOnSide[side.ordinal()];
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	public boolean isBiDirectional()
	{
		int connectedCount = 0;
		ForgeDirection lastDir = null;
		boolean biDirectional = false;
		for (int i = 0; i < 6; i++)
		{
			if (isConnectedOnSide[i])
			{
				connectedCount ++;
				if (connectedCount == 1)
					lastDir = ForgeDirection.getOrientation(i);
				if (connectedCount == 2)
				{
					biDirectional = (lastDir.getOpposite().ordinal() == i);
				}
				else
					biDirectional = false;
			}
		}

		return biDirectional;
	}

	public void checkForAdjacentBlocks()
	{
		if (worldObj != null)
		{
			BlockTransportPipe.CheckTubeConnections(worldObj, xCoord, yCoord, zCoord);
		}
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.checkForAdjacentBlocks();

		if (worldObj == null)
		{
			return;
		}

		if (!worldObj.isRemote && ticksSinceSync < 0)
		{
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.getConnectedSidesByte());
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.markDirty();
		}

		this.ticksSinceSync++;
		if (ticksSinceSync % (20 * 4) == 0)
		{
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.getConnectedSidesByte());
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityTransportPipe(this), new NetworkRegistry.TargetPoint(worldObj.getWorldInfo().getVanillaDimension(), xCoord, yCoord, zCoord, 20));
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int eventData)
	{
		if (eventID == 1)
		{
			this.setConnectedSidesByte((byte)eventData);
			return true;
		}

		return super.receiveClientEvent(eventID, eventData);
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
		this.checkForAdjacentBlocks();
	}

	@Override
	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}
}
