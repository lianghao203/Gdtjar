package c.d.e.gdt;

import android.util.Log;

public class LogUtil {
	public static void i(String msg) {
		// Log.i("info", msg);
	}

	public static void i(long msg) {
		i(String.valueOf(msg));
	}

	public static void i(String tag, int msg) {
		i(String.valueOf(msg));
	}

	public static void i(String tag, boolean msg) {
		i(String.valueOf(msg));
	}
}
