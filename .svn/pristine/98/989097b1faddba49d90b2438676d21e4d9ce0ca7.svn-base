package com.youjuke.buildingmaterialmall.app.product_details;

import android.content.Context;
import android.content.Intent;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyActivity;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;

import java.util.Map;

/**
 * 描述: 特选精卖 商品详情页 webView
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-21 15:34
 */

public class ProductDetailsWebViewClient extends WebViewClient {

    private Context mContext;

    public ProductDetailsWebViewClient(Context mContext) {
        this.mContext = mContext;
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        if (s.contains("/onsale/old_item?id=")){
            //解析出链接中的所有参数
            Map<String, String> stringStringMap = CRequest.URLRequest(s);
            String id = stringStringMap.get("id");
            L.i("拦截商品详情  id = "+id);
            L.i("拦截商品详情  url = "+s);
            RxBus.get().post("goToOtherProduct",id);
            return true;
        }

        if (s.contains("/onsale/fuwu")){

            mContext.startActivity(new Intent(mContext, ServiceAssuranceActivity.class));
            return true;
        }

        //防止加载系统的webView
        webView.loadUrl(s);
        return true;
    }
}
