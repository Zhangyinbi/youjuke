package com.youjuke.buildingmaterialmall.utils;

import android.net.Uri;

import java.util.Map;

/**
 * 描述:
 * <p/>
 * 从 Android API 11 (3.0) 开始，WebView 开始在 WebViewClient 内提供了这样一条 API
 *  就是说只要实现 WebViewClient 的 shouldInterceptRequest 方法，然后调用 WebView 的setWebViewClient 就可以了。
 *  但是，在 API21 以上又弃用了上述 API，使用了一条新的 API为了支持尽量多的版本，看来两个都需要实现了，
 *  发现一看就非常好用的 String url 变成了一个WebResourceRequest request。WebResourceRequest 这个东西是一个接口，
 *
 *
 * 工程:
 * #0000    Tian Xiao    2016-09-14 14:39
 */
public interface  WebResourceRequest {

    Uri getUrl();
    boolean isForMainFrame();
    boolean hasGesture();
    String getMethod();
    Map<String, String> getRequestHeaders();
}
