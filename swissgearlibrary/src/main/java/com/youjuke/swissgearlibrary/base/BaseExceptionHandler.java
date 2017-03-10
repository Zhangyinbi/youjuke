package com.youjuke.swissgearlibrary.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.youjuke.swissgearlibrary.utils.ApplicationUtils;
import com.youjuke.swissgearlibrary.utils.DateUtil;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SDCardUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统异常处理类
 *
 */
public abstract class BaseExceptionHandler implements UncaughtExceptionHandler {
	
	public static final String TAG = "BaseExceptionHandler";
	private UncaughtExceptionHandler mDefaultHandler;
	protected Context context;

	public BaseExceptionHandler(Context context) {
		this.context = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}




	/**
	 * 未捕获异常跳转
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//如果正确处理的为捕获异常 FIXME 此处不知道为何会执行3次
		if (!handleException(ex) && null != mDefaultHandler) {
			mDefaultHandler.uncaughtException(thread,ex);
		}

		onfinish();
	}

	/**
	 * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
	 * @param ex
	 * @return
	 */
	public abstract boolean handleException(Throwable ex);


	/**
	 * 最终是应该 跳往某个activity 还是 退出程序 在这里写
	 */
	public abstract void onfinish();


	/**
	 * 收集设备参数信息
	 */
	protected Map<String, String> collectDeviceInfo() {
		Map<String, String> infos = new LinkedHashMap<String, String>();
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
			L.e(TAG, "an error occured when collect package info" + e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				L.e(TAG, "an error occured when collect crash info" + e);
			}
		}
		return infos;
	}

	/**
	 * 保存错误信息到文件中
	 *
	 * @return 返回文件名称, 便于将文件传送到服务器
	 */
	protected String saveCrashInfo2File(Throwable ex,Map<String, String> deviceInfo) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : deviceInfo.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		L.e("Exception", sb.toString());//打印出异常，以便调试
		try {
			long timestamp = System.currentTimeMillis();
			String time = DateUtil.date2Str(new Date(),DateUtil.FORMAT_FULL_SN);
			String fileName = time + "-" + timestamp + ".log";
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String packgeName = ApplicationUtils.getPackgeName(context);
				String folderName = packgeName.substring(packgeName.lastIndexOf(".")+1);
				String path = SDCardUtil.getSDCardPath()+folderName+File.separator+"log"+File.separator;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			L.e(TAG, "an error occured while writing file..." + e);
		}
		return null;
	}
}
