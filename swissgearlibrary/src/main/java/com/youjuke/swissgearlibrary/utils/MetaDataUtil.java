package com.youjuke.swissgearlibrary.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * 描述: MetaData 工具类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-07-14 09:49
 */
public class MetaDataUtil {

	/**
	 * 在application节点取metadata
	 * <application>
	 *	 <meta-data android:value="hello my application" android:name="data_Name"></meta-data>
	 * </application>
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getFromApplication(Context context,String key){
		ApplicationInfo appInfo = null;
		try {
			appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			String msg=appInfo.metaData.getString(key);
			return msg;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 在Activity应用<meta-data>元素。
	 * xml代码段：
	 * <activity...>
	 *  <meta-data android:name="data_Name" android:value="hello my activity"></meta-data>
	 * </activity>
	 * @param activity
	 * @param key
	 * @return
	 */
	public static String getFromActivity(Activity activity, String key){
		ActivityInfo info = null;
		try {
			info = activity.getPackageManager()
					.getActivityInfo(activity.getComponentName(),
							PackageManager.GET_META_DATA);
			String msg=info.metaData.getString(key);
			return msg;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 在service应用<meta-data>元素。
	 * xml代码段：
	 * <service android:name="MetaDataService">
	 *      <meta-data android:value="hello my service" android:name="data_Name"></meta-data>
	 * </service>
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getFromService(Context context,String key,Class clazz){
		ServiceInfo info = null;
		try {
			ComponentName cn=new ComponentName(context, clazz);
			info=context.getPackageManager()
					.getServiceInfo(cn, PackageManager.GET_META_DATA);
			String msg=info.metaData.getString(key);
			return msg;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 在receiver应用<meta-data>元素。
	 * xml代码段:
	 * <receiver android:name="MetaDataReceiver">
	 *  <meta-data android:value="hello my receiver" android:name="data_Name"></meta-data>
	 *  <intent-filter>
	 *      <action android:name="android.intent.action.PHONE_STATE"></action>
	 *  </intent-filter>
	 * </receiver>
	 * @param context
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static String getFromReceiver(Context context,String key,Class clazz){
		ActivityInfo info = null;
		try {
			ComponentName cn=new ComponentName(context, clazz);
			info=context.getPackageManager()
					.getReceiverInfo(cn, PackageManager.GET_META_DATA);
			String msg=info.metaData.getString(key);
			return msg;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
}
