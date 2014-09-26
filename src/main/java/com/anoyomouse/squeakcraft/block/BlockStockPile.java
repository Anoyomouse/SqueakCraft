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
public class BlockStockPile extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockStockPile()
	{
		super(Material.wood);
		this.setHardness(1.0f);
		this.setBlockName(Names.Blocks.STOCKPILE);
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
