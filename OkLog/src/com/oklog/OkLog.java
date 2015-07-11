package com.oklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class OkLog {

	// Class private fields
	private Context mContext;
	private static OkLog mInstance;
	private static boolean mInitiated = false;
	private RingQueue mLogs;
	
	// Class public fields
	public static LogConfiguration Configuration = new LogConfiguration();
	
	private OkLog(Context context) {
		mContext = context;
		mLogs = new RingQueue(Configuration.getSize());
	}
	
	public synchronized static void initialize(Context context) {
		mInitiated = true;
		mInstance = new OkLog(context);
	}
	
	public static OkLog getInstance() throws NullPointerException {
		if(mInstance == null) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		return mInstance;
	}
	
	public void setConfiguration(LogConfiguration configuration) {
		Configuration = configuration;
		mLogs = new RingQueue(Configuration.getSize());
	}
	
	/**********************************
	***********************************
	********* Logging Methods *********
	***********************************
	***********************************/

	public static <T> void v(String tag, String msg, T... args) throws NullPointerException {
		logByTag(LogLevel.VERBOSE, tag, msg, args);
	}
	
	public static <T> void d(String tag, String msg, T... args) throws NullPointerException {
		logByTag(LogLevel.DEBUG, tag, msg, args);
	}
	
	public static <T> void i(String tag, String msg, T... args) throws NullPointerException {
		logByTag(LogLevel.INFO, tag, msg, args);	
	}
	
	public static <T> void w(String tag, String msg, T... args) throws NullPointerException {
		logByTag(LogLevel.WARNING, tag, msg, args);
	}
	
	public static <T> void e(String tag, String msg, T... args) throws NullPointerException {
		logByTag(LogLevel.ERROR, tag, msg, args);	
	}

	public static <T> void v(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.VERBOSE, clazz, msg, exception);
	}
	
	public static <T> void d(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.DEBUG, clazz, msg, exception);
	}
	
	public static <T> void i(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.INFO, clazz, msg, exception);
	}
	
	public static <T> void w(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.WARNING, clazz, msg, exception);
	}
	
	public static <T> void e(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.ERROR, clazz, msg, exception);	
	}
	
	public static <T> void v(LogLevel level, Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.VERBOSE, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void d(LogLevel level, Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.DEBUG, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void i(LogLevel level, Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.INFO, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void w(LogLevel level, Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.WARNING, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void e(LogLevel level, Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.ERROR, clazz, String.format(msg, args), exception);	
	}
	
	public static <T> void v(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.VERBOSE, object, msg, exception);
	}
	
	public static <T> void d(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.DEBUG, object, msg, exception);
	}
	
	public static <T> void i(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.INFO, object, msg, exception);
	}
	
	public static <T> void w(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.WARNING, object, msg, exception);
	}
	
	public static <T> void e(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.ERROR, object, msg, exception);	
	}
	
	public static <T> void v(LogLevel level, Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.VERBOSE, object, String.format(msg, args), exception);
	}
	
	public static <T> void d(LogLevel level, Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.DEBUG, object, String.format(msg, args), exception);
	}
	
	public static <T> void i(LogLevel level, Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.INFO, object, String.format(msg, args), exception);
	}
	
	public static <T> void w(LogLevel level, Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.WARNING, object, String.format(msg, args), exception);
	}
	
	public static <T> void e(LogLevel level, Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.ERROR, object, String.format(msg, args), exception);	
	}
	
	/**********************************
	***********************************
	********* Private Methods *********
	***********************************
	***********************************/
	
	private static <T> void logByTag(LogLevel level, String tag, String msg, T... args) throws NullPointerException {
		if(!mInitiated) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		LogLine log = new LogLine(level, String.format(msg, args), null, Configuration.getPrintStackTrace());
		logToLogcat(level, tag, String.format(msg, args));
	}
	
	private static <T> void logByClass(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		if(!mInitiated) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		LogLine log = new LogLine(level, clazz, msg, exception, Configuration.getPrintStackTrace());
		logToLogcat(level, clazz.getSimpleName(), msg);
	}
	
	private static <T> void logByObject(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		if(!mInitiated) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		LogLine log = new LogLine(level, object, msg, exception, Configuration.getPrintStackTrace());
		logToLogcat(level, object.getClass().getSimpleName(), msg);
	}
	
	private static void logToLogcat(LogLevel level, String tag, String msg) {
		switch (level) {
		case DEBUG:
			Log.d(tag, msg);
			break;
		case ERROR:
			Log.e(tag, msg);
			break;
		case INFO:
			Log.i(tag, msg);
			break;
		case VERBOSE:
			Log.v(tag, msg);
			break;
		case WARNING:
			Log.w(tag, msg);
			break;
		default:
			Log.e(tag, msg);
			break;
		}
	}
	
	public void writeFile(String fileName, String content) {
		FileOutputStream outputStream = null;
		try {
			outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getLogFile(String fileName) {
		String line;
		File file = null;
		BufferedReader input = null;
		try {
		    file = new File(mContext.getFilesDir(), fileName);
		    input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		    StringBuffer buffer = new StringBuffer();
		    while ((line = input.readLine()) != null) {
		        buffer.append(line);
		    }
		    input.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		return file;
	}
	
	public Uri getLogFileUri(String fileName) {
		Uri uri = Uri.fromFile(getLogFile(fileName));
		return uri;
	}
	
	/**
	 * Checks if external storage is available for read and write 
	 * @return True in case the external storage has a write permission False otherwise
	 */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/**
	 * Checks if external storage is available to at least read 
	 * @return True in case the external storage has a read permission False otherwise
	 */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
