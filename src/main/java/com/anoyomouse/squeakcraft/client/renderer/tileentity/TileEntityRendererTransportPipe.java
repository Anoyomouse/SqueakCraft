package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelTransportPipe;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
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

			// Render
			modelTransportPipe.renderPart(Names.ModelParts.TRANSPORT_PIPE_CORNER);

			int edges = tileEntityTransportPipe.edgeDetect((int) x, (int) y, (int) z);
			if (edges > 0)
			{
				if ((edges & 1) != 0)
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER1);
				if ((edges & 2) != 0)
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER2);
				if ((edges & 4) != 0)
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER3);
				if ((edges & 8) != 0)
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER4);
				if ((edges & 16) != 0)
					renderCornerPiece(Names.ModelParts.TRANSPORT_PIPE_CORNER5);
				if ((edges & 32) != 0)
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
