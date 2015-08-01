package com.oklog.entities;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RingBuffer {
	
	// Class variables
	private int mSize;
	private long mNumberOfElements;
	private Object mLock;
	private ConcurrentLinkedQueue<LogLine> mElements; 
	
	public RingBuffer(int size) {
		mSize = size;
		mNumberOfElements = 0;
		mLock = new Object();
		mElements = new ConcurrentLinkedQueue<LogLine>();
	}
	
	public void add(LogLine log) {
		synchronized (mLock) {
			while(mElements.size() >= mSize) {
				mElements.poll();
			}
			mNumberOfElements++;
			mElements.add(log);
		}
	}
	
	public void reset() {
		synchronized (mLock) {
			mNumberOfElements = 0;
			mElements.clear();
		}
	}
	
	public String getLogFileBody() {
		StringBuilder logTextBody;
		synchronized (mLock) {
			logTextBody = new StringBuilder();
			for(LogLine log : mElements) {
				logTextBody.append(String.format("%s%n", log.getString()));
			}
		}
		return logTextBody.toString();
	}
	
	public long size() {
		return mNumberOfElements;
	}
}
