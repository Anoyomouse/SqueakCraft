package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntitySqueakCraft;
import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntityTransportPipe extends MessageTileEntitySqueakBase implements IMessageHandler<MessageTileEntityTransportPipe, IMessage>
{
	public byte connectedSides;

	public MessageTileEntityTransportPipe(TileEntityTransportPipe tileEntityTransportPipe)
	{
		super(tileEntityTransportPipe);
		this.connectedSides = tileEntityTransportPipe.getConnectedSidesByte();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.connectedSides = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeByte(connectedSides);
	}

	@Override
	public IMessage onMessage(MessageTileEntityTransportPipe message, MessageContext ctx)
	{
		TileEntity tileEntity = this.getTileEntityMessage(message, ctx);

		if (tileEntity instanceof TileEntityTransportPipe)
		{
			((TileEntityTransportPipe) tileEntity).setConnectedSidesByte(message.connectedSides);
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityTransportPipe - %s, connectedSides:%s", super.toString(), connectedSides);
	}
}
