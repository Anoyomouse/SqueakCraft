/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.SqueakCraftMod;
import com.anoyomouse.squeakcraft.api.ITubeConnectable;
import com.anoyomouse.squeakcraft.block.BlockTransportPipe;
import com.anoyomouse.squeakcraft.network.PacketHandler;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityTransportPipe;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.transport.TransportCrate;
import com.anoyomouse.squeakcraft.utility.HelperUtilities;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

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
	private byte connectedSides = 0;
	private ArrayList<TransportCrate> crates = new ArrayList<TransportCrate>();

	public TileEntityTransportPipe()
	{
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		this.setConnectedSidesByte(nbtTagCompound.getByte(Names.NBT.CONNECTED_SIDES));

		int numberOfCrates = nbtTagCompound.getInteger(Names.NBT.TRANSPORT_PIPE_CRATE_COUNT);

		NBTTagList contents = nbtTagCompound.getTagList(Names.NBT.TRANSPORT_PIPE_ITEMS, numberOfCrates);
		if (this.crates == null ) { this.crates = new ArrayList<TransportCrate>(); }
		else { this.crates.clear(); }

		for (int i = 0; i < contents.tagCount(); i++)
		{
			NBTTagCompound tagCompound = contents.getCompoundTagAt(i);

			this.crates.add(TransportCrate.loadTransportCrateFromNBTTag(tagCompound));
		}

		if (worldObj != null && !worldObj.isRemote)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}

		// PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityTransportPipe(this), new NetworkRegistry.TargetPoint(worldObj.getWorldInfo().getVanillaDimension(), xCoord, yCoord, zCoord, 20));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setByte(Names.NBT.CONNECTED_SIDES, getConnectedSidesByte());

		NBTTagList contents = new NBTTagList();
		for (TransportCrate transportCrates : crates)
		{
			contents.appendTag(transportCrates.getNBTTagData());
		}

		nbtTagCompound.setTag(Names.NBT.TRANSPORT_PIPE_ITEMS, contents);
	}

	/* ** CALLED FROM PacketHelper ** */
	public byte getConnectedSidesByte()
	{
		return HelperUtilities.getByteFromDirectionArray(isConnectedOnSide);
	}

	public void setConnectedSidesByte(byte sides)
	{
		// Read in the ItemStacks in the inventory from NBT
		this.connectedSides = HelperUtilities.setDirectionArrayFromByte(sides, this.isConnectedOnSide);
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
		int sideInt = side.ordinal();
		if (isConnectedOnSide[sideInt] != connected)
		{
			isConnectedOnSide[sideInt] = connected;
			if (connected)
				connectedSides ++;
			else
				connectedSides --;
		}
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

	public ArrayList<TransportCrate> getContents()
	{
		return this.crates;
	}

	public boolean isBiDirectional()
	{
		byte connectedCount = 0;
		ForgeDirection lastDir = null;
		boolean biDirectional = false;
		for (int i = 0; i < 6; i++)
		{
			if (isConnectedOnSide[i])
			{
				connectedCount ++;
				if (connectedCount == 1)
					lastDir = ForgeDirection.getOrientation(i);
				biDirectional = (connectedCount == 2) && (lastDir.getOpposite().ordinal() == i);
			}
		}
		this.connectedSides = connectedCount;

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

		if (this.crates != null || this.crates.size() > 0)
		{
			ArrayList<TransportCrate> markForRemoval = new ArrayList<TransportCrate>();
			for (TransportCrate crate : this.crates)
			{
				crate.addProgress(5);

				if (crate.getProgress() == 50)
				{
					// LogHelper.info("We're in the middle, we need to move in a random direction!");
					if (this.connectedSides == 1)
					{
						for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
						{
							if (this.isConnectedOnSide[direction.ordinal()])
							{
								// LogHelper.info("Bounce block, we only have 1 connection: " + direction.toString());
								crate.setHeading(direction);
								break;
							}
						}
					}
					else if (this.connectedSides == 0)
					{
						// Eject!
						markForRemoval.add(crate);
						LogHelper.info("Eject block, we don't have any connections");
					}
					else if (this.connectedSides != 2)
					{
						ArrayList<ForgeDirection> dirs = new ArrayList<ForgeDirection>();
						for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
						{
							if (this.isConnectedOnSide[direction.ordinal()] && crate.getHeading().getOpposite() != direction)
							{
								dirs.add(direction);
							}
						}

						ForgeDirection newHeading = dirs.get(SqueakCraftMod.instance.random.nextInt(dirs.size()));
						// LogHelper.info("Block going in " + crate.getHeading() + " going off to " + newHeading + " at random");
						crate.setHeading(newHeading);
					}
					else
					{
						if (this.isConnectedOnSide[crate.getHeading().ordinal()])
						{
							continue;
						}

						for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
						{
							if (this.isConnectedOnSide[direction.ordinal()] && crate.getHeading().getOpposite() != direction)
							{
								// LogHelper.info("Block going in " + crate.getHeading() + " going off to " + direction);
								crate.setHeading(direction);
								break;
							}
						}
					}
				}
				else if (crate.getProgress() >= 100)
				{
					markForRemoval.add(crate);
					TransferCrateToNextPipe(crate, crate.getHeading());
				}
			}

			for (TransportCrate crate : markForRemoval)
			{
				this.ejectTransportCrate(crate);
			}
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		// PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityTransportPipe(this), new NetworkRegistry.TargetPoint(worldObj.getWorldInfo().getVanillaDimension(), xCoord, yCoord, zCoord, 20));
	}

	private void ejectTransportCrate(TransportCrate crate)
	{
		this.crates.remove(crate);
		if (crate.getProgress() < 100)
		{
			// Throw stuff on the floor!
			// Eject contents here!
		}
	}

	private void TransferCrateToNextPipe(TransportCrate crate, ForgeDirection direction)
	{
		TileEntity te = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
		if (te instanceof TileEntityTransportPipe)
		{
			crate.setProgress(0);
			((TileEntityTransportPipe) te).getContents().add(crate);
		}
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
