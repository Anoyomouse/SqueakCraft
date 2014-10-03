/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 */
package com.anoyomouse.squeakcraft.transport;

import com.anoyomouse.squeakcraft.reference.Names;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/10/02.
 */
public class TransportCrate
{
	private ItemStack contents;
	private ForgeDirection heading;
	private int progressPercent;

	public TransportCrate(ItemStack itemStack, ForgeDirection heading, int progress)
	{
		this.contents = itemStack;
		this.heading = heading;
		this.progressPercent = progress;
	}

	public int getProgress()
	{
		return this.progressPercent;
	}

	public void setProgress(int progress)
	{
		this.progressPercent = progress;
	}

	public ForgeDirection getHeading()
	{
		return heading;
	}

	public void setHeading(ForgeDirection heading)
	{
		this.heading = heading;
	}

	public NBTTagCompound getNBTTagData()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger(Names.NBT.CRATE_HEADING, this.heading.ordinal());
		tag.setInteger(Names.NBT.CRATE_PROGRESS, this.progressPercent);
		NBTTagCompound item = new NBTTagCompound();
		contents.writeToNBT(item);
		tag.setTag(Names.NBT.CRATE_CONTENTS, item);
		return tag;
	}

	public static TransportCrate loadTransportCrateFromNBTTag(NBTTagCompound tag)
	{
		ForgeDirection heading = ForgeDirection.getOrientation(tag.getInteger(Names.NBT.CRATE_HEADING));
		int progress = tag.getInteger(Names.NBT.CRATE_PROGRESS);
		ItemStack contents = ItemStack.loadItemStackFromNBT(tag.getCompoundTag(Names.NBT.CRATE_CONTENTS));

		return new TransportCrate(contents, heading, progress);
	}

	public void writeByteBufData(ByteBuf data)
	{
		data.writeByte(this.heading.ordinal());
		data.writeByte(this.progressPercent);

		ByteBufUtils.writeItemStack(data, this.contents);
	}

	public static TransportCrate readByteBufData(ByteBuf data)
	{
		ForgeDirection heading = ForgeDirection.getOrientation(data.readByte());
		int progress = data.readByte();

		ItemStack item = ByteBufUtils.readItemStack(data);

		return new TransportCrate(item, heading, progress);
	}

	public void addProgress(int addProgress)
	{
		if (this.progressPercent < 50 && this.progressPercent + addProgress >= 50)
		{
			this.progressPercent = 50;
		}
		else
		{
			this.progressPercent += addProgress;
		}
	}
}
