package com.oklog.config;

public enum FilePolicy {
	WRITE_ALL,
	WRITE_ESCALATING,
	WRITE_ONLY_ERRORS,
	DONT_WRITE;
}
