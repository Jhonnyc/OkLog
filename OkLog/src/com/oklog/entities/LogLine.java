package com.oklog.entities;

import com.oklog.config.LogLevel;
import com.oklog.utils.Utils;

public class LogLine {
	
	// Class Variables
	private LogLevel mLogLevel;
	private String mTag;
	private String mClassName;
	private String mPackageName;
	private String mLogMessage;
	private Throwable mException;
	private boolean mPrintStackTrace;
	private boolean mHasClass = false;
	private boolean mHasMessage = false;
	private long mEpochTime;
	private String mFormattedDate;
	private String mStackTrace;
	
	public LogLine(LogLevel level, String msg, Throwable exception, boolean printStackTrace) {
		mLogLevel = level;
		mEpochTime = System.currentTimeMillis();
		mFormattedDate = Utils.getStringDateFromEpochTime(mEpochTime);
		if(exception != null) {
			mException = exception;
			mPrintStackTrace = printStackTrace;
			if(mPrintStackTrace) {
				mStackTrace = Utils.getFormattedStackTraceForException(mException);
			}
		}
		if(msg != null && msg.trim().length() > 0) {
			mHasMessage = true;
			mLogMessage = msg;
		}
	}
	
	public LogLine(LogLevel level, Class<?> clazz, String msg, Throwable exception, boolean printStackTrace) {
		this(level, msg, exception, printStackTrace);
		if(clazz != null) {
			mClassName = clazz.getSimpleName();
			mPackageName = clazz.getPackage().getName();
			mHasClass = true;
		}
	}
	
	public LogLine(LogLevel level, Object object, String msg, Throwable exception, boolean printStackTrace) {
		this(level, msg, exception, printStackTrace);
		if(object != null) {
			mClassName = object.getClass().getSimpleName();
			mPackageName = object.getClass().getPackage().getName();
			mHasClass = true;
		}
	}
	
	public LogLine(LogLevel level, String tag, String msg, Throwable exception, boolean printStackTrace) {
		this(level, msg, exception, printStackTrace);
		if(tag != null && tag.trim().length() > 0) {
			mTag = tag;
		}
	}
	
	@Override
	public String toString() {
		String line = mLogLevel.toString() + " # ";
		line += String.format("%s #", mFormattedDate);
		if(mHasClass) {
			line += String.format(" %s @ %s #", mPackageName, mClassName);
		} else {
			line += String.format(" %s #", mTag);
		}
		if(mHasMessage) {
			line += String.format(" %s #", mLogMessage);
		}
		if(mPrintStackTrace) {
			line += String.format(" %s #", mStackTrace);
		}
		return line;
	}

}