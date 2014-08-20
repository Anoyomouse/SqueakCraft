package com.anoyomouse.squeakcraft.init;

import com.anoyomouse.squeakcraft.block.BlockFlag;
import com.anoyomouse.squeakcraft.block.BlockSqueakCraft;
import com.anoyomouse.squeakcraft.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModBlocks
{
	public static final BlockSqueakCraft flag = new BlockFlag();

	public static void init()
	{
		GameRegistry.registerBlock(flag, "flag");
	}
}
