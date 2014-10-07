/*
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.network.message;

import com.anoyomouse.squeakcraft.tileentity.TileEntityPlacementTank;
import com.anoyomouse.squeakcraft.utility.LogHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Anoyomouse on 2014/09/27.
 */
public class MessageTileEntityPlacementTank extends MessageTileEntitySqueakBase implements IMessageHandler<MessageTileEntityPlacementTank, IMessage>
{
	public byte connectedSides;
	private int layer;
	private boolean isMasterEntity;

	private int masterEntityLocationX;
	private int masterEntityLocationY;
	private int masterEntityLocationZ;

	public MessageTileEntityPlacementTank()
	{
		super();
		this.connectedSides = 0;
		this.layer = 0;
		this.isMasterEntity = false;
		this.masterEntityLocationX = 0;
		this.masterEntityLocationY = 0;
		this.masterEntityLocationZ = 0;
	}

	public MessageTileEntityPlacementTank(TileEntityPlacementTank tileEntityPlacementTank)
	{
		super(tileEntityPlacementTank);
		this.connectedSides = tileEntityPlacementTank.getConnectedSides();
		this.layer = tileEntityPlacementTank.getLayer();
		this.isMasterEntity = tileEntityPlacementTank.isMaster();

		int[] masterEntityLocation = tileEntityPlacementTank.getMasterEntityLocation();
		this.masterEntityLocationX = masterEntityLocation[0];
		this.masterEntityLocationY = masterEntityLocation[1];
		this.masterEntityLocationZ = masterEntityLocation[2];
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.connectedSides = buf.readByte();
		this.layer = buf.readInt();
		this.isMasterEntity = buf.readBoolean();
		this.masterEntityLocationX = buf.readInt();
		this.masterEntityLocationY = buf.readInt();
		this.masterEntityLocationZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeByte(this.connectedSides);
		buf.writeInt(this.layer);
		buf.writeBoolean(this.isMasterEntity);
		buf.writeInt(this.masterEntityLocationX);
		buf.writeInt(this.masterEntityLocationY);
		buf.writeInt(this.masterEntityLocationZ);
	}

	@Override
	public IMessage onMessage(MessageTileEntityPlacementTank message, MessageContext ctx)
	{
		TileEntity tileEntity = this.getTileEntityMessage(message, ctx);

		if (tileEntity instanceof TileEntityPlacementTank)
		{
			TileEntityPlacementTank tank = ((TileEntityPlacementTank) tileEntity);
			tank.setConnectedSides(message.connectedSides);
			tank.setLayer(message.layer);
			tank.setIsMaster(message.isMasterEntity);
			tank.setMasterEntityLocation(message.masterEntityLocationX, message.masterEntityLocationY, message.masterEntityLocationZ);
		}

		return null;
	}

	@Override
	public String toString()
	{
		return String.format("MessageTileEntityPlacementTank - %s, connectedSides:%s, layer:%s, isMaster:%s, M@ (%3d,%3d,%3d)", super.toString(), connectedSides, layer, isMasterEntity?"Yes":"No", masterEntityLocationX, masterEntityLocationY, masterEntityLocationZ);
	}
}
