package com.youjuke.buildingmaterialmall.app.me;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.Constant;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述: 装修跟踪activity
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-22 14:16
 */

public class DecorationTrackingActivity extends BaseActivity{

    private WebViewClient client;
    private Toolbar toolbar;
    private TextView toolText;

    private void assignViews() {
        toolText = (TextView) findViewById(R.id.tool_bar_text_view);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                //防止加载系统的webView
                if (s.contains("tel:")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(s)));
                    return true;
                }
                webView.loadUrl(s);
                return true;
            }
        };
        mTBSWebView.setWebViewClient(client);
        if (BuildingMaterialApp.user != null) {
            mTBSWebView.loadUrl(Constant.URL_REBATE + BuildingMaterialApp.user.getMobile());
        } else {
            mTBSWebView.loadUrl(Constant.URL_REBATE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_decoration_tracking;
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

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildingMaterialApp.user != null) {
            mTBSWebView.loadUrl(Constant.URL_REBATE + BuildingMaterialApp.user.getMobile());
        } else {
            mTBSWebView.loadUrl(Constant.URL_REBATE);
        }
    }
}
