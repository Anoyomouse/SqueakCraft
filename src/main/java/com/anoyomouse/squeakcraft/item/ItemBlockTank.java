/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.item;

import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class ItemBlockTank extends ItemBlock
{
	public ItemBlockTank(Block block)
	{
		super(block);
		this.setHasSubtypes(false);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean flag)
	{
		// TODO Localize and add more descriptive text
		int metaData = itemStack.getItemDamage();

		list.add(StatCollector.translateToLocal("tooltip." + Reference.MODID + ":" + Names.Blocks.TANK + ":" + metaData));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
	}
}
