/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.client.renderer.item;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelTank;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTankValve;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
@SideOnly(Side.CLIENT)
public class ItemRendererTank implements IItemRenderer
{
	private final ModelTank modelTank;
	private final ModelTankValve modelTankValve;

	public ItemRendererTank()
	{
		modelTank = new ModelTank();
		modelTankValve = new ModelTankValve();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			case ENTITY:
				this.RenderTank(0F, 0F, 0F, item.getItemDamage());
				return;
			case EQUIPPED:
				this.RenderTank(0.5F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case EQUIPPED_FIRST_PERSON:
				this.RenderTank(0.0F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case INVENTORY:
				this.RenderTank(0.0F, 0.075F, 0.0F, item.getItemDamage());
				return;
			default:
			{
			}
		}
	}

	private void RenderTank(float x, float y, float z, int metaData)
	{
		// Bind texture
		if (metaData == 0 || metaData == 1 || metaData == 2)
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Model.TANK);
		else
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Model.TANK_VALVE);

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(1, 1, 1);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x, y, z);

		// Render
		if (metaData == 0)
			modelTank.renderPart(Names.ModelParts.TANK_WALL);
		else if(metaData == 1)
			modelTank.renderPart(Names.ModelParts.TANK_EDGE);
		else if (metaData == 2)
			modelTank.renderPart(Names.ModelParts.TANK_CORNER);
		else if (metaData == 3)
			modelTank.renderPart(Names.ModelParts.TANK_CORNER_SUPPORT);
		else if (metaData == 4)
			modelTankValve.renderPart(Names.ModelParts.TANK_VALVE);
		else if (metaData == 5)
			modelTankValve.renderPart(Names.ModelParts.TANK_VALVE_PIPE);

		GL11.glPopMatrix();
	}
}
