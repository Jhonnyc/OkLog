package com.oklog.deprecated;

import com.oklog.entities.LogLine;


public class RingQueue {
	
	//  Class variables
	private int mIndex;
	private int mFirst;
	private int mLast;
	private int mSize;
	private int mCurrentSize;
	private LogLine[] mElements;
	private StringBuilder mLogTextBody;
	
	public RingQueue(int size) {
		mSize = size;
		mIndex = 0;
		mFirst = 0;
		mLast = mSize - 1;
		mElements = new LogLine[mSize];
	}
	
	public synchronized void add(LogLine log) {
		if(mCurrentSize >= mSize) {
			mFirst = (mFirst + 1) % mSize;
			mLast = mIndex;
			mElements[mIndex] = log;
		} else {
			mElements[mIndex] = log;
		}
		mCurrentSize++;
		mIndex = mCurrentSize % mSize;
	}
	
	public String getLogFileBody() {
		int index;
		LogLine line;
		mLogTextBody = new StringBuilder();
		if(mFirst < mLast) {
			for(index = mFirst; index < mLast; index++) {
				line = mElements[index];
				mLogTextBody.append(line.toString());
			}	
		} else {
			for(index = mFirst; ; ) {
				if(index == mLast) {
					break;
				} else {
					line = mElements[index];
					mLogTextBody.append(line.toString());
					index = ((index+1) % mSize);
				}
			}
		}
		
		line = mElements[mLast];
		mLogTextBody.append(line.toString());
		
		return mLogTextBody.toString();
	}
}
