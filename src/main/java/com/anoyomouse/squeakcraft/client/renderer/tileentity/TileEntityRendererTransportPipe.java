/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelCrate;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTransportPipe;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityRendererTransportPipe extends TileEntitySpecialRenderer
{
	private final ModelTransportPipe modelTransportPipe = new ModelTransportPipe();
	private final ModelCrate modelCrate = new ModelCrate();

	private int position = 0;
	private int ticker = 0;
	private float posFloat;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		ticker ++;
		if (ticker > 100)
		{
			ticker = 0;

			position += 1;
			if (position > 100)
			{
				position = 0;
			}
		}

		posFloat = (float)position / 100.0F;
		byte direction = 0;

		if (tileEntity instanceof TileEntityTransportPipe)
		{
			TileEntityTransportPipe tileEntityTransportPipe = (TileEntityTransportPipe) tileEntity;

			this.bindTexture(Textures.Model.TRANSPORT_PIPE);

			GL11.glPushMatrix();
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			if (tileEntityTransportPipe.isBiDirectional())
			{
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.UP))
				{
					GL11.glTranslatef((float) x, (float) y, (float) z);
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_LONG);

					GL11.glTranslatef(0.0F, direction == 1 ? 1.0F - posFloat : posFloat, 0.0F);
					this.bindTexture(Textures.Model.CRATE);
					modelCrate.renderPart(Names.ModelParts.CRATE);
				}
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.EAST))
				{
					GL11.glTranslatef((float) x, (float) y, (float) z);
					GL11.glPushMatrix();
					GL11.glRotatef(90.0F, 0F, 0F, 1.0F);
					GL11.glTranslatef(0.0F, -1.0F, 0F);
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
					modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_LONG);
					GL11.glPopMatrix();

					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
					GL11.glTranslatef(direction == 1 ? 1.0F - posFloat : posFloat, 0.0F, 0.0F);
					this.bindTexture(Textures.Model.CRATE);
					modelCrate.renderPart(Names.ModelParts.CRATE);
				}
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.NORTH))
				{
					GL11.glTranslatef((float) x, (float) y, (float) z);
					GL11.glPushMatrix();
					GL11.glRotatef(90.0F, 1.0F, 0F, 0F);
					GL11.glTranslatef(0F, 0F, -1.0F);
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
					modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_LONG);
					GL11.glPopMatrix();

					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
					GL11.glTranslatef(0.0F, 0.0F, direction == 1 ? 1.0F - posFloat : posFloat);
					this.bindTexture(Textures.Model.CRATE);
					modelCrate.renderPart(Names.ModelParts.CRATE);

				}
			}
			else
			{
				GL11.glTranslatef((float) x, (float) y, (float) z);
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER);

				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.UP))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER1);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.WEST))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER2);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.SOUTH))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER3);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.EAST))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER4);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.NORTH))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER5);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.DOWN))
					renderPipePiece(Names.ModelParts.TRANSPORT_PIPE_CORNER6);
			}

			GL11.glPopMatrix();
		}
	}

	private void renderPipePiece(String partName)
	{
		modelTransportPipe.renderPart(partName);
	}
}
