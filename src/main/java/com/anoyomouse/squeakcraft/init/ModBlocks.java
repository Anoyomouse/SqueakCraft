package com.anoyomouse.squeakcraft.init;

import com.anoyomouse.squeakcraft.block.BlockFlag;
import com.anoyomouse.squeakcraft.block.BlockSqueakCraft;
import com.anoyomouse.squeakcraft.block.BlockStockPile;
import com.anoyomouse.squeakcraft.block.BlockTransportPipe;
import com.anoyomouse.squeakcraft.item.ItemBlockStockPile;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModBlocks
{
	public static final BlockSqueakCraft flag = new BlockFlag();
	public static final BlockSqueakCraft stockPile = new BlockStockPile();
	public static final BlockSqueakCraft transportPipe = new BlockTransportPipe();

	public static void init()
	{
		GameRegistry.registerBlock(flag, Names.Blocks.FLAG);
		GameRegistry.registerBlock(stockPile, ItemBlockStockPile.class, Names.Blocks.STOCKPILE);
		GameRegistry.registerBlock(transportPipe, Names.Blocks.TRANSPORT_PIPE);
	}
}
