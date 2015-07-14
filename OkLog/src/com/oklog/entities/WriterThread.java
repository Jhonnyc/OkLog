package com.oklog.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class WriterThread implements Runnable {

	private RingQueue mRingQueue;
	private File mLogFile;
	private LogLine mLog;
	private boolean mAppendQueue = false;

	public WriterThread(RingQueue ringQueue, File logFile) {
		mRingQueue = ringQueue;
		mLogFile = logFile;
		mAppendQueue = true;
	}
	
	public WriterThread(LogLine log, File logFile) {
		mLog = log;
		mLogFile = logFile;
	}

	@Override
	public void run() {
//		long start = System.currentTimeMillis();
		FileWriter writer = null;

		try {
			String content;
			writer = new FileWriter(mLogFile, false);
			if(mAppendQueue) {
				content = mRingQueue.getLogFileBody();
			} else {
				content = mLog.toString();
			}
			writer.append(content);
			writer.close();
//			long end = (System.currentTimeMillis() - start) / 1000;
//			Log.e(WriterThread.class.getSimpleName(), "Took " + end + " Sec");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}