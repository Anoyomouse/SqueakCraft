package com.anoyomouse.squeakcraft.proxy;

import com.anoyomouse.squeakcraft.client.renderer.item.ItemRendererStockPile;
import com.anoyomouse.squeakcraft.client.renderer.tileentity.TileEntityRendererStockPile;
import com.anoyomouse.squeakcraft.client.settings.KeyBindings;
import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
	}

	@Override
	public void registerKeyBindings()
	{
		ClientRegistry.registerKeyBinding(KeyBindings.charge);
		ClientRegistry.registerKeyBinding(KeyBindings.release);
	}

	@Override
	public void initRenderingAndTextures()
	{
		RenderIds.stockpile = RenderingRegistry.getNextAvailableRenderId();

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.stockPile), new ItemRendererStockPile());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStockPile.class, new TileEntityRendererStockPile());
	}

	@Override
	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch)
	{
		// ClientSoundHelper.playSound(soundName, xCoord, yCoord, zCoord, volume, pitch);
	}
}
