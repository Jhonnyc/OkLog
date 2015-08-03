package com.oklog;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.oklog.config.LogConfiguration;
import com.oklog.config.LogLevel;
import com.oklog.config.WritePolicy;
import com.oklog.entities.LogLine;
import com.oklog.entities.OnAfterCrash;
import com.oklog.entities.ReaderThread;
import com.oklog.entities.RingBuffer;
import com.oklog.entities.StringKeeper;
import com.oklog.entities.WriterThread;

public class OkLog {

	// Class final fields
	private static final String LOG_FILE_NAME = "Log.log";
	
	// Class private fields
	private Context mContext;
	private static OkLog mInstance;
	private static boolean mInitiated = false;
	private StringKeeper mStringKeeper; 
	private BlockingQueue<Runnable> mBlockingQueue;
	private static OnAfterCrash mOnAfterCrash;  
	private static File mLogFile;
	private static File mLogFileToSend;
	private static ThreadPoolExecutor mExecutor;
	private static AtomicInteger mCurrentNumberOfLines;
	private static RingBuffer mLogs;
	
	// Class public fields
	public static LogConfiguration Configuration = new LogConfiguration();
	
	private OkLog(Context context) {
		mContext = context;
		mLogFile = getLogFile(LOG_FILE_NAME, mContext);
		mLogFileToSend = getLogFile(Configuration.getLogFileName(), mContext);
		mStringKeeper = new StringKeeper(Configuration.getSize());
		mLogs = new RingBuffer(Configuration.getSize());
		mCurrentNumberOfLines = new AtomicInteger();
		ReaderThread reader = new ReaderThread(mLogFile, mLogFileToSend, mStringKeeper, mOnAfterCrash);
		mBlockingQueue = new ArrayBlockingQueue<Runnable>(Configuration.getThreadPoolSize());
		mExecutor = new ThreadPoolExecutor(1, 1, 5000, TimeUnit.SECONDS, mBlockingQueue);
		mExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
				if(Configuration.getRetryOnFail()) {
					executor.execute(runnable);
				}
			}
		});
		mExecutor.execute(reader);
	}
	
	public synchronized static void initialize(Context context) {
		mInitiated = true;
		mOnAfterCrash = null;
		mInstance = new OkLog(context);
	}
	
	public synchronized static void initialize(Context context, OnAfterCrash onAfterCrash) {
		mInitiated = true;
		mOnAfterCrash = onAfterCrash;
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

	public static <T> void v(Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.VERBOSE, clazz, msg, exception);
	}
	
	public static <T> void d(Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.DEBUG, clazz, msg, exception);
	}
	
	public static <T> void i(Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.INFO, clazz, msg, exception);
	}
	
	public static <T> void w(Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.WARNING, clazz, msg, exception);
	}
	
	public static <T> void e(Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		logByClass(LogLevel.ERROR, clazz, msg, exception);	
	}
	
	public static <T> void v(Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.VERBOSE, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void d(Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.DEBUG, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void i(Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.INFO, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void w(Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.WARNING, clazz, String.format(msg, args), exception);
	}
	
	public static <T> void e(Class<?> clazz, Exception exception, String msg, T... args) throws NullPointerException {
		logByClass(LogLevel.ERROR, clazz, String.format(msg, args), exception);	
	}
	
	public static <T> void v(Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.VERBOSE, object, msg, exception);
	}
	
	public static <T> void d(Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.DEBUG, object, msg, exception);
	}
	
	public static <T> void i(Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.INFO, object, msg, exception);
	}
	
	public static <T> void w(Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.WARNING, object, msg, exception);
	}
	
	public static <T> void e(Object object, String msg, Exception exception) throws NullPointerException {
		logByObject(LogLevel.ERROR, object, msg, exception);	
	}
	
	public static <T> void v(Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.VERBOSE, object, String.format(msg, args), exception);
	}
	
	public static <T> void d(Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.DEBUG, object, String.format(msg, args), exception);
	}
	
	public static <T> void i(Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.INFO, object, String.format(msg, args), exception);
	}
	
	public static <T> void w(Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.WARNING, object, String.format(msg, args), exception);
	}
	
	public static <T> void e(Object object, Exception exception, String msg, T... args) throws NullPointerException {
		logByObject(LogLevel.ERROR, object, String.format(msg, args), exception);	
	}
	
	/**********************************
	***********************************
	********* Public Methods **********
	***********************************
	***********************************/
	
	public static File getLogFile(Context context) {
		File file = null;
		try {
		    file = new File(context.getFilesDir(), Configuration.getLogFileName());
		    if(!file.exists()) {
		    	file.createNewFile();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return file;
	}
	
	public static Uri getLogFileUri(Context context) {
		Uri uri = Uri.fromFile(getLogFile(context));
		return uri;
	}
	
	public synchronized static int addToCurrentLines(int addition) {
		return mCurrentNumberOfLines.addAndGet(addition);
	}
	
	public synchronized static int getCurrentLines() {
		return mCurrentNumberOfLines.get();
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
		boolean printStackTrace = Configuration.getLevelsForStackTracePrint().contains(level);
		LogLine log = new LogLine(level, String.format(msg, args), null, printStackTrace);
		logToLogcat(level, tag, String.format(msg, args));
		writeToLog(log);
	}
	
	private static <T> void logByClass(LogLevel level, Class<?> clazz, String msg, Exception exception) throws NullPointerException {
		if(!mInitiated) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		boolean printStackTrace = Configuration.getLevelsForStackTracePrint().contains(level);
		LogLine log = new LogLine(level, clazz, msg, exception, printStackTrace);
		logToLogcat(level, clazz.getSimpleName(), msg);
		writeToLog(log);
	}
	
	private static <T> void logByObject(LogLevel level, Object object, String msg, Exception exception) throws NullPointerException {
		if(!mInitiated) {
			throw new NullPointerException("The class has never been initialized. " +
					"Use initialize(context) first to create a new instance");
		}
		boolean printStackTrace = Configuration.getLevelsForStackTracePrint().contains(level);
		LogLine log = new LogLine(level, object, msg, exception, printStackTrace);
		logToLogcat(level, object.getClass().getSimpleName(), msg);
		writeToLog(log);
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
	
	private static synchronized void writeToLog(final LogLine log) {
		if(Configuration.getLevelsToLog().contains(log.getLevel())) {
			WriterThread writer;
			mLogs.add(log);
			if(Configuration.getWritePolicy().equals(WritePolicy.EVERY_LINE)) {
				writer = new WriterThread(mLogs, mLogFile);
				mExecutor.execute(writer);
			} else if(Configuration.getWritePolicy().equals(WritePolicy.EVERY_LOG_CYCLE)) {
				boolean isEmpty = mLogs.size() > 0;
				boolean isCycle = ((mLogs.size() % Configuration.getLogCycleSize()) == 0);
				if(!isEmpty && isCycle) {
					writer = new WriterThread(mLogs, mLogFile);
					mExecutor.execute(writer);
				}
			}
		}
	}
	
	private File getLogFile(String fileName, Context context) {
		File file = null;
		try {
		    file = new File(context.getFilesDir(), fileName);
		    if(!file.exists()) {
		    	file.createNewFile();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return file;
	}
}
