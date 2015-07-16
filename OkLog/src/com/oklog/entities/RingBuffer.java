package com.oklog.entities;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RingBuffer {
	
	// Class variables
	private int mSize;
	private Object mLock;
	private ConcurrentLinkedQueue<LogLine> mElements; 
	
	public RingBuffer(int size) {
		mSize = size;
		mLock = new Object();
		mElements = new ConcurrentLinkedQueue<LogLine>();
	}
	
	public void add(LogLine log) {
		synchronized (mLock) {
			while(mElements.size() >= mSize) {
				mElements.poll();
			}
			mElements.add(log);
		}
	}
	
	public void reset() {
		synchronized (mLock) {
			mElements.clear();
		}
	}
	
	public String getLogFileBody() {
		StringBuilder logTextBody;
		synchronized (mLock) {
			logTextBody = new StringBuilder();
			for(LogLine log : mElements) {
				logTextBody.append(String.format("%s%n", log.toString()));
			}
		}
		return logTextBody.toString();
	}
}
