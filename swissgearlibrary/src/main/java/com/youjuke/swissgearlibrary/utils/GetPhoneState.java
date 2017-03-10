package com.youjuke.swissgearlibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

/**
 * 检查手机状态
 * 
 * @author shine_sj
 * 
 */
	public class GetPhoneState {
	private static ConnectivityManager connManager = null;
	private static TelephonyManager telephonyManager = null;
	public static DisplayMetrics dm;

	/**
	 * 检测SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Context appContext) {
		Context context = appContext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 获取当前操作系统的语言
	 * 
	 * @return String es或者zh
	 */
	public static String getSysLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return String 手机型号
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取操作系统的版本号
	 * 
	 * @return String 系统版本号
	 */
	public static String getSysRelease() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 读取sim卡序列号
	 */
	public static String readSimSerialNum(Context con) {
		if (con == null) {
			return "";
		}
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) con
					.getSystemService(Context.TELEPHONY_SERVICE);
		}
		telephonyManager.getSubscriberId();
		return telephonyManager.getSimSerialNumber();
	}

	/**
	 * 读取手机串号
	 * 
	 * @param con
	 *            上下文
	 * @return String 手机串号
	 */
	public static String readTelephoneSerialNum(Context con) {
		TelephonyManager telephonyManager = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		// String string = telephonyManager.getDeviceId();
		return telephonyManager.getDeviceId();
	}

	/**
	 * 获取运营商信息
	 * 
	 * @param con
	 *            上下文
	 * @return String 运营商信息
	 */
	public static String getCarrier(Context con) {
		TelephonyManager telManager = (TelephonyManager) con
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telManager.getSubscriberId();
		if (imsi != null && !"".equals(imsi)) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
				return "中国移动";
			} else if (imsi.startsWith("46001")) {
				return "中国联通";
			} else if (imsi.startsWith("46003")) {
				return "中国电信";
			}
		}
		return "";
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 *            上下文
	 * @return String 返回网络类型
	 */
	public static String getAccessNetworkType(Context context) {
		int type = 0;
		if (connManager != null) {
			type = connManager.getActiveNetworkInfo().getType();
		} else {
			connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			type = connManager.getActiveNetworkInfo().getType();
		}
		if (type == ConnectivityManager.TYPE_WIFI) {
			return "wifi";
		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			return "3G/GPRS";
		}
		return null;
	}

	/**
	 * 获取当前时间
	 */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取手机Ip地址
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {

		try {

			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {

					InetAddress inetAddress = enumIpAddr.nextElement();

					if (!inetAddress.isLoopbackAddress()) {

						//noinspection RedundantStringToString
						return inetAddress.getHostAddress().toString();

					}

				}

			}

		} catch (SocketException ex) {

			ex.printStackTrace();

		}

		return null;

	}
}
