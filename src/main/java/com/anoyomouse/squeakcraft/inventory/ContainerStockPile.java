package com.anoyomouse.squeakcraft.inventory;

import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class ContainerStockPile extends ContainerSqueakCraft
{
	// Small Chest
	public static final int INVENTORY_ROWS = 2;
	public static final int INVENTORY_COLUMNS = 2;
	public static final int INVENTORY_SIZE = INVENTORY_ROWS * INVENTORY_COLUMNS;

	private TileEntityStockPile tileEntityStockPile;
	private int chestInventoryRows;
	private int chestInventoryColumns;

	public ContainerStockPile(InventoryPlayer inventoryPlayer, TileEntityStockPile tileEntityStockPile)
	{
		this.tileEntityStockPile = tileEntityStockPile;
		tileEntityStockPile.openInventory();

		chestInventoryRows = 2;
		chestInventoryColumns = 2;

		// Add the Alchemical Chest slots to the container
		for (int chestRowIndex = 0; chestRowIndex < chestInventoryRows; ++chestRowIndex)
		{
			for (int chestColumnIndex = 0; chestColumnIndex < chestInventoryColumns; ++chestColumnIndex)
			{
				this.addSlotToContainer(new Slot(tileEntityStockPile, chestColumnIndex + chestRowIndex * chestInventoryColumns, 67 + chestColumnIndex * 18, 5 + chestRowIndex * 18));
			}
		}

		// Add the player's inventory slots to the container
		for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
		{
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 5 + inventoryColumnIndex * 18, 45 + inventoryRowIndex * 18));
			}
		}

		// Add the player's action bar slots to the container
		for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 5 + actionBarSlotIndex * 18, 103));
		}
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer entityPlayer)
	{
		super.onContainerClosed(entityPlayer);
		tileEntityStockPile.closeInventory();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
	{
		ItemStack newItemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack = slot.getStack();
			newItemStack = itemStack.copy();

			if (slotIndex < chestInventoryRows * chestInventoryColumns)
			{
				if (!this.mergeItemStack(itemStack, chestInventoryRows * chestInventoryColumns, inventorySlots.size(), false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemStack, 0, chestInventoryRows * chestInventoryColumns, false))
			{
				return null;
			}

			if (itemStack.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return newItemStack;
	}
}
