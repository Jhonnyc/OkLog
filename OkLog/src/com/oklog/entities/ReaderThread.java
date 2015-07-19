package com.oklog.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.oklog.interfaces.TaskStatusListener;


public class ReaderThread implements Runnable {

	private File mLogFile;
	private StringKeeper mFileContent;
	private TaskStatusListener mListener;

	public ReaderThread(File logFile, StringKeeper stringKeeper, TaskStatusListener listener) {
		mLogFile = logFile;
		mFileContent = stringKeeper;
		mListener = listener;
	}

	@Override
	public synchronized void run() {
		try {
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			readLogFileContent(mLogFile);
			if(mListener != null) {
				mListener.onTaskComplete();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		Log.i("ReaderThread", String.format("Done with %n%s", mFileContent.toString()));
	}
	
	public void readLogFileContent(File file) {
		String line;
		BufferedReader input = null;
		try {
		    input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		    while ((line = input.readLine()) != null) {
		    	mFileContent.append(String.format("%s%n", line));
		    }
		    input.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}