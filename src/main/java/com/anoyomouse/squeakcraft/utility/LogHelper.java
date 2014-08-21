package com.anoyomouse.squeakcraft.utility;

import org.apache.logging.log4j.Logger;

/**
 * Created by Anoyomouse on 2014/08/21.
 */
public class LogHelper
{
	private static Logger modLogger;

	public static void setLogger(Logger theLogger)
	{
		modLogger = theLogger;
	}

	public static void info(Object data)
	{
		modLogger.info(data);
	}
}
