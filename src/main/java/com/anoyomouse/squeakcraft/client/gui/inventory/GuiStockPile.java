/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.client.gui.inventory;

import com.anoyomouse.squeakcraft.inventory.ContainerStockPile;
import com.anoyomouse.squeakcraft.reference.Colors;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiStockPile extends GuiContainer
{
	private TileEntityStockPile tileEntityStockPile;

	public GuiStockPile(InventoryPlayer inventoryPlayer, TileEntityStockPile tileEntityStockPile)
	{
		super(new ContainerStockPile(inventoryPlayer, tileEntityStockPile));
		this.tileEntityStockPile = tileEntityStockPile;

		xSize = 170;
		ySize = 123;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		// fontRendererObj.drawString(StatCollector.translateToLocal(tileEntityStockPile.getInventoryName()), 8, 6, Integer.parseInt(Colors.PURE_WHITE, 16));
		// fontRendererObj.drawString(StatCollector.translateToLocal(Names.Containers.VANILLA_INVENTORY), 35, ySize - 95 + 2, Integer.parseInt(Colors.PURE_WHITE, 16));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(Textures.Gui.STOCKPILE);

		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
}
