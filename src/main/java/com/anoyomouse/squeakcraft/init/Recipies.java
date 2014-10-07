/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipies
{
	public static void init()
	{
		// Vanilla Items
		// GameRegistry.addRecipe(new ItemStack(ModItems.squeakLeaf), " a ", "aaa", " a ", 'a', new ItemStack(Items.stick));
		// GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.flag), new ItemStack(ModItems.squeakLeaf), new ItemStack(ModItems.squeakLeaf));

		// Using Ore Dictionary
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.squeakLeaf), " s ", "sss", " s ", 's', "stickWood"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.flag), new ItemStack(ModItems.squeakLeaf), new ItemStack(ModItems.squeakLeaf)));
	}
}
