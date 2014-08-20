package com.anoyomouse.squeakcraft;

import org.apache.logging.log4j.Logger;

import com.anoyomouse.squeakcraft.handler.ConfigurationHandler;
import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.init.ModItems;
import com.anoyomouse.squeakcraft.init.Recipies;
import com.anoyomouse.squeakcraft.proxy.IProxy;
import com.anoyomouse.squeakcraft.reference.Reference;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.InstanceFactory;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, guiFactory = Reference.GUI_FACTORY)
public class SqueakCraftMod
{
	@Mod.Instance(Reference.MODID)
	public static SqueakCraftMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;

	// This is my logger!
	private Logger modLogger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Initialize the logger
		modLogger = event.getModLog();
		modLogger.info("PreInitalization");
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());

		ModItems.init();
		ModBlocks.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		modLogger.info("Initalization");
		Recipies.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		modLogger.info("PostInitalization");
	}
}
