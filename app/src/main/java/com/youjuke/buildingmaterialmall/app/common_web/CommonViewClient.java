package com.youjuke.buildingmaterialmall.app.common_web;

import android.content.Context;
import android.content.Intent;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.app.home.TopNewsOfDecorationDetailsActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.swissgearlibrary.utils.CRequest;

import java.util.Map;


/**
 * 描述:
 *
 * <p>
 *
 * 工程:
 * #0000    Tian Xiao    2016-09-19 18:00
 */
public class CommonViewClient extends WebViewClient {

    Context mContex;

    public CommonViewClient(Context mContex) {
        this.mContex = mContex;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {

        if (s.contains("/cps/chat?siteId=9828051&userId=7157574")||s.contains("/index/kf_html")){
            mContex.startActivity(new Intent(mContex, ServiceCustomerActivity.class));
            return true;
        }

        if (s.contains("onsale/old_item?id="))
        {
            Map<String, String> stringStringMap = CRequest.URLRequest(s);
            String id = stringStringMap.get("id");
            Intent intent = new Intent(mContex, ProductDetailsActivity.class);
            intent.putExtra("id",id);
            mContex.startActivity(intent);
            return true;

        }

        if (s.contains("onsale/fuwu")){

            mContex.startActivity(new Intent(mContex, ServiceAssuranceActivity.class));
            return true;
        }

        if (s.contains("/MaterialMall/zxlist?id=")){
            Intent intent = new Intent(mContex, TopNewsOfDecorationDetailsActivity.class);
            intent.putExtra("url",s);
            intent.putExtra("title","头条详情");
            mContex.startActivity(intent);
            return true;
        }

        //onPageStarted(webView,"加载中.....",);

        //防止加载系统的webView
        webView.loadUrl(s);
        return true;
    }

}
