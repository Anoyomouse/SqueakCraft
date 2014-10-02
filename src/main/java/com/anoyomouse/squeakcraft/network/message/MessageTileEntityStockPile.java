package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntitySqueakCraft;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntityStockPile extends MessageTileEntitySqueakBase implements IMessageHandler<MessageTileEntityStockPile, IMessage>
{
	public int x, y, z;
	public byte orientation, state;
	public String customName, owner;
	public ItemStack[] inventorySlots;

	public MessageTileEntityStockPile(TileEntityStockPile tileEntityStockPile)
	{
		super(tileEntityStockPile);

		this.inventorySlots = new ItemStack[4];
		for(int i = 0;i < 4; i++)
		{
			this.inventorySlots[i] = tileEntityStockPile.getStackInSlot(i);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		this.inventorySlots = new ItemStack[4];
		for (int i = 0; i < 4; i++)
		{
			this.inventorySlots[i] = ByteBufUtils.readItemStack(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		for (int i = 0; i < 4; i++)
		{
			ByteBufUtils.writeItemStack(buf, this.inventorySlots[i]);
		}
	}

	@Override
	public IMessage onMessage(MessageTileEntityStockPile message, MessageContext ctx)
	{
		TileEntity tileEntity = super.getTileEntityMessage(message, ctx);

		if (tileEntity instanceof TileEntityStockPile)
		{
			for(int i = 0; i < 4; i++)
			{
				((TileEntityStockPile) tileEntity).setInventorySlotContents(i, message.inventorySlots[i]);
			}
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityStockPile - %s, inventorySlots:%s", super.toString(), inventorySlots);
	}
}
