package com.oklog.entities;


public class StringKeeper {
	
	private StringBuilder mStringBuilder;
	private int mNumberOfLines;
	
	public StringKeeper(int size) {
		mStringBuilder = new StringBuilder();
		mNumberOfLines = 0;
	}

	public void append(String line) {
		mNumberOfLines++;
		mStringBuilder.append(line);
	}
	
	public int getNumberOfLines() {
		return mNumberOfLines;
	}
	
	@Override
	public String toString() {
		return mStringBuilder.toString();
	}
}
