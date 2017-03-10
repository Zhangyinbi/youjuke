package com.youjuke.buildingmaterialmall.app.seckill;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.buy.TakeOrderActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.receipt_address_compile.ReceiptAddressCompileActivity;
import com.youjuke.buildingmaterialmall.app.service_assurance.ServiceAssuranceActivity;
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.buildingmaterialmall.entity.OrderInfo;
import com.youjuke.buildingmaterialmall.entity.SeckillDetails;
import com.youjuke.buildingmaterialmall.entity.SeckillProjects;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:秒杀详情
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 14:57
 */
public class SeckillDetailsActivity extends BaseActivity implements View.OnClickListener {

    private WebViewClient client;
    private LinearLayout lLservice;
    private Toolbar seckillDetailstoolBar;
    private LinearLayout llProjectDetailNav;
    private LinearLayout llService;
    private LinearLayout llFollow;
    private TextView textNotStart;
    private TextView textPurchaseInAdvance;
    private TextView textPaidInFull;
    private Handler handler;
    private SeckillDetails seckillDetails=null;
    private PayDetailFragment payDetailFragment;
    private List<SeckillProjects> seckillProjectsesList = new ArrayList<>();//购买爆款的商品
    private String url;

    private void assignViews() {

        lLservice = (LinearLayout) findViewById(R.id.ll_service);
        seckillDetailstoolBar = (Toolbar) findViewById(R.id.seckill_detailstool_bar);
        llProjectDetailNav = (LinearLayout) findViewById(R.id.ll_project_detail_nav);
        llFollow = (LinearLayout) findViewById(R.id.ll_follow);
        textNotStart = (TextView) findViewById(R.id.text_not_start);
        textPurchaseInAdvance = (TextView) findViewById(R.id.text_purchase_in_advance);
        textPaidInFull = (TextView) findViewById(R.id.text_paid_in_full);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);

        lLservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeckillDetailsActivity.this, ServiceCustomerActivity.class);
                startActivity(intent);

            }
        });

        llFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SeckillDetailsActivity.this)
                        .setTitle("确认拨打客服电话吗?")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4006891616"));
                                SeckillDetailsActivity.this.startActivity(intent);

                            }
                        }).setPositiveButton("取消", null).show();
            }
        });

        textPaidInFull.setOnClickListener(this);
        textPurchaseInAdvance.setOnClickListener(this);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {

        assignViews();

        url=getIntent().getStringExtra("url");
        mDialog.showDialog();
        client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                mDialog.dimissDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                if (s.contains("/onsale/old_item?id=")){
                    //解析出链接中的所有参数
                    Map<String, String> stringStringMap = CRequest.URLRequest(s);
                    String id = stringStringMap.get("id");
                    L.i("拦截商品详情  id = "+id);
                    L.i("拦截商品详情  url = "+s);
                    Intent intent = new Intent(SeckillDetailsActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();
                    return true;
                }

                if (s.contains("/onsale/fuwu")){

                    startActivity(new Intent(SeckillDetailsActivity.this, ServiceAssuranceActivity.class));
                    return true;
                }
                if (s.contains("onsale/putsale")){
                    webView.loadUrl(s+"?platform=app");
                    return true;
                }

                //防止加载系统的webView
                webView.loadUrl(s);
                return true;
            }
        };

        mTBSWebView.setWebViewClient(client);
        mTBSWebView.addJavascriptInterface(this, "tags");

        mTBSWebView.loadUrl(url);
        //底部状态栏
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.arg1 != 0) {
                    textNotStart.setVisibility(View.VISIBLE);
                    textPurchaseInAdvance.setVisibility(View.GONE);
                    textPaidInFull.setVisibility(View.GONE);
                } else {
                    textNotStart.setVisibility(View.GONE);
                    textPurchaseInAdvance.setVisibility(View.VISIBLE);
                    textPaidInFull.setVisibility(View.VISIBLE);
                }
            }
        };

    }

    /**
     * js交互。为js提供接口
     *
     * @param num
     */
    @JavascriptInterface
    public void showIndex(int num) {
        L.d("index" + num);
        Message message = handler.obtainMessage();
        message.arg1 = num;
        handler.sendMessage(message);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seckill_details;
    }

    @Override
    public void initToolBar() {

        setSupportActionBar(seckillDetailstoolBar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        seckillDetailstoolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_purchase_in_advance:

                    if (seckillDetails==null) {
                        mDialog.showDialog();
                        getSeckillDetails(false);
                    }
                else {
                        payDetailFragment= new PayDetailFragment(SeckillDetailsActivity.this, seckillDetails, false);
                        payDetailFragment.show(getSupportFragmentManager(), "payDetailFragment");
                    }
                break;
            case R.id.text_paid_in_full:

                if (seckillDetails==null) {
                    mDialog.dimissDialog();
                    getSeckillDetails(true);
                } else {

                    payDetailFragment= new PayDetailFragment(SeckillDetailsActivity.this, seckillDetails, true);
                    payDetailFragment.show(getSupportFragmentManager(), "payDetailFragment");
                }

                break;
        }
    }


    private OrderInfo orderInfo;//返回生成的订单
    /**
     * 生成订单
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("getSeckillOrder")
            } )
    public  void  getOrderInfos(PayDetailFragment.mOrderInfo Info){
        seckillProjectsesList.clear();

        seckillProjectsesList.add(new SeckillProjects(Info.classifications_id + ""));
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("goods_info",seckillProjectsesList);
            if (!Info.seckill_in_full){
                //type 除了10元预抢的填5之外，其它的都不提交
                params.put("type","5");
            }

        compositeSubscription.add(
        RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.SAVE_ORDER, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }
                    @Override
                    public void onNext(ResponseBean responseBean) {

                        L.e("getPopUpWindowOrderInfo返回数据 ："+responseBean.toString());
                        if ("200".equals(responseBean.getStatus())){
                            orderInfo = gson.fromJson(responseBean.getData(),new TypeToken<OrderInfo>(){}.getType());
                            Intent intent=new Intent(SeckillDetailsActivity.this,TakeOrderActivity.class);
                            intent.putExtra("orderInfo",orderInfo);
                            startActivity(intent);

                        }else if("400".equals(responseBean.getStatus())){
                            ToastUtil.show(SeckillDetailsActivity.this,responseBean.getMessage());
                            //如果没有收货地址
                            if ("4000".equals(responseBean.getError())){
                                Intent intent = new Intent(SeckillDetailsActivity.this, ReceiptAddressCompileActivity.class);
                                intent.putExtra("siwtch",1);
                                startActivityForResult(intent,1);
                            }
                        }else{
                            ToastUtil.show(SeckillDetailsActivity.this,"请求失败");
                        }
                    }
                })
        );
    }






    public void getSeckillDetails(final Boolean aBoolean) {
        params.clear();
        params.put("type", "0");

        compositeSubscription.add(

                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.SECK_KILL_DATA, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                mDialog.dimissDialog();
                                if(responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(SeckillDetailsActivity.this,responseBean.getMessage());
                                }
                                else if(responseBean.getStatus().equals("200")) {
                                    seckillDetails = gson.fromJson(responseBean.getData()
                                            , new TypeToken<SeckillDetails>() {
                                            }.getType());

                                    payDetailFragment= new PayDetailFragment(SeckillDetailsActivity.this, seckillDetails, aBoolean);
                                    payDetailFragment .show(getSupportFragmentManager(), "payDetailFragment");

                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();

                            }
                        })

        );

    }

}



