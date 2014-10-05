/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.utility;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/10/05.
 */
public class BlockHelper
{
	/**
	 * This is a helper method to return the approximate ForgeDirection an entity is facing.
	 * From https://github.com/LazDude2012/YATS/blob/master/util/LazUtils.java
	 * @param entity The EntityLiving to get a ForgeDirection from.
	 * @param inverted Whether to get the direction the entity is facing (useful for in-world GUIs) or the opposite direction. (useful for block placement)
	 * @return The ForgeDirection the entity is facing (or the opposite if needed).
	 */
	public static ForgeDirection GetFDFromEntity(EntityLivingBase entity, Boolean inverted)
	{
		if (inverted)
		{
			if (entity.rotationPitch > 60) return ForgeDirection.UP;
			if (entity.rotationPitch < -60) return ForgeDirection.DOWN;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch (facing)
			{
				case 0:
					return ForgeDirection.NORTH;
				case 1:
					return ForgeDirection.EAST;
				case 2:
					return ForgeDirection.SOUTH;
				case 3:
					return ForgeDirection.WEST;
			}
		}
		else
		{
			if (entity.rotationPitch > 60) return ForgeDirection.DOWN;
			if (entity.rotationPitch < -60) return ForgeDirection.UP;
			int facing = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
			switch (facing)
			{
				case 0:
					return ForgeDirection.SOUTH;
				case 1:
					return ForgeDirection.WEST;
				case 2:
					return ForgeDirection.NORTH;
				case 3:
					return ForgeDirection.EAST;
			}
		}
		throw new RuntimeException();
	}

}
