package com.youjuke.swissgearlibrary.base;

import android.app.Application;

import com.baidu.mobstat.StatService;
import com.youjuke.swissgearlibrary.utils.ApplicationUtils;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SDCardUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.io.File;

/**
 * 描述: Application 的基类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-07-14 11:04
 */
public abstract class BaseApplication extends Application {

	public static BaseApplication mInstance;

	/**
	 * 是否是测试模式,打包上线APK的时候一定要更改
	 */
	private static boolean isDebug = true;

	/**
	 * 首页按返回键的次数
	 */
	public static int BackKeyCount = 0;

	/**
	 * 是否使用自动堆栈管理
	 */
	public static boolean isUseActivityManager = true;


	/**
	 * 是否保存错误日志
	 */
	public static boolean isSaveErrorLog = true;


	/**
	 * SD卡项目地址
	 */
	public static String fileName = "swissgear";


	/**
	 * 设置是否是测试模式
	 */
	@SuppressWarnings("static-access")
	public void setIsDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	/**
	 * 获取当前是否是测试模式。true为测试环境，false为正式环境
	 *
	 * @return
	 */
	public static boolean getIsDebug() {
		return isDebug;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		//百度统计
		StatService.start(this);
		// 设置工程是否打印LOG
		L.isDebug = isDebug;
		//配置时候显示toast
		ToastUtil.isShow = true;
		//设置SD卡 项目目录
		createAPPDir();
		//配置程序异常退出,异常捕获处理类
		setDefaultUncaughtExceptionHandler();
	}

	/**
	 * 创建SD卡 项目目录
	 */
	public void createAPPDir(){
		String sdFilePath = ApplicationUtils.getSDFilePath(this);
		fileName = ApplicationUtils.getPackgeName(this);
		if (SDCardUtil.isSDCardEnable()){
			File file = new File(sdFilePath);
			if (!file.exists()){
				file.mkdirs();
			}
		}
	}


	/**
	 * 配置程序异常退出,异常捕获处理类
	 */
	public abstract void setDefaultUncaughtExceptionHandler();

	public static BaseApplication getInstance()
	{
		return mInstance;
	}
}
