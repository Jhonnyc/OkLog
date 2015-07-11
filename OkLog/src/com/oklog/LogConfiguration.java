package com.oklog;

public class LogConfiguration {

	// Class Variables
	private boolean mPrintStackTrace;
	private boolean mSendUsingEmail;
	private int mSize;
	private String mLogFileName;
	private TimeFormat mTimeFormat;
	private LogLevel mLogLevel;
	private FilePolicy mFilePolicy;
	private WritePolicy mWritePolicy;

	public LogConfiguration() {
		mPrintStackTrace = true;
		mSendUsingEmail = false;
		mSize = 1000;
		mLogFileName = "OkLog.log";
		mTimeFormat = TimeFormat.DD_MM_YYYY__HH_mm_ss_SSS;
		mLogLevel = LogLevel.VERBOSE;
		mFilePolicy = FilePolicy.WRITE_ALL;
		mWritePolicy = WritePolicy.EVERY_LINE;
	}

	public boolean getPrintStackTrace() {
		return mPrintStackTrace;
	}
	
	public void setPrintStackTrace(boolean printStackTrace) {
		mPrintStackTrace = printStackTrace;
	}
	
	public boolean getSendEmail() {
		return mSendUsingEmail;
	}
	
	public void setSendUsingEmail(boolean sendUsingEmail) {
		mSendUsingEmail = sendUsingEmail;
	}
	
	public int getSize() {
		return mSize;
	}
	
	public void setSize(int size) {
		mSize = size;
	}
	
	public String getLogFileName() {
		return mLogFileName;
	}

	public void setLogFileName(String logFileName) {
		mLogFileName = logFileName;
	}
	
	public TimeFormat getTimeFormat() {
		return mTimeFormat;
	}

	public void setTimeFormat(TimeFormat timeFormat) {
		mTimeFormat = timeFormat;
	}
	
	public LogLevel getLogLevel() {
		return mLogLevel;
	}

	public void setLogLevel(LogLevel logLevel) {
		mLogLevel = logLevel;
	}
	
	public FilePolicy getFilePolicy() {
		return mFilePolicy;
	}
	
	public void setFilePolicy(FilePolicy filePolicy) {
		mFilePolicy = filePolicy;
	}
	
	public WritePolicy getWritePolicy() {
		return mWritePolicy;
	}
	
	public void setWritePolicy(WritePolicy writePolicy) {
		mWritePolicy = writePolicy;
	}
}
