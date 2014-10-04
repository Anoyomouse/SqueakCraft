/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.client.renderer.item;

import com.anoyomouse.squeakcraft.client.renderer.model.ModelNetworkInterface;
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
public class ItemRendererNetworkInterface implements IItemRenderer
{
	private final ModelNetworkInterface modelNetworkInterface;

	public ItemRendererNetworkInterface()
	{
		modelNetworkInterface = new ModelNetworkInterface();
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
				this.RenderNetworkInterface(0F, 0F, 0F, item.getItemDamage());
				return;
			case EQUIPPED:
				this.RenderNetworkInterface(0.5F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case EQUIPPED_FIRST_PERSON:
				this.RenderNetworkInterface(0.0F, 0.25F, 0.0F, item.getItemDamage());
				return;
			case INVENTORY:
				this.RenderNetworkInterface(0.0F, 0.075F, 0.0F, item.getItemDamage());
				return;
			default:
			{
			}
		}
	}

	private void RenderNetworkInterface(float x, float y, float z, int metaData)
	{
		// Bind texture
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.Model.NETWORK_INTERFACE);

		GL11.glPushMatrix();

		// Scale, Translate, Rotate
		GL11.glScalef(1, 1, 1);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(x, y, z);

		// Render
		modelNetworkInterface.renderPart(Names.ModelParts.NETWORK_INTERFACE);

		GL11.glPopMatrix();
	}
}
