/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */

package com.anoyomouse.squeakcraft.block;

import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityPlacementTank;
import com.anoyomouse.squeakcraft.utility.HelperUtilities;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/10/05.
 */
public class BlockPlacementTank extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockPlacementTank()
	{
		super(Material.iron);
		this.setHardness(1.0f);
		this.setBlockName(Names.Blocks.TANK);
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData)
	{
		return new TileEntityPlacementTank();
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return RenderIds.placementTank;
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
	public void onBlockAdded(World world, int x, int y, int z)
	{
		LogHelper.info("Tank block added at (" + HelperUtilities.getSideChar(world) + ") - (" + x + "," + y + "," + z + ")");
		super.onBlockAdded(world, x, y, z);

		// Update TE
		if (!world.isRemote)
		{
			this.updateConnectedBlocks(world, x, y, z);
		}
	}

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int metadata)
	{
		super.onBlockPreDestroy(world, x, y, z, metadata);

		LogHelper.info("Tank block breaking at (" + HelperUtilities.getSideChar(world) + ") - (" + x + "," + y + "," + z + "):" + metadata);

		if (!world.isRemote)
		{
			TileEntityPlacementTank myTileEntity = (TileEntityPlacementTank) world.getTileEntity(x, y, z);
			myTileEntity.markDeleted();
			if (myTileEntity.isMaster())
			{
				/*if (myTileEntity.getChildren().size() > 0)
				{
					TileEntityPlacementTank newMaster = myTileEntity.getChildren().get(0);
					newMaster.takeOverMaster(myTileEntity);
				}*/
			}
			else
			{
				this.updateConnectedBlocks(world, x, y, z);
			}
		}
		// Update TE
	}

	public void updateConnectedBlocks(World world, int x, int y, int z)
	{
		if (world.isRemote)
		{
			return;
		}

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || !(tileEntity instanceof TileEntityPlacementTank))
			return;

		TileEntityPlacementTank tankEntityPlacementTank = (TileEntityPlacementTank)tileEntity;
		boolean foundAdjacentBlock = false;

		TileEntityPlacementTank masterEntity = null;
		for (ForgeDirection checkDir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity te = world.getTileEntity(x + checkDir.offsetX, y + checkDir.offsetY, z + checkDir.offsetZ);
			if (te instanceof TileEntityPlacementTank)
			{
				if (!((TileEntityPlacementTank) te).isDeleted())
				{
					foundAdjacentBlock = true;
					TileEntityPlacementTank tankEntity = (TileEntityPlacementTank) te;
					if (tankEntity.isMaster())
					{
						if (masterEntity != null && masterEntity != tankEntity)
						{
							//masterEntity.takeOverMaster(tankEntity);
						}

						if (masterEntity == null)
						{
							masterEntity = tankEntity;
							tankEntityPlacementTank.setMasterEntityLocation(masterEntity.xCoord, masterEntity.yCoord, masterEntity.zCoord);
							//masterEntity.getChildren().add(myTileEntity);
						}
					}
					else
					{
						if (masterEntity == null)
						{
							masterEntity = tankEntity.getMasterEntity();
							tankEntityPlacementTank.setMasterEntityLocation(masterEntity.xCoord, masterEntity.yCoord, masterEntity.zCoord);
						}
						else if (tankEntity.getMasterEntity() != masterEntity)
						{
						//	tankEntity.setMasterEntity(masterEntity);
						}
					}
				}
			}
		}

		if (!foundAdjacentBlock)
		{
			tankEntityPlacementTank.setIsMaster(true);
			tankEntityPlacementTank.setMasterEntityLocation(x, y, z);
		}
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int a, float b, float c, float d)
	{
		if (world.isRemote)
		{
			return true;
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityPlacementTank)
		{
			LogHelper.info("BlockPlacementTank Activate - " + te.toString());
			return true;
		}
		else
		{
			return super.onBlockActivated(world, x, y, z, entityPlayer, a, b, c, d);
		}
	}

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData)
	{
		super.onBlockEventReceived(world, x, y, z, eventId, eventData);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity.receiveClientEvent(eventId, eventData);
	}
}
