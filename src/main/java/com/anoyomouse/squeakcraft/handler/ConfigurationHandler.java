/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.handler;

import java.io.File;

import com.anoyomouse.squeakcraft.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
	public static Configuration configuration;

	public static boolean testValue = false;

	public static void init(File configFile)
	{
		if (configuration == null)
		{
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(
			ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.modID.equalsIgnoreCase(Reference.MODID))
		{
			// Resync configs
			loadConfiguration();
		}
	}

	private static void loadConfiguration()
	{
		try
		{
			// Read the ccnfiguration file
			testValue = configuration.get(Configuration.CATEGORY_GENERAL,
					"configValue", false, "This is an example config value")
					.getBoolean();
		} catch (Exception e)
		{
			// Log the error
		} finally
		{
			// Save (Create) the configuration file
			if (configuration.hasChanged())
			{
				configuration.save();
			}
		}
	}
}
