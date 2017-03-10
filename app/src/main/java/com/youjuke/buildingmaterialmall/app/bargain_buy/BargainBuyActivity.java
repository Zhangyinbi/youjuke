package com.youjuke.buildingmaterialmall.app.bargain_buy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.L;

/**
 * 描述:
 * 一元超值购
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 17:38
 */
public class BargainBuyActivity extends BaseActivity {

    private Toolbar toolBar;
    private String link;

    private void assignViews() {
        initDialogUtils();
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        initDialogUtils();
    }

    private WebViewClient client;

    /**
     * 因为是网页所以预加载3秒
     */
    public void initDialogUtils() {
        CountDownTimer initTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mDialog.showDialog();
            }

            @Override
            public void onFinish() {
                mDialog.dimissDialog();
            }
        };
        initTimer.start();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        link = getIntent().getStringExtra("link");
        client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {

                if (s.contains("/redemption/detail?")) {
                    Intent intent = new Intent(BargainBuyActivity.this, BargainBuyDetailsActivity.class);
                    intent.putExtra("url", s);
                    startActivity(intent);
                    return true;
                }

                if (s.contains("/onsale/fuwu")) {
                    startActivity(new Intent(BargainBuyActivity.this, ServiceAssuranceActivity.class));
                    return true;
                }

                webView.loadUrl(s);
                return true;
            }
        };
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.setWebChromeClient(new WebChromeClient());
        L.d("一元换购地址"+link);
        mTBSWebView.loadUrl(link);

//        L.d("//开始读取js");
//        mTBSWebView.loadUrl("javascript:funFromjs()");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bargain_buy;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(toolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
