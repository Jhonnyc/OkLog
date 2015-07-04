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

public class OkLog {

	// Class private fields
	private Context mContext;
	private static OkLog mInstance;
	
	// Class public fields
	public static LogConfiguration Configuration = new LogConfiguration();
	
	private OkLog(Context context) {
		mContext = context;
	}
	
	public synchronized static void initialize(Context context) {
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
	********* Private Methods *********
	***********************************
	***********************************/
	
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
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
