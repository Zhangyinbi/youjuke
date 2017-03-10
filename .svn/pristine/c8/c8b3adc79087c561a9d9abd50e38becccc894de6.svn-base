package com.youjuke.swissgearlibrary.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**

 * <p/>
 * App工具类
 */
public class ApplicationUtils
{

    /**
     * 备份App 首先无需提升权限就就可以复制APK，查看权限你就会知道，在data/app下的APK权限如下：-rw-r--r-- system
     *
     * @param packageName
     * @param mActivity
     * @throws IOException
     */
    public static void backupApp(String packageName, Activity mActivity) throws IOException
    {
        // 存放位置
        String newFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        String oldFile = null;
        try
        {
            // 原始位置
            oldFile = mActivity.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        System.out.println(newFile);
        System.out.println(oldFile);

        File in = new File(oldFile);
        File out = new File(newFile + packageName + ".apk");
        if (!out.exists())
        {
            out.createNewFile();
            Toast.makeText(mActivity, "文件备份成功！" + "存放于" + newFile + "目录下",Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(mActivity, "文件已经存在！" + "查看" + newFile + "目录下", Toast.LENGTH_SHORT).show();
        }

        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);

        int count;
        // 文件太大的话，我觉得需要修改
        byte[] buffer = new byte[256 * 1024];
        while ((count = fis.read(buffer)) > 0)
        {
            fos.write(buffer, 0, count);
        }

        fis.close();
        fos.flush();
        fos.close();
    }

    /**
     * 获取当前Apk版本号 android:versionCode
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context)
    {

        int verCode = -1;
        try
        {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e)
        {
        }
        return verCode;
    }

    public static String getVerName(Context context)
    {

        try
        {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e)
        {
        }
        return "";
    }

    /**
     * 返回当前的应用是否处于前台显示状态 不需要android.permission.GET_TASKS权限
     *
     * @param packageName
     * @return
     */
    public static boolean isTopActivity(Context context, String packageName)
    {
        // _context是一个保存的上下文
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() == 0)
            return false;
        for (ActivityManager.RunningAppProcessInfo process : list)
        {
            if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.processName.equals(packageName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测APP是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkAppExist(Context context, String packageName)
    {

        try
        {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null && info.packageName.equals(packageName);
        } catch (PackageManager.NameNotFoundException e)
        {

        } catch (Exception e)
        {
        }
        return false;
    }

    /**
     * 判断是否是DEBUG模式
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }

	/**
	 * 获取app名字
	 * @param context
	 * @return
	 */
	public  static String getAppName(Context context){
		PackageManager pm = context.getPackageManager();
		return context.getApplicationInfo().loadLabel(pm).toString();
	}

	/**
	 * 获取包名
	 * @param context
	 * @return
	 */
	public static String getPackgeName(Context context){
		return context.getPackageName();
	}

	/**
	 * 获取在sd卡中文件路径
	 * @param context
	 * @return
	 */
	public static String getSDFilePath(Context context){
		String packgeName = ApplicationUtils.getPackgeName(context);
		String folderName = packgeName.substring(packgeName.lastIndexOf(".")+1);
		return SDCardUtil.getSDCardPath()+folderName+File.separator+"log"+File.separator;
	}

    /**
     * 调用系统浏览器下载
     * @param context
     * @param url
     */
    public static void download(Context context, String url) {
        if (url != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

}
