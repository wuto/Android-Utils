package com.example.android_utils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Locale;

public class PhoneUtils {

	/**
	 * 获取手机设备序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		if (TextUtils.isEmpty(imei)) {
			return android.os.Build.SERIAL;
		}
		return imei;
	}

	/**
	 * 
	 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * 获取手机号码，大部分获取不到，建议用imei号代替
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	/**
	 * 
	 * 获取Mac地址
	 * @param context
	 * @return
	 */
	@Deprecated
	@SuppressLint("DefaultLocale")
	public static String getMacAddress(Context context) {
		String macAddress = "";
		if (context != null) {
			// wifi mac地址
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (wifi.isWifiEnabled()) {
				WifiInfo info = wifi.getConnectionInfo();
				macAddress = info.getMacAddress();
			}
		}

		if (TextUtils.isEmpty(macAddress)) {
			try {
				macAddress = loadFileAsString("/sys/class/net/eth0/address")
						.toUpperCase(Locale.ENGLISH).substring(0, 17);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return macAddress.toUpperCase();
	}

	/**
	 * 
	 * <uses-permission android:name="android.permission.BLUETOOTH" />
	 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
	 * 获取蓝牙mac
	 * 
	 * @return
	 */
	public static String getCurBluetoothMac() {
		BluetoothAdapter btAda = BluetoothAdapter.getDefaultAdapter();
		// 开启蓝牙
		if (btAda.isEnabled() == false) {
			if (btAda.enable()) {
				while (btAda.getState() == BluetoothAdapter.STATE_TURNING_ON
						|| btAda.getState() != BluetoothAdapter.STATE_ON) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return btAda.getAddress();
	}

	public static String loadFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/**
	 * 
	 * 得到CellLocation即可获取到基站相关信息
	 * @param context
	 * @return
	 */
	public static CellLocation getCellLocation(Context context) {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getCellLocation();
	}


	/**
	 * 获取手机的MAC地址
	 * 
	 * @return
	 */
	public static String getMac() {
		String str = "";
		String macSerial = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim().toUpperCase();// 去空格
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (macSerial == null || "".equals(macSerial)) {
			try {
				return loadFileAsString("/sys/class/net/eth0/address")
						.toUpperCase().substring(0, 17);
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return macSerial;
	}
}
