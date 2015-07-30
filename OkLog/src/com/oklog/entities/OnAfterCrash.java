package com.oklog.entities;

import java.io.File;

public abstract class OnAfterCrash {

	public abstract void doAfterCrash(File logFile);
	
}
