/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.proxy;

import com.anoyomouse.squeakcraft.client.renderer.item.*;
import com.anoyomouse.squeakcraft.client.renderer.tileentity.*;
import com.anoyomouse.squeakcraft.client.settings.KeyBindings;
import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.*;
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
		RenderIds.transportPipe = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.networkInterface = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.cratePile = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.tank = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.placementTank = RenderingRegistry.getNextAvailableRenderId();

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.stockPile), new ItemRendererStockPile());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.transportPipe), new ItemRendererTransportPipe());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.networkInterface), new ItemRendererNetworkInterface());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.tank), new ItemRendererTank());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.placementTank), new ItemRendererPlacementTank());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStockPile.class, new TileEntityRendererStockPile());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransportPipe.class, new TileEntityRendererTransportPipe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNetworkInterface.class, new TileEntityRendererNetworkInterface());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTank.class, new TileEntityRendererTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlacementTank.class, new TileEntityRendererPlacementTank());
	}

	@Override
	public void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch)
	{
		// ClientSoundHelper.playSound(soundName, xCoord, yCoord, zCoord, volume, pitch);
	}
}
