package com.youjuke.buildingmaterialmall.app.schedule_details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-27 13:54
 */
public class FriendshipTipsActivity extends BaseActivity {


    private Toolbar toolBar;
    private TextView toolText;
    private ImageView image;
    private WebViewClient client;
    private void assignViews() {
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        toolText = (TextView) findViewById(R.id.tool_text);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        image = (ImageView) findViewById(R.id.image);
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
        mTBSWebView.setWebViewClient(client);
       String  FriendShipUrl=getIntent().getStringExtra("FriendShipUrl");
        mTBSWebView.loadUrl(FriendShipUrl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_ship_tips;
    }

    @Override
    public void initToolBar() {
        toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
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

        if (keyCode==KeyEvent.KEYCODE_BACK){

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
