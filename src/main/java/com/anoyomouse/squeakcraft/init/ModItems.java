/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.init;

import com.anoyomouse.squeakcraft.item.ItemBlockStockPile;
import com.anoyomouse.squeakcraft.item.ItemBlockTank;
import com.anoyomouse.squeakcraft.item.ItemSqueakCraft;
import com.anoyomouse.squeakcraft.item.ItemSqueakLeaf;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemBlock;

@GameRegistry.ObjectHolder(Reference.MODID)
public class ModItems
{
	public static final ItemSqueakCraft squeakLeaf = new ItemSqueakLeaf();

	public static void init()
	{
		GameRegistry.registerItem(squeakLeaf, Names.Items.SQUEAKLEAF);
	}
}
