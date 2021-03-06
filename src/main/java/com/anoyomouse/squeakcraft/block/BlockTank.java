/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.block;

import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTank;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class BlockTank extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockTank()
	{
		super(Material.iron);
		this.setHardness(1.0f);
		this.setBlockName(Names.Blocks.TANK);
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData)
	{
		return new TileEntityTank();
	}

	@Override
	public int damageDropped(int metaData)
	{
		return metaData;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return RenderIds.tank;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

	@Override
	//World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	public int onBlockPlaced(World World, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{
		LogHelper.info("Place tank block at (" + x + "," + y + "," + z + "):" + metadata);
		return super.onBlockPlaced(World, x, y, z, side, hitX, hitY, hitZ, metadata);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (player.isSneaking() && player.getCurrentEquippedItem() != null || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))
		{
			return true;
		}
		else
		{
			if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityTank)
			{
				TileEntityTank tank = (TileEntityTank)world.getTileEntity(x, y, z);

				LogHelper.info("Tank block at (" + x + "," + y + "," + z + "):" + world.getBlockMetadata(x, y, z) + " Facing: " + tank.getOrientation());

				// player.openGui(SqueakCraftMod.instance, GUIs.STOCKPILE.ordinal(), world, x, y, z);
			}

			return true;
		}
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData)
	{
		super.onBlockEventReceived(world, x, y, z, eventId, eventData);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(eventId, eventData);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
	{
		for(int i = 0; i < 6; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
}
