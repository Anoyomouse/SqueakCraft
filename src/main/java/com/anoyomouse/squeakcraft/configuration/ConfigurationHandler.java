package com.anoyomouse.squeakcraft.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
	public static void init(File configFile)
	{
		Configuration configuration = new Configuration(configFile);
		
		try
		{
			configuration.load();
			
			// Read the ccnfiguration file
		}
		catch (Exception e)
		{
			// Log the error
		}
		finally
		{
			// Save (Create) the config file
			configuration.save();
		}
	}
}
