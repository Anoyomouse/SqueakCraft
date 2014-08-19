package com.anoyomouse.squeakcraft;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.InstanceFactory;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = SqueakCraftMod.MODID, version = SqueakCraftMod.VERSION, name = SqueakCraftMod.NAME)
public class SqueakCraftMod
{
	public static final String MODID = "squeakcraft";
    public static final String NAME = "Squeak Craft";
    public static final String VERSION = "1.7.10-0.1";

	@Mod.Instance(SqueakCraftMod.MODID)
	public static SqueakCraftMod instance;

	// This is my logger!
    private Logger modLogger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	// Initalize the logger
    	modLogger = event.getModLog();
    	modLogger.info("PreInitalization Started");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// Write stuff
    	modLogger.info("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	modLogger.info("PostInitalization Started");
    }
}
