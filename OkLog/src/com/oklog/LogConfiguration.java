package com.oklog;

public class LogConfiguration {

	// Class Variables
	private boolean mSendUsingEmail;
	private TimeFormat mTimeFormat;
	
	
	public LogConfiguration() {
		mSendUsingEmail = false;
		mTimeFormat = TimeFormat.DD_MM_YYYY__HH_mm_ss_SSS;
	}
	
	public String getTimeFormat() {
		return mTimeFormat.toString();
	}
}
