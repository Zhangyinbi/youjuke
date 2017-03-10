package com.youjuke.buildingmaterialmall.app.special_selling;

import android.content.Context;
import android.content.Intent;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.seckill.SeckillDetailsActivity;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;
import java.util.Map;

/**
 * 描述:
 * 精选特卖拦截
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-19 18:11
 */
public class SpecialSellingVebViewClient extends WebViewClient {
    Context mContex;

    public SpecialSellingVebViewClient(Context mContex) {
        this.mContex = mContex;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {

        if (s.contains("/onsale/old_item?id=")){
            //解析出链接中的所有参数
            Map<String, String> stringStringMap = CRequest.URLRequest(s);
            String id = stringStringMap.get("id");
            L.i("拦截商品详情  id = "+id);
            L.i("拦截商品详情  url = "+s);
            Intent intent = new Intent(mContex, ProductDetailsActivity.class);
            intent.putExtra("id",id);
            mContex.startActivity(intent);
            return true;
        }
        if (s.contains("/onsale/fuwu")){

            mContex. startActivity(new Intent(mContex, ServiceAssuranceActivity.class));

        }

        //onPageStarted(webView,"加载中.....",);

        //防止加载系统的webView
        webView.loadUrl(s);
        return true;
    }
}
