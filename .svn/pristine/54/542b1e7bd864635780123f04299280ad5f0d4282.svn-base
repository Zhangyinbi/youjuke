package com.youjuke.buildingmaterialmall.app.home;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.common_web.CommonViewClient;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.utils.ShareUtils;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class TopNewsOfDecorationDetailsActivity extends BaseActivity {

    private WebViewClient client;
    private Toolbar toolbar;
    private String url;
    private String title;
    private TextView toolText;
    private ImageView mImgShare;
    private String id;

    private void assignViews() {
        toolText = (TextView) findViewById(R.id.tool_bar_text_view);
        mImgShare = (ImageView) findViewById(R.id.img_share);
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
        Map<String, String> stringStringMap = CRequest.URLRequest(url);
        id = stringStringMap.get("id");
        client = new CommonViewClient(this) {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                if(mDialog.isShowing())
                    return;
                mDialog.showDialog();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if(!mDialog.isShowing())
                    return;
                mDialog.dimissDialog();
            }
        };

        mTBSWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                if (message != null) {
                    //弹出对话框
                    ToastUtil.show(TopNewsOfDecorationDetailsActivity.this, message);
                }
                jsResult.cancel();
                return true;
            }
        });
        mTBSWebView.addJavascriptInterface(this, "app");
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(url);

        mImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id",id);
                new ShareUtils().with(TopNewsOfDecorationDetailsActivity.this)
                        .setApi(ApiContstants.ZX_SHARE)
                        .setParams(params)
                        .toShare();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_news_of_decoration_details;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
