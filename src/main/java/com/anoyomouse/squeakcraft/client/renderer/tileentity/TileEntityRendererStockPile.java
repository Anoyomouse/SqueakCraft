/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelStockPile;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityRendererStockPile extends TileEntitySpecialRenderer
{
	private final ModelStockPile modelStockPile = new ModelStockPile();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		if (tileEntity instanceof TileEntityStockPile)
		{
			TileEntityStockPile tileEntityStockPile = (TileEntityStockPile) tileEntity;

			this.bindTexture(Textures.Model.STOCKPILE_BASE);

			GL11.glPushMatrix();
			// Scale, Translate, Rotate
			GL11.glScalef(1.0F, 1.0F, 1.0F);

			// Origin is at the center of the model
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef((float) x,(float) y,(float) z);

			// Render
			modelStockPile.renderPart(Names.ModelParts.STOCKPILE_BASE);

			for(int i = 0; i < 4; i++)
			{
				if (tileEntityStockPile.getStackInSlot(i) != null)
				{
					GL11.glPushMatrix();
					this.bindTexture(Textures.Model.STOCKPILE_CRATE);
					if (i == 0)
						modelStockPile.renderPart(Names.ModelParts.STOCKPILE_CRATE1);
					if (i == 1)
						modelStockPile.renderPart(Names.ModelParts.STOCKPILE_CRATE2);
					if (i == 2)
						modelStockPile.renderPart(Names.ModelParts.STOCKPILE_CRATE3);
					if (i == 3)
						modelStockPile.renderPart(Names.ModelParts.STOCKPILE_CRATE4);

					// Render

					GL11.glPopMatrix();
				}
			}
			GL11.glPopMatrix();
		}
	}
}
