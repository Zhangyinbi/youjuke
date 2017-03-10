package com.youjuke.buildingmaterialmall.app.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.baidu.mobstat.StatService;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyDetailsActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.x5_web_view.X5WebView;

import org.jetbrains.annotations.Contract;

import java.util.Map;

/**
 * 描述: 一元换购
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-17 20:15
 */

public class OneYuanBuyFragment extends BaseFragment{
    private WebViewClient client;
    private X5WebView x5WebView;
    private float off = 0f;
    private static final int MIXOFF = 100;//偏移量小于这个值不刷新

    private void assignViews() {
        x5WebView = (X5WebView) parentView.findViewById(R.id.TBS_web_view);
    }

    @Contract(" -> !null")
    public static OneYuanBuyFragment newInstance() {
        return new OneYuanBuyFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_one_yuan_buy;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
        client = new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {

                if (s.contains("/redemption/detail?")) {
                    Intent intent = new Intent(getActivity(), BargainBuyDetailsActivity.class);
                    intent.putExtra("url", s);
                    startActivity(intent);
                    return true;
                }

                if (s.contains("/onsale/old_item?")){
                    Map<String, String> stringStringMap = CRequest.URLRequest(s);
                    String id = stringStringMap.get("id");
                    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    return true;
                }

                if (s.contains("/onsale/fuwu")) {
                    startActivity(new Intent(getActivity(), ServiceAssuranceActivity.class));
                    return true;
                }

                webView.loadUrl(s);
                return true;
            }
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                mDialog.showDialog();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mDialog.dimissDialog();
            }

        };
        x5WebView.setWebViewClient(client);
        x5WebView.setWebChromeClient(new WebChromeClient());
        if (BuildingMaterialApp.user != null){
            x5WebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_BARGAIN_BUY
                    +"&userId="+BuildingMaterialApp.user.getId());
        }else{
            x5WebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_BARGAIN_BUY);
        }
    }


    //百度统计一元购列表页面浏览统计
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(),"一元换购列表浏览量");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        L.d("一元换购:"+hidden);
        if (!hidden){
            if (BuildingMaterialApp.user != null){
                x5WebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_BARGAIN_BUY
                        +"&userId="+BuildingMaterialApp.user.getId());
            }else{
                x5WebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_BARGAIN_BUY);
            }
        }
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"一元换购列表浏览量");
    }
}
