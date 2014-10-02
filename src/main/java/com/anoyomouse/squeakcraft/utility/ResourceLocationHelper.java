/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.utility;

import net.minecraft.util.ResourceLocation;
import com.anoyomouse.squeakcraft.reference.Reference;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class ResourceLocationHelper
{
	public static ResourceLocation getResourceLocation(String modId, String path)
	{
		return new ResourceLocation(modId, path);
	}

	public static ResourceLocation getResourceLocation(String path)
	{
		return getResourceLocation(Reference.MODID.toLowerCase(), path);
	}
}
