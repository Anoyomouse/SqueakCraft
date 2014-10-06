/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.proxy;

import com.anoyomouse.squeakcraft.handler.ConfigurationHandler;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.tileentity.*;
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
		GameRegistry.registerTileEntity(TileEntityNetworkInterface.class, Names.Blocks.NETWORK_INTERFACE);
		GameRegistry.registerTileEntity(TileEntityPlacementTank.class, Names.Blocks.PLACEMENT_TANK);
		GameRegistry.registerTileEntity(TileEntityStockPile.class, Names.Blocks.STOCKPILE);
		GameRegistry.registerTileEntity(TileEntityTank.class, Names.Blocks.TANK);
		GameRegistry.registerTileEntity(TileEntityTransportPipe.class, Names.Blocks.TRANSPORT_PIPE);
	}
}
