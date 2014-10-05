/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.init;

import com.anoyomouse.squeakcraft.block.*;
import com.anoyomouse.squeakcraft.item.ItemBlockStockPile;
import com.anoyomouse.squeakcraft.item.ItemBlockTank;
import com.anoyomouse.squeakcraft.item.ItemSqueakCraft;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlockWithMetadata;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModBlocks
{
	public static final BlockSqueakCraft flag = new BlockFlag();
	public static final BlockSqueakCraft stockPile = new BlockStockPile();
	public static final BlockSqueakCraft transportPipe = new BlockTransportPipe();
	public static final BlockSqueakCraft networkInterface = new BlockNetworkInterface();
	public static final BlockSqueakCraft tank = new BlockTank();

	public static void init()
	{
		GameRegistry.registerBlock(flag, Names.Blocks.FLAG);
		GameRegistry.registerBlock(stockPile, ItemBlockStockPile.class, Names.Blocks.STOCKPILE);
		GameRegistry.registerBlock(transportPipe, Names.Blocks.TRANSPORT_PIPE);
		GameRegistry.registerBlock(networkInterface, Names.Blocks.NETWORK_INTERFACE);
		GameRegistry.registerBlock(tank, ItemBlockTank.class, Names.Blocks.TANK);
	}
}
