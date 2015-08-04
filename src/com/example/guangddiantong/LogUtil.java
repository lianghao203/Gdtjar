package com.example.guangddiantong;

import android.util.Log;

public class LogUtil {
	public static void i(String tag, String msg) {
		if (true) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, long msg) {
		i(tag, String.valueOf(msg));
	}

	public static void i(String tag, int msg) {
		i(tag, String.valueOf(msg));
	}

	public static void i(String tag, boolean msg) {
		i(tag, String.valueOf(msg));
	}
}
