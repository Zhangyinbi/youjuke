package com.youjuke.buildingmaterialmall.app.service_customer;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述:
 * 客服页面
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-19 17:18
 */
public class ServiceCustomerActivity extends BaseActivity {

    private WebViewClient client;
    private Toolbar mserviceToolBar;

    private void assignViews() {
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        mserviceToolBar = (Toolbar) findViewById(R.id.service_tool_bar);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        assignViews();
        client=new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        };
        mTBSWebView.setWebChromeClient(new WebChromeClient());
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_SERVICE_CUSTOMER);




    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_customer;
    }




    @Override
    public void initToolBar() {
        mserviceToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
