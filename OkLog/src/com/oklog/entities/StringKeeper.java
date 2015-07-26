package com.oklog.entities;

public class StringKeeper {
	
	private StringBuilder mStringBuilder;
	private int mNumberOfLines;
	
	public StringKeeper(int size) {
		mNumberOfLines = 0;
		mStringBuilder = new StringBuilder();
	}
	
	public StringKeeper() {
		mNumberOfLines = 0;
		mStringBuilder = new StringBuilder();
	}
	
	public void addLine() {
		mNumberOfLines++;
	}
	

	public void removeLine() {
		mNumberOfLines--;
	}

	public void append(String line) {
		mStringBuilder.append(line);
	}
	
	public void appendLine(String line) {
		mNumberOfLines++;
		mStringBuilder.append(String.format("%s%n", line));
	}
	
	public void append(StringKeeper line) {
		mNumberOfLines += line.getNumberOfLines();
		mStringBuilder.append(line.toString());
	}
	
	public void appendFormat(String format, StringKeeper line) {
		mNumberOfLines += line.getNumberOfLines();
		mStringBuilder.append(String.format(format, line.toString()));
	}
	
	public int getNumberOfLines() {
		return mNumberOfLines;
	}
	
	public int length() {
		return mStringBuilder.length();
	}
	
	@Override
	public String toString() {
		return mStringBuilder.toString();
	}
}
