/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.block;

import com.anoyomouse.squeakcraft.api.ITubeConnectable;
import com.anoyomouse.squeakcraft.init.ModBlocks;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
import com.anoyomouse.squeakcraft.transport.TransportCrate;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class BlockTransportPipe extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockTransportPipe()
	{
		super(Material.iron);
		this.setHardness(2.0f);
		this.setBlockName(Names.Blocks.TRANSPORT_PIPE);
		float bbArea = 0.1510F; // 0.1875
		this.setBlockBounds(bbArea, bbArea, bbArea, 1.0F - bbArea, 1.0F - bbArea, 1.0F - bbArea);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData)
	{
		return new TileEntityTransportPipe();
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
		return RenderIds.transportPipe;
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
		list.add(new ItemStack(item, 1, 0));
	}

	// Lovingly Borrowed from: https://github.com/LazDude2012/YATS/blob/master/block/BlockTube.java
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
	{
		CheckTubeConnections(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock)
	{
		super.onNeighborBlockChange(world, x, y, z, neighborBlock);

		CheckTubeConnections(world, x, y, z);
		TileEntityTransportPipe tileEntityTransportPipe = (TileEntityTransportPipe)world.getTileEntity(x, y, z);
		if (tileEntityTransportPipe != null)
		{
			tileEntityTransportPipe.updateContainingBlockInfo();
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
	{
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);

		CheckTubeConnections((World)world, x, y, z);
		TileEntityTransportPipe tileEntityTransportPipe = (TileEntityTransportPipe) world.getTileEntity(x, y, z);
		if (tileEntityTransportPipe != null)
		{
			tileEntityTransportPipe.updateContainingBlockInfo();
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TileEntityTransportPipe tileEntityTransportPipe = (TileEntityTransportPipe)world.getTileEntity(x, y, z);
		if (tileEntityTransportPipe != null)
		{
			// Update world block
			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int a, float b, float c, float d)
	{
		if (!world.isRemote)
		{
			return false;
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityTransportPipe)
		{
			LogHelper.info("I was right clicked: " + x + " " + y + " " + z + " " + a + " " + b + " " + c + " " + d);

			TileEntityTransportPipe tep = ((TileEntityTransportPipe) te);
			tep.getContents().add(new TransportCrate(new ItemStack(ModBlocks.flag, 1), ForgeDirection.NORTH, 0));

			return true;
		}
		else
		{
			return super.onBlockActivated(world, x, y, z, entityPlayer, a, b, c, d);
		}
	}

	public static void CheckTubeConnections(World world, int x, int y, int z)
	{
		TileEntityTransportPipe originator = (TileEntityTransportPipe) world.getTileEntity(x, y, z);

		if (originator != null)
		{
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity tile = world.getTileEntity(x + side.offsetX, y + side.offsetY, z + side.offsetZ);
				if (tile instanceof ITubeConnectable)
				{
					ITubeConnectable tube = (ITubeConnectable) tile;
					if (tube.IsConnectableOnSide(side.getOpposite()))
					{
						tube.SetConnectionOnSide(side.getOpposite(), true);
						originator.SetConnectionOnSide(side, true);
					}
					else
					{
						tube.SetConnectionOnSide(side.getOpposite(), false);
						originator.SetConnectionOnSide(side, false);
					}
				}
				else if (tile instanceof IInventory)
				{
					originator.SetConnectionOnSide(side, true);
				}
				else originator.SetConnectionOnSide(side, false);
			}
		}
		else
		{
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity tile = world.getTileEntity(x + side.offsetX, y + side.offsetY, z + side.offsetZ);
				if (tile instanceof ITubeConnectable)
				{
					ITubeConnectable tube = (ITubeConnectable) tile;
					if (tube.IsConnectableOnSide(side.getOpposite()))
					{
						tube.SetConnectionOnSide(side.getOpposite(), false);
					}
					else
					{
						tube.SetConnectionOnSide(side.getOpposite(), false);
					}
				}
			}
		}
	}
}
