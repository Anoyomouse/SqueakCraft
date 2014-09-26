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

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class ItemBlockStockPile extends ItemBlock
{
	public ItemBlockStockPile(Block block)
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

		list.add(StatCollector.translateToLocal("tooltip." + Reference.MODID + ":" + Names.Blocks.STOCKPILE));
	}
}
