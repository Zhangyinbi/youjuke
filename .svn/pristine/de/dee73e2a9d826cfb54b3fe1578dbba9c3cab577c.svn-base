package com.youjuke.buildingmaterialmall.utils;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;

import com.tencent.smtt.export.external.interfaces.*;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.Map;

/**
 * 描述:
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 14:49
 */
public class MyWebViewClient extends WebViewClient{



    public static String injectIsParams(String url) {
        if (url != null && !url.contains("xxx=")){
            if (url.contains("?")) {
                return url + "&xxx=1";
            } else {
                return url + "?xxx=1";
            }
        } else {
            return url;
        }
    }

    // 防止加载网页时调起系统浏览器
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, final com.tencent.smtt.export.external.interfaces.WebResourceRequest request) {

        if (request != null && request.getUrl() != null) {
            String scheme = request.getUrl().getScheme().trim();
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                return super.shouldInterceptRequest(view, new com.tencent.smtt.export.external.interfaces.WebResourceRequest() {
                    @Override
                    public Uri getUrl() {
                        //return Uri.parse(injectIsParams(request.getUrl().toString()));

                        return Uri.parse(request.getUrl().toString());
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public boolean isForMainFrame() {
                        return request.isForMainFrame();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public boolean hasGesture() {
                        return request.hasGesture();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public String getMethod() {
                        return request.getMethod();
                    }

                    @SuppressLint("NewApi")
                    @Override
                    public Map<String, String> getRequestHeaders() {
                        return request.getRequestHeaders();
                    }
                });
            }
        }

        return super.shouldInterceptRequest(view, request);
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (!TextUtils.isEmpty(url) && Uri.parse(url).getScheme() != null) {
            String scheme = Uri.parse(url).getScheme().trim();
            if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
                return super.shouldInterceptRequest(view, injectIsParams(url));
            }
        }
        return super.shouldInterceptRequest(view, url);
    }

}
