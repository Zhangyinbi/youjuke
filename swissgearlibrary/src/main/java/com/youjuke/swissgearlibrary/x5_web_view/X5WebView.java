package com.youjuke.swissgearlibrary.x5_web_view;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.sdk.ValueCallback;
//import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebHistoryItem;
import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.x5_web_view.utils.SecurityJsBridgeBundle;
import com.youjuke.swissgearlibrary.x5_web_view.utils.X5WebViewEventHandler;
//import com.tencent.smtt.sdk.WebViewDatabase;

public class X5WebView extends WebView {
    public static final int FILE_CHOOSER = 0;
    private String resourceUrl = "";
    private WebView smallWebView;
    private static boolean isSmallWebViewDisplayed = false;
    private boolean isClampedY = false;
    private Map<String, Object> mJsBridges;
    private boolean refresh=false;
    TextView title;
    //    private WebViewClient client = new WebViewClient() {
//        /**
//         * 防止加载网页时调起系统浏览器
//         */
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//        public void onReceivedHttpAuthRequest(WebView webview,
//                                              com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
//                                              String realm) {
//            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
//        }
//    };
    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }

        View myVideoView;
        View myNormalView;
        CustomViewCallback callback;


        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        @Override
        public boolean onShowFileChooser(WebView arg0,
                                         ValueCallback<Uri[]> arg1, FileChooserParams arg2) {
            // TODO Auto-generated method stub
            Log.e("app", "onShowFileChooser");
            return super.onShowFileChooser(arg0, arg1, arg2);
        }

        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
            Log.e("app", "openFileChooser");
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                ((Activity) (X5WebView.this.getContext())).startActivityForResult(Intent.createChooser(intent, "choose files"),
                        1);
            } catch (android.content.ActivityNotFoundException ex) {

            }

            super.openFileChooser(uploadFile, acceptType, captureType);
        }


        @Override
        public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {

            Log.i("yuanhaizhou", "setX5webview = null");
            return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
        }

        /**
         * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
         */
        @Override
        public boolean onJsPrompt(WebView arg0, String arg1, String arg2, String arg3, JsPromptResult arg4) {
            // 在这里可以判定js传过来的数据，用于调起android native 方法
            if (X5WebView.this.isMsgPrompt(arg1)) {
                if (X5WebView.this.onJsPrompt(arg2, arg3)) {
                    return true;
                } else {
                    return false;
                }
            }
            return super.onJsPrompt(arg0, arg1, arg2, arg3, arg4);
        }

        @Override
        public void onReceivedTitle(WebView arg0, final String arg1) {
            super.onReceivedTitle(arg0, arg1);
            Log.i("yuanhaizhou", "webpage title is " + arg1);

        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClientExtension(new X5WebViewEventHandler(this));// 配置X5webview的事件处理
        // this.setWebViewClient(client);
        this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);

    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(false);//是否允许访问文件
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webSetting.setSupportZoom(false);//支持缩放
        webSetting.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSetting.setPluginsEnabled(true);//支持插件
        webSetting.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSetting.setSupportMultipleWindows(false);//是否支持新窗口
        webSetting.setLoadWithOverviewMode(true);//充满全屏
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        //设置滚动条隐藏
        webSetting.setGeolocationEnabled(true);
        //缓存大小
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        //把图片放在最后渲染
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //缓存
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    public X5WebView(Context arg0) {
        super(arg0);
    }

    public static void setSmallWebViewEnabled(boolean enabled) {
        isSmallWebViewDisplayed = enabled;
    }


    /**
     * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
     *
     * @param methodName 方法名称
     * @param blockName  区块名称
     * @return true ：调用成功 ； false ：调用失败
     */
    private boolean onJsPrompt(String methodName, String blockName) {
        String tag = SecurityJsBridgeBundle.BLOCK + blockName + "-" + SecurityJsBridgeBundle.METHOD + methodName;

        if (this.mJsBridges != null && this.mJsBridges.containsKey(tag)) {
            ((SecurityJsBridgeBundle) this.mJsBridges.get(tag)).onCallMethod();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判定当前的prompt消息是否为用于调用native方法的消息
     *
     * @param msg 消息名称
     * @return true 属于prompt消息方法的调用
     */
    private boolean isMsgPrompt(String msg) {
        if (msg != null && msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)) {
            return true;
        } else {
            return false;
        }
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_dispatchTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_dispatchTouchEvent(ev);
        android.util.Log.d("Bran", "dispatchTouchEvent " + ev.getAction() + " " + r);
        return r;
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_onInterceptTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_onInterceptTouchEvent(ev);
        return r;
    }

    public void tbs_onScrollChanged(int l, int t, int oldl, int oldt, View view) {
        super_onScrollChanged(l, t, oldl, oldt);
    }

    public void tbs_onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {

        L.d("clampedY"+clampedY);

        if (clampedY) {
            this.isClampedY = true;

        } else {
            this.isClampedY = false;
        }
        if (refresh&&this.getTop()<=2){
            this.reload();
            refresh=false;
        }

        super_onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    public void tbs_computeScroll(View view) {
        super_computeScroll();
    }

    public boolean tbs_overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                    int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {

        if (this.getTop()>=100){
            refresh=true;
        }
        L.d("top-------------"+this.getTop());

        if (this.isClampedY&&deltaY<=0) {
            this.layout(this.getLeft(), this.getTop() + (-deltaY) / 4, this.getRight(),
                    this.getBottom() + (-deltaY) / 4);
        }



        return super_overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }


    public boolean tbs_onTouchEvent(MotionEvent event, View view) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.isClampedY = false;
            this.layout(this.getLeft(), 0, this.getRight(), this.getBottom());
        }

        return super_onTouchEvent(event);
    }
}
