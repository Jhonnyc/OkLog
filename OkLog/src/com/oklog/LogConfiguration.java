package com.oklog;

public class LogConfiguration {

	// Class Variables
	private boolean mSendUsingEmail;
	private String mLogFileName;
	private TimeFormat mTimeFormat;

	public LogConfiguration() {
		mSendUsingEmail = false;
		mLogFileName = "OkLog.log";
		mTimeFormat = TimeFormat.DD_MM_YYYY__HH_mm_ss_SSS;
	}

	public TimeFormat getTimeFormat() {
		return mTimeFormat;
	}

	public boolean isSendEmail() {
		return mSendUsingEmail;
	}

	public String getmLogFileName() {
		return mLogFileName;
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
	
}
