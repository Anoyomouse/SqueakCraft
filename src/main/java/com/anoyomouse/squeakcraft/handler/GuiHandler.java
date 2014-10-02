/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.handler;

import com.anoyomouse.squeakcraft.client.gui.inventory.GuiStockPile;
import com.anoyomouse.squeakcraft.inventory.ContainerStockPile;
import com.anoyomouse.squeakcraft.reference.GUIs;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
	{
		if (id == GUIs.STOCKPILE.ordinal())
		{
			TileEntityStockPile tileEntityStockPile = (TileEntityStockPile) world.getTileEntity(x, y, z);
			return new ContainerStockPile(entityPlayer.inventory, tileEntityStockPile);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
	{
		if (id == GUIs.STOCKPILE.ordinal())
		{
			TileEntityStockPile tileEntityStockPile = (TileEntityStockPile) world.getTileEntity(x, y, z);
			return new GuiStockPile(entityPlayer.inventory, tileEntityStockPile);
		}

		return null;
	}
}
