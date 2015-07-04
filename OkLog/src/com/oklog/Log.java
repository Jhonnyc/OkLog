package com.oklog;

public class Log {
	
	// Class Variables
	private LogLevel mLogLevel;
	private String mTag;
	private String mClassName;
	private String mPackageName;
	private Throwable mException;
	private boolean mPrintStackTrace;
	private boolean mHasClass = false;
	private long mEpochTime;
	private String mFormattedDate;
	private String mStackTrace;
	
	public Log(LogLevel level, Class<?> clazz, Throwable exception, boolean printStackTrace) {
		mLogLevel = level;
		if(clazz != null) {
			mClassName = clazz.getSimpleName();
			mPackageName = clazz.getPackage().getName();
			mHasClass = true;
		}
		mException = exception;
		mPrintStackTrace = printStackTrace;
		mEpochTime = System.currentTimeMillis();
		mFormattedDate = Utils.getStringDateFromEpochTime(mEpochTime);
		if(mPrintStackTrace) {
			mStackTrace = Utils.getFormattedStackTraceForException(mException);
		}
	}
	
	public Log(LogLevel level, Object object, Throwable exception, boolean printStackTrace) {
		mLogLevel = level;
		if(object != null) {
			mClassName = object.getClass().getSimpleName();
			mPackageName = object.getClass().getPackage().getName();
			mHasClass = true;
		}
		mEpochTime = System.currentTimeMillis();
		mFormattedDate = Utils.getStringDateFromEpochTime(mEpochTime);
		mException = exception;
		mPrintStackTrace = printStackTrace;
		if(mPrintStackTrace) {
			mStackTrace = Utils.getFormattedStackTraceForException(mException);
		}
	}
	
	public Log(LogLevel level, String tag, Throwable exception, boolean printStackTrace) {
		mLogLevel = level;
		mTag = tag;
		mEpochTime = System.currentTimeMillis();
		mFormattedDate = Utils.getStringDateFromEpochTime(mEpochTime);
		mException = exception;
		mPrintStackTrace = printStackTrace;
		if(mPrintStackTrace) {
			mStackTrace = Utils.getFormattedStackTraceForException(mException);
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
		if(mPrintStackTrace) {
			line += String.format(" %s #", mStackTrace);
		}
		return line;
	}

}
