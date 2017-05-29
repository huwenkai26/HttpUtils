package com.jiumeng.utils;

import android.util.Log;

public class Debug {
	public static boolean debug=false;
	private static final String SDK = "kksdk";	
	public static final String CORE = "kkcore";
	private static final String VIDEO = "kkvideo";
	private static final String DOWNLOAD = "kkdownload";
	
	//core模块的日志
	public static void core(String msg){
		if (!debug){
			Log.d(CORE, msg);
		}
	}
	
	//video模块的日志
	public static void video(String msg){
		if (!debug){
			Log.d(VIDEO, msg);
		}
	}
	
	//video模块的日志
	public static void download(String msg){
		if (!debug){
			Log.d(DOWNLOAD, msg);
		}
	}
	
	//客户可见的日志
	public static void i(String msg){
		Log.i(SDK, msg);
	}
	//客户可见的日志
	public static void e(String msg){
		Log.e(SDK, msg);
	}
	
}
