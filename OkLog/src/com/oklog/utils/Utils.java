package com.oklog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.oklog.OkLog;

public class Utils {
	
	public static final String NEW_LINE = System.getProperty("line.separator");

	public static String getStringDateFromEpochTime(long epochTime) {
		Date date = new Date(epochTime);
		SimpleDateFormat formatter = new SimpleDateFormat(OkLog.Configuration.getTimeFormat().toString());
		String formattedTime = formatter.format(date);
		return formattedTime;
	}
	
	public static String getFormattedStackTraceForException(Throwable exception) {
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement element : exception.getStackTrace()) {
			String causeLine = String.format("%s%n", element.toString());
			stringBuilder.append(causeLine);
		}

		return stringBuilder.toString();
	}
	
	
}
