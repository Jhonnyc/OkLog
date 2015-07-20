package com.oklog.deprecated;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunnablesQueue implements TaskStatusListener {
	
	private LinkedList<Runnable> mLinkedList;
	private AtomicBoolean mIsBusy;
	
	public RunnablesQueue() {
		mLinkedList = new LinkedList<Runnable>();
		mIsBusy = new AtomicBoolean(false);
	}
	
	public synchronized void addTask(Runnable task) {
		if(mIsBusy.get()) {
			mLinkedList.add(task);
		} else {
			mIsBusy.set(true);
			task.run();
		}
	}
	
	@Override
	public synchronized void onTaskComplete() {
		mIsBusy.set(false);
		Runnable task = mLinkedList.pop();
		if(task != null) {
			mIsBusy.set(true);
			task.run();
		}
	}
	
	

}
