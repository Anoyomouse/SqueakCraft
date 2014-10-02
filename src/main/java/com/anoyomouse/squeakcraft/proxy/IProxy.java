/**
 * Copyright (c) David-John Miller AKA Anoyomouse 2014
 *
 * See LICENCE in the project directory for licence information
 **/
package com.anoyomouse.squeakcraft.proxy;

public interface IProxy
{
	public abstract void registerKeyBindings();

	public abstract void initRenderingAndTextures();

	public abstract void registerEventHandlers();

	public abstract void registerTileEntities();

	public abstract void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch);
}
