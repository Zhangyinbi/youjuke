package com.youjuke.buildingmaterialmall;

import android.content.Intent;

import com.fm.openinstall.OpenInstall;
import com.tencent.smtt.sdk.TbsDownloader;
import com.youjuke.buildingmaterialmall.entity.User;
import com.youjuke.buildingmaterialmall.utils.CrashHandler;
import com.youjuke.swissgearlibrary.base.BaseApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * 描述:
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-06 13:50
 */
public class BuildingMaterialApp extends BaseApplication {
    //线下特卖
    public static BuildingMaterialApp mInstance;
    public static final String WEB_FILE_PATH_INDEX = "index";
    public static final String WEB_FILE_PATH_FLASEH_SALE = "flasehSale";
    public static final String WX_PAY_API_KEY = "248f1e86353c1d1ac653dc33614ddba8";


    //http://prebk.youjuke.com/onsale/index
    public static final String YOU_JU_KE_INDEX="http://prebk.youjuke.com/onsale/index?platform=app";
    public static final String YOU_JU_KE_FLASH_SALE="http://prebk.youjuke.com/jchd.html?putin=weixin&child=fwhsc";
    public static final String YOU_JU_KE_SECKILL_DETAILS="http:/prebk.youjuke.com/onsale/item?platform=app";
    public static final String YOU_JU_KE_SPECIAL_SELLING="http://prebk.youjuke.com/onsale/oldlist?platform=app";
    public static final String YOU_JU_KE_SNATCH="http://prebk.youjuke.com/snatch/index?platform=app";
    public static final String YOU_JU_KE_BARGAIN_BUY=
            "http://prebk.youjuke.com/redemption/index?platform=app";
    public static final String YOU_JU_KE_PRODUCTDETAILS="http://prebk.youjuke.com/onsale/old_item?platform=app";
    public static final String YOU_JU_KE_NEW_PRODUCTDETAILS="http://prebk.youjuke.com/onsale/putsale?platform=app";
    public static final String YOU_JU_KE_SERVICE_ASSURANCE="http://prebk.youjuke.com/onsale/fuwu?platform=app";
    //public static final String YOU_JU_KE_SERVICE_CUSTOMER="http://prebk.youjuke.com/index/kf_html";
    public static final String YOU_JU_KE_SERVICE_CUSTOMER = "http://m.youjuke.com/index/kf_html";


    /*public static final String YOU_JU_KE_FLASH_SALE = "http://m.youjuke.com/jchd.html?putin=weixin&child=fwhsc";
    public static final String YOU_JU_KE_PRODUCTDETAILS = "http://m.youjuke.com/onsale/old_item?platform=app";
    public static final String YOU_JU_KE_INDEX = "http://m.youjuke.com/onsale/index?platform=app";
    public static final String YOU_JU_KE_SECKILL_DETAILS = "http:/m.youjuke.com/onsale/item?platform=app";
    public static final String YOU_JU_KE_SPECIAL_SELLING = "http://m.youjuke.com/onsale/oldlist?platform=app";
    public static final String YOU_JU_KE_SNATCH = "http://m.youjuke.com/snatch/index?platform=app";
    public static final String YOU_JU_KE_BARGAIN_BUY = "http://m.youjuke.com/redemption/index?platform=app";
    public static final String YOU_JU_KE_SERVICE_ASSURANCE = "http://m.youjuke.com/onsale/fuwu?platform=app";
    public static final String YOU_JU_KE_NEW_PRODUCTDETAILS = "http://m.youjuke.com/onsale/putsale?platform=app";
    public static final String YOU_JU_KE_SERVICE_CUSTOMER = "http://m.youjuke.com/index/kf_html";*/

    public static User user = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        OpenInstall.init(this, "y4yppf");
        JPushInterface.setDebugMode(true);//设置显示调试
        //极光推送
        JPushInterface.init(this);
//
//        JPushInterface.setAlias(this, //上下文对象
//                "test123456", //别名
//                new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
//                    @Override
//                    public void gotResult(int i, String s, Set<String> set) {
//                        Log.d("alias", "set alias result is" + i);
//                    }
//                });
//         搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        TbsDownloader.needDownload(getApplicationContext(), false);

        initX5();//TBS预加载
    }

    private void initX5() {
        Intent intent = new Intent(this, PreLoadX5Service.class);
        startService(intent);
    }

    //设置异常处理类
    @Override
    public void setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    }

    public static BuildingMaterialApp getInstance() {
        return  mInstance;
    }
}
