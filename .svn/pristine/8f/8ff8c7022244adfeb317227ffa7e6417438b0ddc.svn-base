package com.youjuke.buildingmaterialmall.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.youjuke.swissgearlibrary.base.BaseApplication;
import com.youjuke.swissgearlibrary.base.BaseExceptionHandler;
import com.youjuke.swissgearlibrary.manager.ActivityManager;


/**
 * 描述: 全局异常处理类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-16 14:31
 */
public class CrashHandler extends BaseExceptionHandler {

	public CrashHandler(Context context) {
		super(context);
	}



	@Override
	public boolean handleException(Throwable ex) {
		if (ex == null)
			return false;

		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(context, "很抱歉，程序出现异常", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();
		try {
			if (BaseApplication.isSaveErrorLog){
				//保存错误日志
				String filename = saveCrashInfo2File(ex,collectDeviceInfo());
				//如果处理了,让程序继续运行1秒后退出,保证错误日志的保存
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void onfinish() {
		//退出程序  只要执行推出程序  就会执行3次
		ActivityManager.getInstance().finishAllActivity();
	}
}
