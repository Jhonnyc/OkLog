package com.oklog.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import com.oklog.OkLog;

public class WriterThread implements Runnable {

	private RingBuffer mRingBuffer;
	private File mLogFile;
	private LogLine mLog;
	private RingBuffer mLogs;

	public WriterThread(RingBuffer logs, File logFile) {
		mLogs = logs;
		mLogFile = logFile;
	}

//	public WriterThread(LogLine log, File logFile) {
//		mLog = log;
//		mLogFile = logFile;
//	}

	@Override
	public synchronized void run() {
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
//		long start = System.currentTimeMillis();
		FileWriter writer = null;

		try {
			String content;
			writer = new FileWriter(mLogFile, false);
			 
			if(OkLog.getCurrentLines() > 0) {
				content = String.format("%n%s", mLogs.getLogFileBody());
			} else {
				content = mLogs.getLogFileBody();
			}
			
			writer.append(content);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}