package com.anoyomouse.squeakcraft;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = SqueakCraftMod.MODID, version = SqueakCraftMod.VERSION)
public class SqueakCraftMod
{
    public static final String MODID = "squeakcraftmod";
    public static final String VERSION = "1.7.10-0.1";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }
}
