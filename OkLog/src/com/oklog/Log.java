package com.oklog;

public class Log {
	
	// Class Variables
	private LogLevel mLogLevel;
	private String mTag;
	private String mClassName;
	private String mPackageName;
	private Throwable mException;
	private boolean mPrintStackTrace;
	private long mEpochTime;
	private String mFormattedDate;
	private String mStackTrace;
	
	public Log(LogLevel level, Class<?> clazz, Throwable exception, boolean printStackTrace) {
		mLogLevel = level;
		mClassName = clazz.getSimpleName();
		mPackageName = clazz.getPackage().getName();
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
		mClassName = object.getClass().getSimpleName();
		mPackageName = object.getClass().getPackage().getName();
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
		return null;
	}

}
