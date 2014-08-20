package com.anoyomouse.squeakcraft.creativetab;

import com.anoyomouse.squeakcraft.init.ModItems;
import com.anoyomouse.squeakcraft.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabSqueakCraft
{
	public static final CreativeTabs SQUEAKCRAFT_TAB = new CreativeTabs(
			Reference.MODID)
	{

		@Override
		public Item getTabIconItem()
		{
			// TODO Auto-generated method stub
			return ModItems.squeakLeaf;
		}
	};
}
