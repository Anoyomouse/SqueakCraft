/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.client.renderer.item;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelBox;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTank;
import com.anoyomouse.squeakcraft.client.renderer.model.ModelTankValve;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
@SideOnly(Side.CLIENT)
public class ItemRendererPlacementTank implements IItemRenderer
{
	private final ModelBox modelBox;

	public ItemRendererPlacementTank()
	{
		modelBox = new ModelBox();
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
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Blocks.PLACEMENT_TANK);

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(1, 1, 1);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x, y, z);

		// Render
		modelBox.render();

		GL11.glPopMatrix();
	}
}
