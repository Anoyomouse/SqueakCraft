/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.client.renderer.item;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelStockPile;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Textures;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import cpw.mods.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
@SideOnly(Side.CLIENT)
public class ItemRendererStockPile implements IItemRenderer
{
	private final ModelStockPile modelStockPile;

	public ItemRendererStockPile()
	{
		modelStockPile = new ModelStockPile();
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
				this.RenderStockPile(0F, 0F, 0F, item.getItemDamage());
				return;
			case EQUIPPED:
				this.RenderStockPile(0.5F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case EQUIPPED_FIRST_PERSON:
				this.RenderStockPile(0.0F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case INVENTORY:
				this.RenderStockPile(0.0F, 0.075F, 0.0F, item.getItemDamage());
				return;
			default:
			{
			}
		}
	}

	private void RenderStockPile(float x, float y, float z, int metaData)
	{
		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Model.STOCKPILE_BASE);

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(1, 1, 1);
		GL11.glTranslatef(x, y, z);

		// Render
		modelStockPile.renderPart(Names.ModelParts.STOCKPILE_BASE);

		GL11.glPopMatrix();
	}
}
