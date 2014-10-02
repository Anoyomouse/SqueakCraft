package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntitySqueakCraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntitySqueakCraft extends MessageTileEntitySqueakBase implements IMessageHandler<MessageTileEntitySqueakCraft, IMessage>
{
	public MessageTileEntitySqueakCraft(TileEntitySqueakCraft tileEntitySqueakCraft)
	{
		super(tileEntitySqueakCraft);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
	}

	@Override
	public IMessage onMessage(MessageTileEntitySqueakCraft message, MessageContext ctx)
	{
		TileEntity tileEntity = this.getTileEntityMessage(message, ctx);

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntitySqueakCraft - %s", super.toString());
	}
}
