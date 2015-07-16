package com.oklog.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import com.oklog.interfaces.TaskStatusListener;

public class WriterThread implements Runnable {

	private RingQueue mRingQueue;
	private File mLogFile;
	private LogLine mLog;
	private boolean mAppendQueue = false;
	private TaskStatusListener mListener;

	public WriterThread(RingQueue ringQueue, File logFile, TaskStatusListener listener) {
		mRingQueue = ringQueue;
		mLogFile = logFile;
		mAppendQueue = true;
		mListener = listener;
	}
	
	public WriterThread(LogLine log, File logFile, TaskStatusListener listener) {
		mLog = log;
		mLogFile = logFile;
		mListener = listener;
	}

	@Override
	public synchronized void run() {
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
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
			if(mListener != null) {
				mListener.onTaskComplete();
			}
//			long end = (System.currentTimeMillis() - start) / 1000;
//			Log.e(WriterThread.class.getSimpleName(), "Took " + end + " Sec");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}