/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.network;

import com.anoyomouse.squeakcraft.network.message.MessageTileEntityPlacementTank;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntitySqueakCraft;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityStockPile;
import com.anoyomouse.squeakcraft.network.message.MessageTileEntityTransportPipe;
import com.anoyomouse.squeakcraft.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase());

	public static void init()
	{
		INSTANCE.registerMessage(MessageTileEntitySqueakCraft.class, MessageTileEntitySqueakCraft.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityStockPile.class, MessageTileEntityStockPile.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityTransportPipe.class, MessageTileEntityTransportPipe.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityPlacementTank.class, MessageTileEntityPlacementTank.class, 3, Side.CLIENT);
	}
}
