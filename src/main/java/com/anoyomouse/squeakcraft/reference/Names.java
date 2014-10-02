/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.reference;

public final class Names
{
	public static final class Keys
	{
		public static final String CATEGORY = "keys." + Reference.MODID + ".category";

		public static final String CHARGE = "keys." + Reference.MODID + ".charge";
		public static final String RELEASE = "keys." + Reference.MODID + ".release";
	}

	public static final class NBT
	{
		public static final String ITEMS = "Items";
		public static final String CHARGE_LEVEL = "chargeLevel";
		public static final String MODE = "mode";
		public static final String CRAFTING_GUI_OPEN = "craftingGuiOpen";
		public static final String UUID_MOST_SIG = "UUIDMostSig";
		public static final String UUID_LEAST_SIG = "UUIDLeastSig";
		public static final String DISPLAY = "display";
		public static final String COLOR = "color";
		public static final String STATE = "teState";
		public static final String CUSTOM_NAME = "CustomName";
		public static final String DIRECTION = "teDirection";
		public static final String OWNER = "owner";
		public static final String OWNER_UUID_MOST_SIG = "ownerUUIDMostSig";
		public static final String OWNER_UUID_LEAST_SIG = "ownerUUIDLeastSig";
		public static final String CONNECTED_SIDES = "CONNECTED_SIDES";
	}

	public static final class Containers
	{
		public static final String VANILLA_INVENTORY = "container.inventory";
		public static final String VANILLA_CRAFTING = "container.crafting";
		public static final String STOCKPILE = "container." + Reference.MODID + ":" + Blocks.STOCKPILE;
		public static final String TRANSPORT_PIPE = "container." + Reference.MODID + ":" + Blocks.TRANSPORT_PIPE;
	}

	public static final class Items
	{
		public static final String SQUEAKLEAF = "squeakLeaf";
	}

	public static final class ModelParts
	{
		public static final String STOCKPILE_BASE = "StockpileBase";
		public static final String STOCKPILE_CRATE = "StockpileChest";

		public static final String TRANSPORT_PIPE_LONG = "PipeLong";
		public static final String TRANSPORT_PIPE_CORNER = "PipeCorner";
		public static final String TRANSPORT_PIPE_CORNER1 = "Corner1";
		public static final String TRANSPORT_PIPE_CORNER2 = "Corner2";
		public static final String TRANSPORT_PIPE_CORNER3 = "Corner3";
		public static final String TRANSPORT_PIPE_CORNER4 = "Corner4";
		public static final String TRANSPORT_PIPE_CORNER5 = "Corner5";
		public static final String TRANSPORT_PIPE_CORNER6 = "Corner6";
	}

	public static final class Blocks
	{
		public static final String FLAG = "flag";
		public static final String STOCKPILE = "stockPile";
		public static final String TRANSPORT_PIPE = "transportPipe";
	}
}
