package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntitySqueakCraft;
import com.anoyomouse.squeakcraft.tileentity.TileEntityStockPile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntityStockPile implements IMessage, IMessageHandler<MessageTileEntityStockPile, IMessage>
{
	public int x, y, z;
	public byte orientation, state;
	public String customName, owner;

	public MessageTileEntityStockPile()
	{
	}

	public MessageTileEntityStockPile(TileEntityStockPile tileEntityStockPile)
	{
		this.x = tileEntityStockPile.xCoord;
		this.y = tileEntityStockPile.yCoord;
		this.z = tileEntityStockPile.zCoord;
		this.orientation = (byte) tileEntityStockPile.getOrientation().ordinal();
		this.state = (byte) tileEntityStockPile.getState();
		this.customName = tileEntityStockPile.getCustomName();
		this.owner = tileEntityStockPile.getOwner();

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.orientation = buf.readByte();
		this.state = buf.readByte();
		int customNameLength = buf.readInt();
		this.customName = new String(buf.readBytes(customNameLength).array());
		int ownerLength = buf.readInt();
		this.owner = new String(buf.readBytes(ownerLength).array());

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(orientation);
		buf.writeByte(state);
		buf.writeInt(customName.length());
		buf.writeBytes(customName.getBytes());
		buf.writeInt(owner.length());
		buf.writeBytes(owner.getBytes());

	}

	@Override
	public IMessage onMessage(MessageTileEntityStockPile message, MessageContext ctx)
	{
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityStockPile)
		{
			((TileEntitySqueakCraft) tileEntity).setOrientation(message.orientation);
			((TileEntitySqueakCraft) tileEntity).setState(message.state);
			((TileEntitySqueakCraft) tileEntity).setCustomName(message.customName);
			((TileEntitySqueakCraft) tileEntity).setOwner(message.owner);

		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityStockPile - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s", x, y, z, orientation, state, customName, owner);
	}
}
