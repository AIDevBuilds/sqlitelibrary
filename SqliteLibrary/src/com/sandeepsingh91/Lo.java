package com.sandeepsingh91;

public final class Lo {

	private final static String TAG = "";
	private final static boolean DEBUG = true;

	private Lo() {
	}

	public static void v(String msg) {
		if (DEBUG) {
			android.util.Log.i(TAG, msg);
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			android.util.Log.e(TAG, msg);
		}
	}

}
