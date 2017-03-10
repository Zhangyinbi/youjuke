package com.youjuke.buildingmaterialmall.app.snatch;

import android.os.Bundle;
import android.view.KeyEvent;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述:
 * 一元夺宝
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 17:32
 */
public class SnatchActivity extends BaseActivity {

    private WebViewClient client;

    private void assignViews() {
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        client=new WebViewClient(){};
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_SNATCH);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_snatch;
    }

    @Override
    public void initToolBar() {

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
