package com.example.android_utils.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class T {

	

	private static Toast mToast;


	private T(Context context) {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		} else {
			mToast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		mToast.show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		} else {
			mToast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
		mToast.show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
		} else {
			mToast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
		mToast.show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_LONG);
		} else {
			mToast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
		mToast.show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(duration);
		} else {
			mToast.makeText(context, message, duration).show();
		}
		mToast.show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration) {
		if (null != mToast) {
			mToast.setText(message);
			mToast.setDuration(duration);
		} else {
			mToast.makeText(context, message, duration).show();
		}
		mToast.show();
	}
	
	/**
	 * 取消显示Toast
	 */
	public void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

}