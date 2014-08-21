package com.anoyomouse.squeakcraft.proxy;

import com.anoyomouse.squeakcraft.client.settings.KeyBindings;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerKeyBindings()
	{
		ClientRegistry.registerKeyBinding(KeyBindings.charge);
		ClientRegistry.registerKeyBinding(KeyBindings.release);
	}
}
