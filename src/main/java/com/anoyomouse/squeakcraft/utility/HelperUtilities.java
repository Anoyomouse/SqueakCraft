/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.utility;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/10/05.
 */
public class HelperUtilities
{
	private static byte[] directionCache;
	public static byte[] getDirectionCache()
	{
		if (directionCache == null)
		{
			directionCache = new byte[6];
			for (int i = 0; i < 6; i++)
			{
				directionCache[i] = (byte)(1 << i);
			}
		}

		return directionCache;
	}

	public static byte getByteFromDirectionArray(boolean[] input)
	{
		if (input == null || input.length < 6)
		{
			return 0;
		}

		byte[] dirCache = getDirectionCache();

		byte sides = 0;
		for (int i = 0; i < 6; i++)
		{
			if (input[i]) sides |= dirCache[i];
		}

		return sides;
	}

	public static byte setDirectionArrayFromByte(byte sides, boolean[] output)
	{
		byte counter = 0;
		byte[] dirCache = getDirectionCache();
		for (int i = 0; i < 6; i++)
		{
			if ((sides & dirCache[i]) != 0)
			{
				output[i] = true;
				counter++;
			}
			else
			{
				output[i] = false;
			}
		}

		return counter;
	}

	public static boolean isDirectionSet(byte sideByte, ForgeDirection direction)
	{
		byte[] dirCache = getDirectionCache();
		return (sideByte & dirCache[direction.ordinal()]) != 0;
	}

	public static char getSideChar(World world)
	{
		return (world.isRemote ? 'C' : 'S');
	}
}
