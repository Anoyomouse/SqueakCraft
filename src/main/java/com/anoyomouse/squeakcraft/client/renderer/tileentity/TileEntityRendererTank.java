/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelTank;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityRendererTank extends TileEntitySpecialRenderer
{
	private final ModelTank modelTank = new ModelTank();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		if (tileEntity instanceof TileEntityTank)
		{
			TileEntityTank tileEntityTank = (TileEntityTank) tileEntity;
			Block block = tileEntityTank.getWorldObj().getBlock(tileEntityTank.xCoord, tileEntityTank.yCoord, tileEntityTank.zCoord);

			this.bindTexture(Textures.Model.TANK);

			GL11.glPushMatrix();
			// Scale, Translate, Rotate
			GL11.glScalef(1.0F, 1.0F, 1.0F);

			// Origin is at the center of the model
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef((float) x,(float) y,(float) z);

			// Render
			modelTank.renderPart(Names.ModelParts.TANK_WALL);

			GL11.glPopMatrix();
		}
	}
}
