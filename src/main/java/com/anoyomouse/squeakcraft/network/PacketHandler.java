package com.anoyomouse.squeakcraft.network;

import com.anoyomouse.squeakcraft.network.message.MessageTileEntitySqueakCraft;
import com.anoyomouse.squeakcraft.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID.toLowerCase());

	public static void init()
	{
		INSTANCE.registerMessage(MessageTileEntitySqueakCraft.class, MessageTileEntitySqueakCraft.class, 0, Side.CLIENT);
	}
}
