/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.client.renderer.model;

import com.anoyomouse.squeakcraft.reference.Models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
@SideOnly(Side.CLIENT)
public class ModelTankValve
{
	private IModelCustom modelTankValve;

	public ModelTankValve()
	{
		modelTankValve = AdvancedModelLoader.loadModel(Models.TANK_VALVE);
	}

	public void render()
	{
		modelTankValve.renderAll();
	}

	public void renderPart(String partName)
	{
		modelTankValve.renderPart(partName);
	}
}
