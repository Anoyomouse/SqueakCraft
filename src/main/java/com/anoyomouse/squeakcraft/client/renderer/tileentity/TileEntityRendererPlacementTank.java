/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.client.renderer.tileentity;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelBox;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTank;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTankValve;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.tileentity.TileEntityPlacementTank;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class TileEntityRendererPlacementTank extends TileEntitySpecialRenderer
{
	private final ModelBox modelBox = new ModelBox();
	private final ModelTank modelTank = new ModelTank();
	private final ModelTankValve modelTankValve = new ModelTankValve();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick)
	{
		if (tileEntity instanceof TileEntityPlacementTank)
		{
			TileEntityPlacementTank tileEntityTank = (TileEntityPlacementTank) tileEntity;

			/*
			int metadata = tileEntityTank.getBlockMetadata();

			if ( metadata == 0 || metadata == 1 || metadata == 2 )
				this.bindTexture(Textures.Model.TANK);
			else
				this.bindTexture(Textures.Model.TANK_VALVE);
			*/
			if (tileEntityTank.isMaster())
			{
				this.bindTexture(Textures.Blocks.GOLD_TANK);
			}
			else
				this.bindTexture(Textures.Blocks.PLACEMENT_TANK);

			GL11.glPushMatrix();
			// Scale, Translate, Rotate
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			ForgeDirection dir = tileEntityTank.getOrientation();

			// Origin is at the center of the model
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glTranslatef((float) x,(float) y,(float) z);

			modelBox.render();

			/*
			boolean []tankCheck = new boolean[6];
			World worldObj = tileEntityTank.getWorldObj();
			int xC = tileEntityTank.xCoord, yC = tileEntityTank.yCoord, zC = tileEntityTank.zCoord;
			for (ForgeDirection checkDir : ForgeDirection.VALID_DIRECTIONS)
			{
				tankCheck[checkDir.ordinal()] = worldObj.getBlock(xC + checkDir.offsetX, yC + checkDir.offsetY, zC + checkDir.offsetZ) instanceof BlockTank;
			}

			int layer = 1;
			if (!tankCheck[ForgeDirection.UP.ordinal()] && tankCheck[ForgeDirection.DOWN.ordinal()])
			{
				layer = 0;
			}
			if (tankCheck[ForgeDirection.UP.ordinal()] && !tankCheck[ForgeDirection.DOWN.ordinal()])
			{
				layer = 2;
			}

			if (metadata == 1 || metadata == 2)
			{
				// Top Layer!
				if (layer == 0)
				{
					// Edges
					if (metadata == 1)
					{
						if (dir == ForgeDirection.NORTH)
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.SOUTH)
							GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.WEST)
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
						// The model is east!
					}

					if (metadata == 2)
					{
						if (dir == ForgeDirection.NORTH)
						{
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
						}
						else if (dir == ForgeDirection.SOUTH)
						{
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
						}
						else if (dir == ForgeDirection.EAST)
						{
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
							GL11.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
						}
						else if (dir == ForgeDirection.WEST)
							GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
					}
				}

				if (layer == 2)
				{
					if (metadata == 1)
					{
						GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
						if (dir == ForgeDirection.NORTH)
							GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.SOUTH)
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.WEST)
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
					}

					if (metadata == 2)
					{
						GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
						if (dir == ForgeDirection.SOUTH)
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.EAST)
						{
							GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
						}
						else if (dir == ForgeDirection.NORTH)
							GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
						else if (dir == ForgeDirection.WEST)
						{
							GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
						}
					}
				}
			}

			// Middle Layer && Edges
			if (layer == 1 && metadata == 1)
			{
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				// South is fine
				if (dir == ForgeDirection.EAST)
				{
					GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				}
				else if (dir == ForgeDirection.WEST)
				{
					GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				}
				else if (dir == ForgeDirection.NORTH)
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
				}
			}

			if (metadata == 0)
			{
				// (dir == ForgeDirection.UP) // Do Nothing for UP
				if (dir == ForgeDirection.DOWN)
				{
					GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				}
				else if (dir == ForgeDirection.NORTH)
				{
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				}
				else if (dir == ForgeDirection.SOUTH)
				{
					GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				}
				else if (dir == ForgeDirection.EAST)
				{
					GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				}
				else if (dir == ForgeDirection.WEST)
				{
					GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
				}
			}

			if (metadata == 4)
			{
				if (dir == ForgeDirection.DOWN)
				{
					GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				}
			}

			// Render
			if (metadata == 0)
				modelTank.renderPart(Names.ModelParts.TANK_WALL);
			else if (metadata == 1)
				modelTank.renderPart(Names.ModelParts.TANK_EDGE);
			else if (metadata == 2)
				modelTank.renderPart(Names.ModelParts.TANK_CORNER);
			else if (metadata == 3)
				modelTank.renderPart(Names.ModelParts.TANK_CORNER_SUPPORT);
			else if (metadata == 4)
				modelTankValve.renderPart(Names.ModelParts.TANK_VALVE);
			else if (metadata == 5)
				modelTankValve.renderPart(Names.ModelParts.TANK_VALVE_PIPE);
			*/

			GL11.glPopMatrix();
		}
	}
}
