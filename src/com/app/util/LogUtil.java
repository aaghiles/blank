package com.app.util;

import org.apache.log4j.Logger;

public class LogUtil {

	private static Logger log = Logger.getLogger(LogUtil.class);

	public static void logInfo(String message) {
		log.info(message);
	}

	public static void logError(String message) {
		log.error(message);
	}
}
