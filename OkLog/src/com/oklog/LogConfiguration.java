package com.oklog;

public class LogConfiguration {

	// Class Variables
	private boolean mSendUsingEmail;
	private String mLogFileName;
	private TimeFormat mTimeFormat;
	private LogLevel mLogLevel;
	private FilePolicy mFilePolicy;

	public LogConfiguration() {
		mSendUsingEmail = false;
		mLogFileName = "OkLog.log";
		mTimeFormat = TimeFormat.DD_MM_YYYY__HH_mm_ss_SSS;
		mLogLevel = LogLevel.VERBOSE;
		mFilePolicy = FilePolicy.WRITE_ALL;
	}

	public TimeFormat getTimeFormat() {
		return mTimeFormat;
	}

	public boolean isSendEmail() {
		return mSendUsingEmail;
	}

	public String getLogFileName() {
		return mLogFileName;
	}
	
	public LogLevel getLogLevel() {
		return mLogLevel;
	}
	
	public FilePolicy getFilePolicy() {
		return mFilePolicy;
	}
	
	public void setTimeFormat(TimeFormat timeFormat) {
		mTimeFormat = timeFormat;
	}

	public void setSendUsingEmail(boolean sendUsingEmail) {
		mSendUsingEmail = sendUsingEmail;
	}

	public void setLogFileName(String logFileName) {
		mLogFileName = logFileName;
	}
	
	public void setLogLevel(LogLevel logLevel) {
		mLogLevel = logLevel;
	}
	
	public void setLogLevel(FilePolicy filePolicy) {
		mFilePolicy = filePolicy;
	}
}
