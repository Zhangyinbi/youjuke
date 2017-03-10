package com.youjuke.buildingmaterialmall.app.service_assurance;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述:服务保障
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-14 18:30
 */
public class ServiceAssuranceActivity  extends BaseActivity{

    private Toolbar toolbar;
    private WebView TBSWebView;
    private WebViewClient client;



    private void assignViews() {
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        client=new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                //webView.loadUrl(s);
                return true;
            }
        };
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_SERVICE_ASSURANCE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_assurance;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
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
