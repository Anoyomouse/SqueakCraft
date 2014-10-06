/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.reference;

import net.minecraft.util.ResourceLocation;
import com.anoyomouse.squeakcraft.utility.ResourceLocationHelper;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class Models
{
	public static final String MODEL_LOCATION = "models/";

	public static final ResourceLocation STOCKPILE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "stockpile.obj");
	public static final ResourceLocation TRANSPORT_PIPE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "Pipe.obj");
	public static final ResourceLocation CRATE_PILE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "CratePile.obj");
	public static final ResourceLocation NETWORK_INTERFACE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "Interface.obj");
	public static final ResourceLocation CRATE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "Crate.obj");
	public static final ResourceLocation TANK = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "Tank.obj");
	public static final ResourceLocation TANK_VALVE = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "TankValve.obj");
	public static final ResourceLocation TANK_SUPPORT = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "TankSupport.obj");
	public static final ResourceLocation BOX = ResourceLocationHelper.getResourceLocation(MODEL_LOCATION + "Box.obj");
}
