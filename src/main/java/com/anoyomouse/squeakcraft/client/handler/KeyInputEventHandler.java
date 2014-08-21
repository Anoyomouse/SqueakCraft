package com.anoyomouse.squeakcraft.client.handler;

import com.anoyomouse.squeakcraft.client.settings.KeyBindings;
import com.anoyomouse.squeakcraft.reference.Key;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

/**
 * Created by Anoyomouse on 2014/08/21.
 */
public class KeyInputEventHandler
{
	private static Key getPressedKeybinding()
	{
		if (KeyBindings.charge.isPressed())
		{
			return Key.CHARGE;
		}
		else if (KeyBindings.release.isPressed())
		{
			return Key.RELEASE;
		}
		else
		{
			return Key.UNKNOWN;
		}
	}

	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
	{
		LogHelper.info(getPressedKeybinding());
	}
}
