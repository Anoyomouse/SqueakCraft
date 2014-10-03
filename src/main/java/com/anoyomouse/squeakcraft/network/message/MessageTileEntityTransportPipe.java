/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntityTransportPipe;
import com.anoyomouse.squeakcraft.transport.TransportCrate;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import sun.plugin2.message.transport.Transport;

import java.util.ArrayList;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntityTransportPipe extends MessageTileEntitySqueakBase implements IMessageHandler<MessageTileEntityTransportPipe, IMessage>
{
	public byte connectedSides;
	public ArrayList<TransportCrate> contents;

	public MessageTileEntityTransportPipe()
	{
		super();
		connectedSides = 0;
	}

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
		if (this.contents == null) this.contents = new ArrayList<TransportCrate>();
		else this.contents.clear();

		int numItems = buf.readInt();
		for(int i = 0; i < numItems; i++)
		{
			this.contents.add(TransportCrate.readByteBufData(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeByte(this.connectedSides);
		if (this.contents != null )
		{
			buf.writeInt(this.contents.size());
			for (TransportCrate crate : this.contents)
			{
				crate.writeByteBufData(buf);
			}
		}
		else
		{
			buf.writeInt(0);
		}
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
