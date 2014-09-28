package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.block.BlockTransportPipe;
import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.inventory.ContainerStockPile;
import com.anoyomouse.squeakcraft.network.PacketHandler;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityStockPile;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityTransportPipe extends TileEntitySqueakCraft
{
	/**
	 * Server sync counter (once per 20 ticks)
	 */
	private int ticksSinceSync = -1;

	// Items in the pipe
	private ItemStack[] inventory;

	private boolean hadStuff;

	public TileEntityTransportPipe()
	{
		super();
		inventory = new ItemStack[16];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
		inventory = new ItemStack[16];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < inventory.length)
			{
				inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}

		checkStacks();
	}

	// Borrowed from IronChests
	@Override
	public void markDirty()
	{
		super.markDirty();
		checkStacks();
	}

	// Borrowed from IronChests
	private void checkStacks()
	{
		if (worldObj == null || worldObj.isRemote)
		{
			return;
		}

		boolean hasStuff = false;
		for (int i = 0; i < 16; i++)
		{
			if (inventory[i] != null)
			{
				hasStuff = true;
				break;
			}
		}

		LogHelper.info("StockPile (" + xCoord + "," + yCoord + "," + zCoord + ") - Has Stuff: " + hasStuff + " - Had Stuff: " + hadStuff);

		// If we don't have stuff, but we had stuff, then we need to update the block
		if (!hasStuff && hadStuff)
		{
			hadStuff = false;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			LogHelper.info("StockPile (" + xCoord + "," + yCoord + "," + zCoord + ") has no stuff");
		}
		// If we have stuff, but we didn't have stuff, then we need to update the block
		else if (hasStuff && !hadStuff)
		{
			hadStuff = true;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			LogHelper.info("StockPile (" + xCoord + "," + yCoord + "," + zCoord + ") has stuff");
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		checkStacks();
		return super.getDescriptionPacket(); // PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityStockPile(this));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
		{
			if (inventory[currentIndex] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
	}

	public int edgeDetect(int x, int y, int z)
	{
		int edges = 0;

		TileEntity te = worldObj.getTileEntity(x, y + 1, z);
		if (te != null) LogHelper.info("Block (x, y + 1, z) - (" + x + ", " + (y + 1) + ", " + z + "): " + te.toString());
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 0;
		}

		te = worldObj.getTileEntity(x, y, z - 1);
		if (te != null) LogHelper.info("Block (x, y, z - 1) - (" + x + ", " + y + ", " + (z - 1) + "): " + te.toString());
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 4;
		}

		te = worldObj.getTileEntity(x, y, z + 1);
		if (te != null) LogHelper.info("Block (x, y, z + 1) - (" + x + ", " + y + ", " + (z + 1) + "): " + te.toString());
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 2;
		}

		te = worldObj.getTileEntity(x - 1, y, z);
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 1;
		}

		te = worldObj.getTileEntity(x + 1, y, z);
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 3;
		}

		te = worldObj.getTileEntity(x, y - 1, z);
		if (te != null && te instanceof TileEntityTransportPipe)
		{
			edges |= 1 << 5;
		}

		return edges;
	}

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (worldObj == null)
		{
			return;
		}

		if (!worldObj.isRemote && ticksSinceSync < 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.stockPile, 1, 0);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			this.markDirty();
		}

		this.ticksSinceSync++;
		if (ticksSinceSync % (20 * 4) == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.stockPile, 1, 0);
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
			return true;
		}

		return super.receiveClientEvent(eventID, eventData);
	}
}
