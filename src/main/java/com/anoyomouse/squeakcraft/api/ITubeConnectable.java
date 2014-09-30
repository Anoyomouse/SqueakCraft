package com.anoyomouse.squeakcraft.api;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Anoyomouse on 2014/09/29.
 */
public interface ITubeConnectable
{
	public boolean AcceptsItemsOnSide(ForgeDirection side);

	public boolean IsConnectedOnSide(ForgeDirection side);

	public Boolean CanRoute();

	public void SetConnectionOnSide(ForgeDirection side, boolean connected);

	public boolean IsConnectableOnSide(ForgeDirection side);
}
