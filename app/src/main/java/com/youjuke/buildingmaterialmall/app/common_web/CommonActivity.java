package com.youjuke.buildingmaterialmall.app.common_web;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.utils.ShareUtils;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

/**
 * 描述:通用网页Activity
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-13 16:40
 */
public class CommonActivity extends BaseActivity {

    private WebViewClient client;
    private Toolbar toolbar;
    private String url;
    private String title;
    private TextView toolText;

    private void assignViews() {
        toolText = (TextView) findViewById(R.id.tool_bar_text_view);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        //读取URL
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        toolText.setText(title);
        client = new CommonViewClient(this) {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                if(mDialog!=null&&mDialog.isShowing())
                    return;
                mDialog.showDialog();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if(mDialog!=null&&!mDialog.isShowing())
                    return;
                mDialog.dimissDialog();
            }
        };

        mTBSWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                if (message != null) {
                    //弹出对话框
                    ToastUtil.show(CommonActivity.this, message);
                }
                jsResult.cancel();
                return true;
            }
        });
        mTBSWebView.addJavascriptInterface(this, "app");
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    public void initToolBar() {
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 灵感列表页面分享 交互js方法 此方法运行在JavaBridge线程中
     */
    @JavascriptInterface
    public void share(String id){
//        L.i("灵感id:" + id);
        params.clear();
        params.put("id",id);
        new ShareUtils().with(this)
                .setApi(ApiContstants.LG_SHARE)
                .setParams(params)
                .toShare();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
