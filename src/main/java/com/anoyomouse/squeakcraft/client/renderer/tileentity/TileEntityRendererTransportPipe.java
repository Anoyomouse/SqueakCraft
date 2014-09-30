package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelTransportPipe;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
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

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		if (tileEntity instanceof TileEntityTransportPipe)
		{
			TileEntityTransportPipe tileEntityTransportPipe = (TileEntityTransportPipe) tileEntity;

			this.bindTexture(Textures.Model.TRANSPORT_PIPE);

			GL11.glPushMatrix();
			// Scale, Translate, Rotate
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glTranslatef((float) x, (float) y, (float) z);

			if (tileEntityTransportPipe.isBiDirectional())
			{
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.UP))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_LONG);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.EAST))
				{
					GL11.glPushMatrix(); ////
					GL11.glTranslatef(1.0F, 0F, 0);
					GL11.glRotatef(90.0F, 0F, 0F, 1.0F);
					modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_LONG);
					GL11.glPopMatrix();
				}
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.NORTH))
				{
					GL11.glPushMatrix();
					GL11.glTranslatef(0F, 1.0F, 0);
					GL11.glRotatef(90.0F, 1.0F, 0F, 0F);
					modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_LONG);
					GL11.glPopMatrix();
				}
			}
			else
			{
				// Render
				modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_CORNER);

				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.UP))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER1);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.WEST))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER2);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.SOUTH))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER3);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.EAST))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER4);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.NORTH))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER5);
				if (tileEntityTransportPipe.IsConnectedOnSide(ForgeDirection.DOWN))
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER6);
			}

			GL11.glPopMatrix();
		}
	}

	private void renderCornerPiece(String partName)
	{
		GL11.glPushMatrix();
		modelTransportPipe.renderPart(partName);
		GL11.glPopMatrix();
	}
}
