package com.youjuke.buildingmaterialmall.app.start;

import android.content.Intent;
import android.os.Bundle;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.home.MainActivity;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.utils.Installation;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.ApplicationUtils;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述:启动页面
 * <p/>
 * 工程:
 * #0000    mwy    2016-10-10 16:11
 * #0000    mwy    2016-02-28 19:11 增加版本号判断
 */
public class SplashActivity extends BaseActivity implements AppInstallListener {

    Boolean user_first;
    Integer versionCode;

    @Override
    public void initViews(Bundle savedInstanceState) {

        AppData appData = SPUtil.getObject(getApplicationContext(), "appData", AppData.class);
        if (appData!=null){
            saveChanelInfo(appData);
        }

        OpenInstall.getInstall(this, this);
        user_first = (Boolean) SPUtil.get(this,"FIRST",true);
        versionCode = (Integer) SPUtil.get(this,"VersionCode",0);
        if(user_first || versionCode.intValue() == 0
                || versionCode.intValue() < ApplicationUtils.getVerCode(this)){//第一次
            SPUtil.put(this,"FIRST",false);
            SPUtil.put(this,"VersionCode",ApplicationUtils.getVerCode(this));
            startActivity(new Intent(SplashActivity.this,LeadActivity.class));
            finish();

        }else{
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void onInstallFinish(AppData appData, Error error) {
        if (error == null) {
//            ToastUtil.show(this,appData.toString());
            SPUtil.put(this,"channel",appData.getChannel());
            saveChanelInfo(appData);
            L.d("SplashActivity", "OpenInstall install-data : " + appData.toString());
        } else {
            L.d("SplashActivity", "error : "+error.toString());
        }
    }

    /**
     * 下载量统计（set_app_channel）
     */
    private void saveChanelInfo(final AppData appData) {
        params.clear();
        params.put("machine_id", Installation.getUniquePsuedoID().replace("-",""));
        params.put("channel",appData.getChannel());
        params.put("mobile","");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.SET_APP_CHANNEL, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        SPUtil.setObject(getApplicationContext(),"appData",appData);
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if (responseBean.getStatus().equals("400")){
                            SPUtil.setObject(getApplicationContext(),"appData",appData);
                        }else {
                            SPUtil.remove(getApplicationContext(),"appData");
                        }
                    }
                });
    }
}
