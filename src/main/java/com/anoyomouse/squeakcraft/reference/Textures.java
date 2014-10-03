/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.reference;

import com.anoyomouse.squeakcraft.utility.ResourceLocationHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public final class Textures
{
	public static final String RESOURCE_PREFIX = Reference.MODID.toLowerCase() + ":";

	public static final class Armor
	{
		private static final String ARMOR_SHEET_LOCATION = "textures/armor/";
	}

	public static final class Model
	{
		private static final String MODEL_TEXTURE_LOCATION = "textures/models/";
		public static final ResourceLocation STOCKPILE_BASE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "stockpile_base.png");
		public static final ResourceLocation STOCKPILE_CRATE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "stockpile_crate.png");

		public static final ResourceLocation TRANSPORT_PIPE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "PipeTexture.png");

		public static final ResourceLocation INTERFACE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "InterfaceTexture.png");

		public static final ResourceLocation CRATE_PILE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "stockpile_crate.png");
		public static final ResourceLocation CRATE = ResourceLocationHelper.getResourceLocation(MODEL_TEXTURE_LOCATION + "stockpile_crate.png");
	}

	public static final class Gui
	{
		private static final String GUI_SHEET_LOCATION = "textures/gui/";
		public static final ResourceLocation STOCKPILE = ResourceLocationHelper.getResourceLocation(GUI_SHEET_LOCATION + "stockpile_gui.png");
	}

	public static final class Effect
	{
		private static final String EFFECTS_LOCATION = "textures/effects/";
	}
}
