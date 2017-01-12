package com.example.android_utils.utils;

/**
 * 按钮重复点击
 * 
 */
public class BtnClickUtils {
	private BtnClickUtils() {

	}

	private static long mLastClickTime = 0;
	public synchronized static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - mLastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}

		mLastClickTime = time;

		return false;
	}
	
	
	
	/**
	 * @param n
	 * @return
	 */
	public synchronized static boolean isFastDoubleClick(int n) {
		long time = System.currentTimeMillis();
		long timeD = time - mLastClickTime;
		if (0 < timeD && timeD < n*1000) {
			return true;
		}

		mLastClickTime = time;

		return false;
	}

}
