/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.client.renderer.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import com.anoyomouse.squeakcraft.reference.Models;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
@SideOnly(Side.CLIENT)
public class ModelStockPile
{
	private IModelCustom modelStockPile;

	public ModelStockPile()
	{
		modelStockPile = AdvancedModelLoader.loadModel(Models.STOCKPILE);
	}

	public void render()
	{
		modelStockPile.renderAll();
	}

	public void renderPart(String partName)
	{
		modelStockPile.renderPart(partName);
	}
}
