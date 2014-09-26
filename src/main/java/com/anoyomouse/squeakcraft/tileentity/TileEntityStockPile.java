package com.anoyomouse.squeakcraft.tileentity;

import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.inventory.ContainerStockPile;
import com.anoyomouse.squeakcraft.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityStockPile extends TileEntitySqueakCraft implements IInventory
{
	/**
	 * The number of players currently using this chest
	 */
	public int numUsingPlayers;

	/**
	 * Server sync counter (once per 20 ticks)
	 */
	private int ticksSinceSync;

	// Items in the stockpile
	private ItemStack[] inventory;

	private boolean hadStuff;

	public TileEntityStockPile()
	{
		super();
		this.state = 0;

		inventory = new ItemStack[ContainerStockPile.INVENTORY_SIZE];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
		inventory = new ItemStack[this.getSizeInventory()];
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

	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex)
	{
		if (slotIndex >= 0 && slotIndex < inventory.length)
			return inventory[slotIndex];

		return null;
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
		if (worldObj != null && worldObj.isRemote)
		{
			return;
		}

		boolean hasStuff = false;
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (inventory[i] != null)
			{
				hasStuff = true;
				break;
			}
		}

		if (!hasStuff && hadStuff)
		{
			hadStuff = false;
			if (worldObj != null)
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			return;
		}
		hadStuff = true;
		if (worldObj != null)
		{
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int decrementAmount)
	{
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			if (itemStack.stackSize <= decrementAmount)
			{
				setInventorySlotContents(slotIndex, null);
			}
			else
			{
				itemStack = itemStack.splitStack(decrementAmount);
				if (itemStack.stackSize == 0)
				{
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex)
	{
		if (slotIndex >= 0 && slotIndex < inventory.length)
		{
			if (inventory[slotIndex] != null)
			{
				ItemStack itemStack = inventory[slotIndex];
				inventory[slotIndex] = null;
				return itemStack;
			}
			else
			{
				return null;
			}
		}

		return null;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
	{
		if (slotIndex >= 0 && slotIndex < inventory.length)
		{
			inventory[slotIndex] = itemStack;

			if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
			{
				itemStack.stackSize = this.getInventoryStackLimit();
			}

			this.markDirty();
		}
	}

	@Override
	public String getInventoryName()
	{
		return this.hasCustomName() ? this.getCustomName() : Names.Containers.STOCKPILE;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return this.hasCustomName();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 *
	 * @param entityplayer The player we are checking to see if they can use this chest
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory()
	{
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.stockPile, 1, numUsingPlayers);
	}

	@Override
	public void closeInventory()
	{
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.stockPile, 1, numUsingPlayers);
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack)
	{
		return true;
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

	/**
	 * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	 * ticks and creates a new spawn inside its implementation.
	 */
	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (++ticksSinceSync % 20 * 4 == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ModBlocks.stockPile, 1, numUsingPlayers);
		}
	}

	/**
	 * Called when a client event is received with the event number and argument, see World.sendClientEvent
	 */
	@Override
	public boolean receiveClientEvent(int eventID, int numUsingPlayers)
	{
		if (eventID == 1)
		{
			this.numUsingPlayers = numUsingPlayers;
			return true;
		}
		else
		{
			return super.receiveClientEvent(eventID, numUsingPlayers);
		}
	}
}
