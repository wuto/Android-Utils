package com.example.android_utils.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtils {

	private static Toast sToast;
	private static Context sContext;

	private ToastUtils(Context context) {
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
		showT(context, message, Toast.LENGTH_SHORT);
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message) {
		showT(context, message, Toast.LENGTH_SHORT);
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message) {
		showT(context, message, Toast.LENGTH_LONG);
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message) {
		showT(context, message, Toast.LENGTH_LONG);
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration) {

		showT(context, message, duration);
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration) {

		showT(context, message, duration);
	}

	/**
	 * 
	 * 短时间时间，有格式
	 * 
	 * @param context
	 * @param format
	 * @param duration
	 * @param args
	 */
	public static void show(Context context, String format, Object... args) {

		String _message = String.format(format, args);
		showT(context, _message, Toast.LENGTH_SHORT);
	}

	/**
	 * 
	 * 自定义时间，有格式
	 * 
	 * @param context
	 * @param format
	 * @param duration
	 * @param args
	 */
	public static void show(Context context, String format, int duration,
			Object... args) {

		String _message = String.format(format, args);
		showT(context, _message, duration);
	}

	private static void showT(Context context, Object  message, int duration) {
		int _msg_int = 0;
		String _msg_str = "";
		if (message instanceof String) {
			_msg_str = (String) message;
		} else if (message instanceof Integer) {
			_msg_int = (Integer) message;
		} else {
			return;
		}
		if (null != sToast) {
			if (_msg_int != 0 && TextUtils.isEmpty(_msg_str)) {
				sToast.setText(_msg_int);
			} else if (_msg_int == 0 && !TextUtils.isEmpty(_msg_str)) {
				sToast.setText(_msg_str);
			} else {
				return;
			}
			sToast.setDuration(duration);
		} else {
			if (sContext == null) {
				sContext = context.getApplicationContext();
			}
			if (_msg_int != 0 && TextUtils.isEmpty(_msg_str)) {
				sToast=Toast.makeText(sContext, _msg_int, duration);
			} else if (_msg_int == 0 && !TextUtils.isEmpty(_msg_str)) {
				sToast=Toast.makeText(sContext, _msg_str, duration);
			} else {
				return;
			}
		}
		sToast.show();
	}

	/**
	 * 取消显示Toast
	 */
	public void cancelToast() {
		if (sToast != null) {
			sToast.cancel();
		}
	}

}
