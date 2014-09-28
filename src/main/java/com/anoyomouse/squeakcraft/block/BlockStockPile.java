package com.anoyomouse.squeakcraft.block;

import com.anoyomouse.squeakcraft.SqueakCraftMod;
import com.anoyomouse.squeakcraft.reference.GUIs;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * Created by Anoyomouse on 2014/09/26.
 */
public class BlockStockPile extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockStockPile()
	{
		super(Material.wood);
		this.setHardness(1.0f);
		this.setBlockName(Names.Blocks.STOCKPILE);
		this.setBlockBounds(0, 0, 0, 1, 0.5f, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData)
	{
		return new TileEntityStockPile();
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
		return RenderIds.stockpile;
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

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int xCoord, int yCoord, int zCoord)
	{
		this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
		return super.getSelectedBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int xCoord, int yCoord, int zCoord)
	{
		this.setBlockBoundsBasedOnState(world, xCoord, yCoord, zCoord);
		return super.getCollisionBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityStockPile)
		{
			TileEntityStockPile sp = (TileEntityStockPile)te;
			int flags = sp.getInventoryItemFlags();

			if (flags == 0)
			{
				this.setBlockBounds(0, 0, 0, 1F, 0.5F, 1F);
			}
			else
			{
				this.setBlockBounds(0, 0, 0, 1F, 1F, 1F);
			}
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1F, 1F, 1F);
		}
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity)
	{
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
		super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);

		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityStockPile)
		{
			TileEntityStockPile sp = (TileEntityStockPile) te;
			int flags = sp.getInventoryItemFlags();

			if (flags == 0)
			{
				return;
			}

			if ((flags & 1) != 0)
			{
				this.setBlockBounds(0F, 0.5F, 0F, 0.5F, 1.0F, 0.5F);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}

			if ((flags & 2) != 0)
			{
				this.setBlockBounds(0.5F, 0.5F, 0F, 1.0F, 1.0F, 0.5F);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}

			if ((flags & 4) != 0)
			{
				this.setBlockBounds(0F, 0.5F, 0.5F, 0.5F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}

			if ((flags & 8) != 0)
			{
				this.setBlockBounds(0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
			}
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
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
			if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileEntityStockPile)
			{
					player.openGui(SqueakCraftMod.instance, GUIs.STOCKPILE.ordinal(), world, x, y, z);
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
		list.add(new ItemStack(item, 1, 0));
	}
}
