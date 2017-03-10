package com.youjuke.buildingmaterialmall.app.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.Constant;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import org.jetbrains.annotations.Contract;

/**
 * 描述: 服务fragment
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-17 19:17
 */

public class ServiceFragment extends BaseFragment {
    private WebViewClient client;
    private Toolbar toolbar;

    private void assignViews() {
        mTBSWebView = (WebView) parentView.findViewById(R.id.TBS_web_view);
        toolbar = $(R.id.tool_bar);
    }

    @Contract(" -> !null")
    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_service;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
        client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                //防止加载系统的webView
                if (s.contains("tel:")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(s)));
                    return true;
                } else if (s.contains("/jchd.html")) {
                    //免费3d效果
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面建材特卖会页面浏览量", "eventLabel", 1);
                } else if (s.contains("/zxgs")) {
                    //免费定制公司
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面免费定制公司页面浏览量", "eventLabel", 1);
                } else if (s.contains("/zxgw.html")) {
                    //免费VIP装修顾问
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面vip装修顾问页面浏览量", "eventLabel", 1);
                } else if (s.contains("/daikuan.html")) {
                    //免息家装分期
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面免息家装分期页面浏览量", "eventLabel", 1);
                } else if (s.contains("/zhuangxiutuoguan.html")) {
                    //装修全程托管
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面装修托管页面浏览量", "eventLabel", 1);
                } else if (s.contains("/stagejianli.html")) {
                    //免费节点监理
                    //百度统计点击量
                    StatService.onEvent(mContext, "服务页面免费节点监理页面浏览量", "eventLabel", 1);
                } else if (s.contains("/cps/chat?siteId=9828051&userId=7157574")||s.contains("/index/kf_html")){
                    getActivity().startActivity(new Intent(getActivity(), ServiceCustomerActivity.class));
                    return true;
                }
                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
             super.onPageStarted(webView, s, bitmap);
                ((MainActivity)getActivity()).mDialog.showDialog();
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                ((MainActivity)getActivity()).mDialog.dimissDialog();
                //关于左上角的返回 显示和隐藏
                if (mTBSWebView.canGoBack()){
                    toolbar.setNavigationIcon(R.mipmap.back);
                }else{
                    toolbar.setNavigationIcon(null);
                }
            }
        };
        mTBSWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
                if (message != null) {
                    //弹出对话框
                    ToastUtil.show(getActivity(), message, Toast.LENGTH_LONG);
                }
                jsResult.cancel();
                return true;
            }
        });
        mTBSWebView.setWebViewClient(client);
        mTBSWebView.loadUrl(Constant.URL_SERVICE);
        initToolBar();
    }

    private void initToolBar(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTBSWebView.canGoBack()){
                    mTBSWebView.goBack();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "底部服务菜单点击量");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "底部服务菜单点击量");
    }

    /**
     * 浏览器回退
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK ) {
            if (mTBSWebView.canGoBack()){
                mTBSWebView.goBack();
                toolbar.setNavigationIcon(R.mipmap.back);
                return true;
            }else{
                toolbar.setNavigationIcon(null);
                return false;
            }
        }
        return false;
    }

}
