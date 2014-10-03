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
import com.anoyomouse.squeakcraft.transport.TransportCrate;
import com.anoyomouse.squeakcraft.utility.LogHelper;
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
	private float posFloat;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
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

			for (TransportCrate crate : tileEntityTransportPipe.getContents())
			{
				GL11.glPushMatrix();
				posFloat = this.getProgressFloat(crate.getProgress());
				if (crate.getHeading() == ForgeDirection.UP)
					GL11.glTranslatef(0.0F, posFloat, 0.0F);
				else if (crate.getHeading() == ForgeDirection.DOWN)
					GL11.glTranslatef(0.0F, -posFloat, 0.0F);
				else if (crate.getHeading() == ForgeDirection.EAST)
					GL11.glTranslatef(posFloat, 0.0F, 0.0F);
				else if (crate.getHeading() == ForgeDirection.WEST)
					GL11.glTranslatef(-posFloat, 0.0F, 0.0F);
				else if (crate.getHeading() == ForgeDirection.NORTH)
					GL11.glTranslatef(0.0F, 0.0F, -posFloat);
				else if (crate.getHeading() == ForgeDirection.SOUTH)
					GL11.glTranslatef(0.0F, 0.0F, posFloat);

				this.bindTexture(Textures.Model.CRATE);
				modelCrate.renderPart(Names.ModelParts.CRATE);
				GL11.glPopMatrix();
			}

			/*
			if (tileEntityTransportPipe.getContents().size() == 0)
			{
				GL11.glPushMatrix();
				this.bindTexture(Textures.Model.CRATE);
				modelCrate.renderPart(Names.ModelParts.CRATE);
				GL11.glPopMatrix();
			}
			*/

			GL11.glPopMatrix();
		}
	}

	private float getProgressFloat(int progress)
	{
		if (progress < 0) progress = 0;
		if (progress > 100) progress = 100;
		float output = (float)progress / 100.0f;
		return output - 0.5f;
	}

	private void renderPipePiece(String partName)
	{
		modelTransportPipe.renderPart(partName);
	}
}
