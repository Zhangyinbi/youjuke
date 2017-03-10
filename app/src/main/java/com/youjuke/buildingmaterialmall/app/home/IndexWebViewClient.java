package com.youjuke.buildingmaterialmall.app.home;

import android.content.Context;
import android.content.Intent;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.seckill.SeckillDetailsActivity;
import com.youjuke.buildingmaterialmall.app.snatch.SnatchActivity;
import com.youjuke.buildingmaterialmall.app.special_selling.SpecialSellingActivity;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;

import java.util.Map;

/**
 * 描述:
 *  WebView客户端 ，截取链接跳转
 *
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 17:17
 */
public class IndexWebViewClient extends WebViewClient{
    private Context mContex;
    public IndexWebViewClient(Context mContex) {
        this.mContex = mContex;
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {

        if (s.contains("/onsale/item")){
            L.i("拦截秒杀URL");
            mContex.startActivity(new Intent(mContex, SeckillDetailsActivity.class));
            return true;
        }
        if (s.contains("/jchd.html")){
            L.i("拦截导航轮换现场特卖");
            RxBus.get().post("showTab","jchd");
            return true;
        }

        if (s.contains("/onsale/oldlist")){
            L.i("拦截特卖URL");
            mContex.startActivity(new Intent(mContex, SpecialSellingActivity.class));
            return true;
        }


        if (s.contains("/snatch/index")){
            L.i("拦截一元夺宝URL");
            mContex.startActivity(new Intent(mContex, SnatchActivity.class));
            return true;
        }

        if (s.contains("/redemption/index")){
            L.i("拦截一元 超值购URL");
            mContex.startActivity(new Intent(mContex, BargainBuyActivity.class));
            return true;
        }

        if (s.contains("/onsale/old_item?id=")){
            Map<String, String> stringStringMap = CRequest.URLRequest(s);
            String id = stringStringMap.get("id");
            L.i("拦截商品详情  id = "+id);
            L.i("拦截商品详情  url = "+s);
            Intent intent = new Intent(mContex, ProductDetailsActivity.class);
            intent.putExtra("id",id);
            mContex.startActivity(intent);
            return true;
        }
//
//        if (s.contains("/onsale/fuwu")){
//
//            L.i("服务保障URL");
//            mContex.startActivity(new Intent(mContex, ServiceAssuranceActivity.class));
//            return true;
//        }
        //onPageStarted(webView,"加载中.....",);
        //防止加载系统的webView
        return true;
    }
}
