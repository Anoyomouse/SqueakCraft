package com.anoyomouse.squeakcraft.block;

import com.anoyomouse.squeakcraft.SqueakCraftMod;
import com.anoyomouse.squeakcraft.reference.GUIs;
import com.anoyomouse.squeakcraft.reference.Names;
import com.anoyomouse.squeakcraft.reference.RenderIds;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
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
public class BlockTransportPipe extends BlockSqueakCraft implements ITileEntityProvider
{
	public BlockTransportPipe()
	{
		super(Material.iron);
		this.setHardness(2.0f);
		this.setBlockName(Names.Blocks.TRANSPORT_PIPE);
		float bbArea = 0.1825F; // 0.1875
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
}
