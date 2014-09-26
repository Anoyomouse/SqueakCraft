package com.anoyomouse.squeakcraft.proxy;

import com.anoyomouse.squeakcraft.handler.ConfigurationHandler;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy
{
	@Override
	public void registerEventHandlers()
	{
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
	}

	@Override
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityStockPile.class, Names.Blocks.STOCKPILE);
	}
}
