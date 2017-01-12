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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
		String deviceId = telephonyManager.getDeviceId();
		if (TextUtils.isEmpty(deviceId)) {
			return android.os.Build.SERIAL;
		}
		return deviceId;
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
	 * 
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
	 * 
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

	/**
	 * 
	 * 获取手机信息
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, String> getPhoneInfo(Context context) {
		Map<String, String> map = new HashMap<String, String>();
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId(); // 获取设备id
		String imsi = tm.getSubscriberId(); // 国际移动用户识别码
		String phoneMode = android.os.Build.MODEL; // 获取手机的型号 设备名称
		String phoneSDk = android.os.Build.VERSION.RELEASE; // 获取系统版本字符串。如4.1.2
															// 或2.2 或2.3等
		map.put("imei", imei);
		map.put("imsi", imsi);
		map.put("phoneMode", phoneMode + "##" + phoneSDk);
		map.put("model", phoneMode);
		map.put("sdk", phoneSDk);
		return map;
	}

	/**
	 * 在开发中 我们有时候会需要获取当前手机的系统版本来进行判断，或者需要获取一些当前手机的硬件信息。
	 * 
	 * android.os.Build类中。包括了这样的一些信息。我们可以直接调用 而不需要添加任何的权限和方法。
	 * 
	 * 
	 * android.os.Build.BOARD：获取设备基板名称
	 * 
	 * android.os.Build.BOOTLOADER:获取设备引导程序版本号
	 * 
	 * android.os.Build.BRAND：获取设备品牌
	 * 
	 * android.os.Build.CPU_ABI：获取设备指令集名称（CPU的类型）
	 * 
	 * android.os.Build.CPU_ABI2：获取第二个指令集名称
	 * 
	 * android.os.Build.DEVICE：获取设备驱动名称
	 * 
	 * android.os.Build.DISPLAY：获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
	 * 
	 * android.os.Build.FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
	 * 
	 * android.os.Build.HARDWARE：设备硬件名称,一般和基板名称一样（BOARD）
	 * 
	 * android.os.Build.HOST：设备主机地址
	 * 
	 * android.os.Build.ID:设备版本号。
	 * 
	 * android.os.Build.MODEL ：获取手机的型号 设备名称。
	 * 
	 * android.os.Build.MANUFACTURER:获取设备制造商
	 * 
	 * android:os.Build.PRODUCT：整个产品的名称
	 * 
	 * android:os.Build.RADIO：无线电固件版本号，通常是不可用的 显示unknown
	 * 
	 * android.os.Build.TAGS：设备标签。如release-keys 或测试的 test-keys
	 * 
	 * android.os.Build.TIME：时间
	 * 
	 * android.os.Build.TYPE:设备版本类型 主要为"user" 或"eng".
	 * 
	 * android.os.Build.USER:设备用户名 基本上都为android-build
	 * 
	 * android.os.Build.VERSION.RELEASE：获取系统版本字符串。如4.1.2 或2.2 或2.3等
	 * 
	 * android.os.Build.VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
	 * 
	 * android.os.Build.VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
	 * 
	 * android.os.Build.VERSION.SDK：系统的API级别 一般使用下面大的SDK_INT 来查看
	 * 
	 * android.os.Build.VERSION.SDK_INT：系统的API级别 数字表示
	 * 
	 * 
	 * android.os.Build.VERSION_CODES类
	 * 中有所有的已公布的Android版本号。全部是Int常亮。可用于与SDK_INT进行比较来判断当前的系统版本
	 */

}
