package com.anoyomouse.squeakcraft.init;

import com.anoyomouse.squeakcraft.item.ItemSqueakCraft;
import com.anoyomouse.squeakcraft.item.ItemSqueakLeaf;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	public static final ItemSqueakCraft squeakLeaf = new ItemSqueakLeaf();
	
	public static void init()
	{
		GameRegistry.registerItem(squeakLeaf, "squeakLeaf");
	}
}
