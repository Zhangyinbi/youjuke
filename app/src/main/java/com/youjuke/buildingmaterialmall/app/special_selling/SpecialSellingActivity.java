package com.youjuke.buildingmaterialmall.app.special_selling;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述:
 * 精选特卖
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 17:24
 */
public class SpecialSellingActivity extends BaseActivity{

    private WebViewClient client;
    private Toolbar special_selling_toolbar;

    private void assignViews() {
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        special_selling_toolbar = (Toolbar) findViewById(R.id.special_selling_toolbar);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        client=new SpecialSellingVebViewClient(this);
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_SPECIAL_SELLING);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_special_selling;
    }

    @Override
    public void initToolBar() {
        special_selling_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
