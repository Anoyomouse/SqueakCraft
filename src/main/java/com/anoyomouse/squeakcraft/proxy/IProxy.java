package com.anoyomouse.squeakcraft.proxy;

public interface IProxy
{
	public abstract void registerKeyBindings();

	public abstract void initRenderingAndTextures();

	public abstract void registerEventHandlers();

	public abstract void registerTileEntities();

	public abstract void playSound(String soundName, float xCoord, float yCoord, float zCoord, float volume, float pitch);
}
