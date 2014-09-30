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
public class MessageTileEntityTransportPipe implements IMessage, IMessageHandler<MessageTileEntityTransportPipe, IMessage>
{
	public int x, y, z;
	public byte orientation, state;
	public byte connectedSides;
	public String customName, owner;

	public MessageTileEntityTransportPipe()
	{
	}

	public MessageTileEntityTransportPipe(TileEntityTransportPipe tileEntityTransportPipe)
	{
		this.x = tileEntityTransportPipe.xCoord;
		this.y = tileEntityTransportPipe.yCoord;
		this.z = tileEntityTransportPipe.zCoord;
		this.orientation = (byte) tileEntityTransportPipe.getOrientation().ordinal();
		this.state = (byte) tileEntityTransportPipe.getState();
		this.customName = tileEntityTransportPipe.getCustomName();
		this.owner = tileEntityTransportPipe.getOwner();

		this.connectedSides = tileEntityTransportPipe.getConnectedSidesByte();
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

		this.connectedSides = buf.readByte();
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

		buf.writeByte(connectedSides);
	}

	@Override
	public IMessage onMessage(MessageTileEntityTransportPipe message, MessageContext ctx)
	{
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityTransportPipe)
		{
			((TileEntitySqueakCraft) tileEntity).setOrientation(message.orientation);
			((TileEntitySqueakCraft) tileEntity).setState(message.state);
			((TileEntitySqueakCraft) tileEntity).setCustomName(message.customName);
			((TileEntitySqueakCraft) tileEntity).setOwner(message.owner);

			((TileEntityTransportPipe) tileEntity).setConnectedSidesByte(message.connectedSides);
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityTransportPipe - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s, connectedSides:%s", x, y, z, orientation, state, customName, owner, connectedSides);
	}
}
