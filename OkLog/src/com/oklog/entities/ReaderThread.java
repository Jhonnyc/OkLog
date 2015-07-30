package com.oklog.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

import com.oklog.OkLog;

public class ReaderThread implements Runnable {

	private File mLogFile;
	private File mLogFileToSend;
	private StringKeeper mFileContent;
	private OnAfterCrash mOnAfterCrash;
	private boolean mHasContent;

	public ReaderThread(File logFile, File logFileToSend, StringKeeper stringKeeper, OnAfterCrash onAfterCrash) {
		mLogFile = logFile;
		mLogFileToSend = logFileToSend;
		mFileContent = stringKeeper;
		mOnAfterCrash = onAfterCrash;
		mHasContent = false;
	}

	@Override
	public synchronized void run() {
		try {
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			//readLogFileContent(mLogFile);
			copyFileUsingFileChannels(mLogFile, mLogFileToSend);
			if(mHasContent && mOnAfterCrash != null) {
				mOnAfterCrash.doAfterCrash(mLogFileToSend);
			}
			PrintWriter writer = new PrintWriter(mLogFile);
			writer.print("");
			OkLog.addToCurrentLines(OkLog.getCurrentLines() * -1);
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void readLogFileContent(File file) {
		int numberOfLines;
		String line;
		BufferedReader input = null;
		try {
			if(file.exists()) {
			    input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			    while ((line = input.readLine()) != null) {
			    	if(line.trim().length() > 0) {
			    		mFileContent.append(String.format("%s%n", line));
			    		OkLog.addToCurrentLines(1);
			    	}
			    }
			    numberOfLines = OkLog.getCurrentLines();
			    mHasContent = numberOfLines > 0;
			    input.close();
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private static void copyFileUsingFileChannels(File sourceFile, File destFile) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(sourceFile).getChannel();
			outputChannel = new FileOutputStream(destFile).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
}