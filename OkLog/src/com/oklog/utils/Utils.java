package com.oklog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.oklog.OkLog;
import com.oklog.entities.StringKeeper;

public class Utils {
	
	public static final String NEW_LINE = System.getProperty("line.separator");

	public static String getStringDateFromEpochTime(long epochTime) {
		Date date = new Date(epochTime);
		SimpleDateFormat formatter = new SimpleDateFormat(OkLog.Configuration.getTimeFormat().toString(), Locale.US);
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
	
	public static StringKeeper getFormattedStackTraceForException(Throwable exception, int pad) {
		String causeLine;
		StringKeeper stringKeeper = new StringKeeper();
		StackTraceElement[] elements = exception.getStackTrace();
		if(elements != null && elements.length > 0) {
			stringKeeper.appendLine(elements[0].toString());
			if(elements.length > 1) {
				for (int i = 1; i < (elements.length - 1); i++) {
					String element = elements[i].toString();
					causeLine = Utils.padLeft(element,pad + element.length());
					stringKeeper.appendLine(causeLine);
				}
				String element = elements[elements.length - 1].toString();
				causeLine = Utils.padLeft(element,pad + element.length());
				stringKeeper.addLine();
				stringKeeper.append(causeLine);
			}
		}
		
		return stringKeeper;
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%" + n + "s", s);  
	}
}
