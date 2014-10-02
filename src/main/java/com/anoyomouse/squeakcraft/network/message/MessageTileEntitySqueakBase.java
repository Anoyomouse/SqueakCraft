/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntitySqueakCraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/10/01.
 */
public abstract class MessageTileEntitySqueakBase implements IMessage
{
	// This is the data needed for a base TileEntitySqueakCraft
	public int x, y, z;
	public byte orientation, state;
	public String customName, owner;

	protected MessageTileEntitySqueakBase()
	{
		x = y = z = 0;
		orientation = 0; state = 0;
		customName = ""; owner = "";
	}

	protected MessageTileEntitySqueakBase(TileEntitySqueakCraft tileEntitySqueakCraft)
	{
		this.x = tileEntitySqueakCraft.xCoord;
		this.y = tileEntitySqueakCraft.yCoord;
		this.z = tileEntitySqueakCraft.zCoord;
		this.orientation = (byte) tileEntitySqueakCraft.getOrientation().ordinal();
		this.state = (byte) tileEntitySqueakCraft.getState();
		this.customName = tileEntitySqueakCraft.getCustomName();
		this.owner = tileEntitySqueakCraft.getOwner();
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

	protected TileEntity getTileEntityMessage(MessageTileEntitySqueakBase message, MessageContext ctx)
	{
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntitySqueakCraft)
		{
			((TileEntitySqueakCraft) tileEntity).setOrientation(message.orientation);
			((TileEntitySqueakCraft) tileEntity).setState(message.state);
			((TileEntitySqueakCraft) tileEntity).setCustomName(message.customName);
			((TileEntitySqueakCraft) tileEntity).setOwner(message.owner);
		}

		return tileEntity;
	}

	@Override
	public String toString()
	{
		return String.format("x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s", x, y, z, orientation, state, customName, owner);
	}
}
