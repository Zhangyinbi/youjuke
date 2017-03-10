package com.youjuke.swissgearlibrary.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.service.HideService;
import com.youjuke.swissgearlibrary.utils.NetUtil;
import com.youjuke.swissgearlibrary.utils.StatusBarCompat;
import com.youjuke.swissgearlibrary.widgets.dialog.ShapeLoadingDialogMessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * 描述:Acitivity基类
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-06 13:38
 * #0001    Tian Xiao    2016-09-14 添加TBS的配置
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    public ShapeLoadingDialogMessage mDialog;
    protected CompositeSubscription compositeSubscription;
    /**
     * 访问网络时 提交数据的集合
     */
    protected Map<String, Object> params = new HashMap<String, Object>();

    protected Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN&&
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setSharedElementEnterTransition(new AutoTransition());//设置共享元素的进入动画
//            getWindow().setSharedElementExitTransition(new AutoTransition());//设置共享元素的退出动画
//        }
            //设置布局内容
        setContentView(getLayoutId());
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //初始化Dialog
        mDialog = ShapeLoadingDialogMessage.newInstance(this);
        //初始化全局RX订阅者
        compositeSubscription = new CompositeSubscription();
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//        //适配4.4系统状态栏
//        StatusBarCompat.compat(this);
//        }
        StatusBarUtil.setTransparent(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //添加进栈
        ActivityManager.getInstance().addActivity(this);
        RxBus.get().register(this);
        //TBS配置
        if (mTBSWebView != null) {
//            if (mTBSWebView.getX5WebViewExtension()!=null) {
//                L.d("X5 --TBS 加载");
                initializeWebSetting();
           // }
        }
        startHideService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTBSWebView != null) {
            //释放资源
            mTBSWebView.stopLoading();
//            mTBSWebView.removeAllViews();
//            mTBSWebView.destroy();
            mTBSWebView = null;
        }
        if (mDialog != null){
            if (mDialog.isShowing()){
                mDialog.dimissDialog();
            }
            mDialog = null;
        }

        if (compositeSubscription != null
                && !compositeSubscription.isUnsubscribed())
            compositeSubscription.unsubscribe();
        RxBus.get().unregister(this);
        ActivityManager.getInstance().finishActivity(this);
        stopHideService();
    }

    public abstract void initViews(Bundle savedInstanceState);

    public abstract int getLayoutId();

    public abstract void initToolBar();

    /**
     * 获取Intent
     */
    protected void handleIntent(Intent intent) {
    }


    /**
     * 开始预加载进程
     */
    private void startHideService(){
        Intent intent = new Intent(this, HideService.class);
        this.startService(intent);
    }

    private void stopHideService(){
        Intent intent = new Intent(this, HideService.class);
        this.stopService(intent);
    }

    /**
     * 沉浸式辅助
     *
     * @param activity
     */
    protected void setRootView(Activity activity) {
        StatusBarCompat.setTransparent(activity);
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        ViewGroup childAt = (ViewGroup) rootView.getChildAt(0);
        ViewGroup childAt1 = (ViewGroup) childAt.getChildAt(0);
        childAt1.setFitsSystemWindows(true);
        childAt1.setClipToPadding(true);
    }

    /**
     * ----------------TBS 配置--------------------
     */

    protected WebSettings mWebSetting;

    private String appCacheDir;

    protected WebView mTBSWebView;
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }


    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                hideInputMethod(this, v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * TBSWebSetting，TbsWeb缓存
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void initializeWebSetting() {

        //5.0开始改变缓存方式
        if (Build.VERSION.SDK_INT >= 19) {
            mTBSWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //获取WebSettings
        mWebSetting = mTBSWebView.getSettings();
        mWebSetting.setDefaultTextEncodingName("utf-8"); //设置文本编码
        //确认加载JS
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setDomStorageEnabled(true);
        // 是否支持缩放
        mWebSetting.setSupportZoom(false);
        mWebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebSetting.setAppCacheMaxSize(1024 * 1024 * 20);//设置缓存大小
        //设置缓存路径
        appCacheDir = Environment.getExternalStorageDirectory().getPath() + "/youjueke/cache/";
        File fileSD = new File(appCacheDir);
        if (!fileSD.exists()) {
            fileSD.mkdir();
        }
        mWebSetting.setAppCachePath(appCacheDir);
        mWebSetting.setAllowContentAccess(true);
        mWebSetting.setAppCacheEnabled(true);
        if (NetUtil.isConnected(this) || NetUtil.isWIFI(this)) {
            //有网络网络加载
            mWebSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无网时本地缓存加载
            mWebSetting.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
        mWebSetting.setRenderPriority(WebSettings.RenderPriority.NORMAL);//渲染
        mWebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);//缓存模式。比如说某种情况下自动清理缓存
        //TODO TBS的长按事件暂住禁止掉
        mTBSWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
