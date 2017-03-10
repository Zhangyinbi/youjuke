package com.youjuke.buildingmaterialmall.app.bargain_buy;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.buildingmaterialmall.entity.BargainBuy;
import com.youjuke.buildingmaterialmall.entity.BargainBuyDetailsProject;
import com.youjuke.buildingmaterialmall.entity.OrderInfo;
import com.youjuke.buildingmaterialmall.entity.ShareProjectInfo;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.utils.ShareUtils;
import com.youjuke.buildingmaterialmall.widgets.HintDialog;
import com.youjuke.buildingmaterialmall.widgets.OneYuanBuyChanceDialog;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.CRequest;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:一元换购商品详情
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-28 16:07
 */
public class BargainBuyDetailsActivity extends BaseActivity implements View.OnClickListener {

    private String id;
    private Toolbar toolBar;
    private String URL;
    private WebViewClient client;
    private LinearLayout llProjectDetailNav;
    private LinearLayout linearLayoutService;
    private LinearLayout linearLayoutPhone;
    private LinearLayout linearLayoutBuy;
    private TextView textNotStart;
    private TextView tv_share;
    private BargainBuyDetailsFragment detailsFragment;
    private BargainBuy bargainBuy;
    private List<BargainBuyDetailsProject> detailsProjectList = new ArrayList<>();
    private ShareProjectInfo shareProjectInfo;//分享商品信息的实体
    private HintDialog hintDialog;
    private AlertDialog.Builder builderRegister;
    private AlertDialog.Builder bargainBuyRemind;
    private AlertDialog.Builder completeShare;
    private AlertDialog.Builder bargainBuyOver;
    private AlertDialog builderRegisterDialog;
    private AlertDialog bargainBuyRemindDialog;
    private AlertDialog completeShareDialog;
    private AlertDialog bargainBuyOverDialog;
    private OrderInfo orderInfo;//返回生成的订单

    private OneYuanBuyChanceDialog oneYuanBuyChanceDialog;

    private void assignViews() {
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        llProjectDetailNav = (LinearLayout) findViewById(R.id.ll_project_detail_nav);
        linearLayoutService = (LinearLayout) findViewById(R.id.linearLayout_service);
        linearLayoutPhone = (LinearLayout) findViewById(R.id.linearLayout_phone);
        linearLayoutBuy = (LinearLayout) findViewById(R.id.linearLayout_buy);
        textNotStart = (TextView) findViewById(R.id.text_not_start);
        tv_share = (TextView) findViewById(R.id.tv_share);
        linearLayoutBuy.setClickable(true);
        linearLayoutBuy.setOnClickListener(this);
        linearLayoutService.setClickable(true);
        linearLayoutService.setOnClickListener(this);
        linearLayoutPhone.setClickable(true);
        linearLayoutPhone.setOnClickListener(this);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        URL = getIntent().getStringExtra("url");
        try{
            id = URL.substring(URL.indexOf("id=") + 3, URL.indexOf("&"));
        }catch (Exception e){
            e.printStackTrace();
            finish();
        }
        assignViews();
        client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                if (s.contains("/onsale/old_item?id=")) {
                    //解析出链接中的所有参数
                    Map<String, String> stringStringMap = CRequest.URLRequest(s);
                    String id = stringStringMap.get("id");
                    Intent intent = new Intent(BargainBuyDetailsActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
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
        mTBSWebView.loadUrl(URL + "&platform=app");
        //获取商品分享信息
        getShareInfo();
        //获取一元换购商品信息
        getBargainBuyCommodity(id);
    }

    /**
     * 获取一元换购商品信息
     *
     * @param CommodityId
     */
    private void getBargainBuyCommodity(String CommodityId) {
        params.clear();
        params.put("type", 2);
        params.put("id", CommodityId);
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.SECK_KILL_DATA, params).toJson())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(BargainBuyDetailsActivity.this, responseBean.getMessage());
                                } else if (responseBean.getStatus().equals("200")) {
                                    bargainBuy = gson.fromJson(responseBean.getData(), new TypeToken<BargainBuy>() {
                                    }.getType());
//                                    detailsFragment = new BargainBuyDetailsFragment(bargainBuy, BargainBuyDetailsActivity.this);
//                                    detailsFragment.show(getSupportFragmentManager(), "BargainBuyDetails");
                                    if (bargainBuy.getInventory().equals("0")) {
                                        textNotStart.setText("缺货登记");
                                        textNotStart.setBackgroundColor(Color.parseColor("#808080"));
                                    }
                                } else {
                                    ToastUtil.show(BargainBuyDetailsActivity.this, "请求失败,请稍后再试");
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(BargainBuyDetailsActivity.this, "请求错误,请稍后再试");
                            }
                        })
        );
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bargain_buy_details;
    }

    /**
     * 显示缺货登记dilog
     *
     * @param Classification
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("showBargainBuyRegister")
            })
    public void showBargainBuyRegister(final String Classification) {
        builderRegister = new AlertDialog.Builder(BargainBuyDetailsActivity.this, R.style.MyDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(BargainBuyDetailsActivity.this);
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.dialog_stockout_register, null);
        builderRegister.setView(layout);
        Button dialogBtnRegister = (Button) layout.findViewById(R.id.button_commit);
        final ClearEditText userName = (ClearEditText) layout.findViewById(R.id.text_user_name);
        final ClearEditText mobile = (ClearEditText) layout.findViewById(R.id.text_mobile);
        final ImageView imageViewClose = (ImageView) layout.findViewById(R.id.imageView_close);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builderRegisterDialog.dismiss();
            }
        });
        dialogBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登记
                if (userName.getText().length() <= 0) {
                    ToastUtil.show(BargainBuyDetailsActivity.this, "登记姓名不能为空");
                } else if (mobile.getText().length() <= 0) {
                    ToastUtil.show(BargainBuyDetailsActivity.this, "手机号不能为空");
                } else if (!Validator.isMobile(mobile.getText().toString())) {
                    ToastUtil.show(BargainBuyDetailsActivity.this, "手机号格式不正确");
                } else {
                    commitRegister(userName.getText().toString(), mobile.getText().toString(), Classification);
                }
            }
        });
        builderRegisterDialog = builderRegister.show();
    }

    /**
     * 提交缺货登记信息
     */
    private void commitRegister(String name, String mobile, String classification_id) {
        params.clear();
        params.put("username", name);
        params.put("mobile", mobile);
//        params.put("classification_id", classification_id);
        params.put("redemption_id", id);
        params.put("goods_id",bargainBuy.getGoods_id());
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.WANT_BOOK, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if (responseBean.getStatus().equals("200")) {
                                    //提交成功
                                    builderRegisterDialog.dismiss();
                                    ToastUtil.show(BargainBuyDetailsActivity.this, responseBean.getData() + "");
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(BargainBuyDetailsActivity.this, responseBean.getMessage() + "");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(BargainBuyDetailsActivity.this, "请求失败,请检查网络状态\n或者稍后在试");
                            }
                        })
        );

    }

    /**
     * 生成订单请求
     *
     * @param bargainBuyOrder
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("getBargainBuyOrder")
            })
    public void getBargainBuyOrder(BargainBuyOrder bargainBuyOrder) {

        detailsProjectList.clear();
        L.d("产品的ID" + bargainBuyOrder.classifications_id);
        detailsProjectList.add(new BargainBuyDetailsProject(0));//接口是这样写的。。。这个是不需要参数，但是还是必须要有。。。
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("type", bargainBuyOrder.type);//11 一元换购
        params.put("goods_info", detailsProjectList);
        params.put("redemption_id", bargainBuyOrder.redemption_id);
        params.put("classification", bargainBuyOrder.classifications_id);
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
                                L.e("getPopUpWindowOrderInfo返回数据 ：" + responseBean.toString());
                                if ("200".equals(responseBean.getStatus())) {
                                    orderInfo = gson.fromJson(responseBean.getData(), new TypeToken<OrderInfo>() {
                                    }.getType());
                                    Intent intent = new Intent(BargainBuyDetailsActivity.this, TakeOrderActivity.class);
                                    intent.putExtra("orderInfo", orderInfo);
                                    startActivity(intent);
                                } else if ("400".equals(responseBean.getStatus())) {
                                    //ToastUtil.show(BargainBuyDetailsActivity.this, responseBean.getMessage());
                                    //如果没有收货地址
                                    if ("4000".equals(responseBean.getError())) {
                                        Intent intent = new Intent(BargainBuyDetailsActivity.this,
                                                ReceiptAddressCompileActivity.class);
                                        ToastUtil.show(BargainBuyDetailsActivity.this, "请添加收货地址");
                                        intent.putExtra("siwtch", 1);
                                        startActivityForResult(intent, 1);
                                    }else if("save_order_4004".equals(responseBean.getError())){
                                        //默认次数已用完，可通过分享邀好友注册获得机会
                                        oneYuanBuyChanceDialog =
                                                new OneYuanBuyChanceDialog(BargainBuyDetailsActivity.this,
                                                        OneYuanBuyChanceDialog.INVITATION);
                                        oneYuanBuyChanceDialog.show();

                                    }else if("save_order_4008".equals(responseBean.getError())){
                                        //邀好友注册次数已用完，可通过购买虚拟商品获得机会！
                                        oneYuanBuyChanceDialog =
                                                new OneYuanBuyChanceDialog(BargainBuyDetailsActivity.this,
                                                        OneYuanBuyChanceDialog.BUY);
                                        oneYuanBuyChanceDialog.show();

                                    }else if ("save_order_4006".equals(responseBean.getError())) {
                                        //通过购买虚拟商品次数已用完，可通过分享报价器获得机会！
                                        oneYuanBuyChanceDialog =
                                                new OneYuanBuyChanceDialog(BargainBuyDetailsActivity.this,
                                                        OneYuanBuyChanceDialog.SHARE);
                                        oneYuanBuyChanceDialog.show();

                                    }else if (responseBean.getError().equals("save_order_4007")
                                            || responseBean.getError().equals("save_order_4009")) {
                                        //购买次数已全部用完！
                                        oneYuanBuyChanceDialog =
                                                new OneYuanBuyChanceDialog(BargainBuyDetailsActivity.this,
                                                        OneYuanBuyChanceDialog.OVER);
                                        oneYuanBuyChanceDialog.show();
                                    }else {
                                        ToastUtil.show(BargainBuyDetailsActivity.this, responseBean.getMessage());
                                    }
                                } else {
                                    ToastUtil.show(BargainBuyDetailsActivity.this, "请求失败");
                                }
                            }
                        })
        );
    }


    /**
     * 购买的三次机会都使用过了
     */
    private void showBargainBuyOver() {
        bargainBuyOver = new AlertDialog.Builder(BargainBuyDetailsActivity.this, R.style.MyDialogStyle);
        final LayoutInflater inflater = LayoutInflater.from(BargainBuyDetailsActivity.this);
        @SuppressLint("InflateParams")
        View layout = inflater.inflate(R.layout.dialog_bargain_buy_details_complete, null);
        bargainBuyOver.setView(layout);
        Button buttonBuy = (Button) layout.findViewById(R.id.button_commit);
        TextView textViewShoppingMall = (TextView) layout.findViewById(R.id.text_to_shopping_mall);
        ImageView imageViewClose = (ImageView) layout.findViewById(R.id.imageView_close);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭
                bargainBuyOverDialog.dismiss();
            }
        });
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接购买
                bargainBuyOverDialog.dismiss();
                Intent intent = new Intent(BargainBuyDetailsActivity.this, ProductDetailsActivity.class);
                intent.putExtra("id", bargainBuy.getGoods_id());
                startActivity(intent);
                finish();
            }
        });
        textViewShoppingMall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bargainBuyOverDialog.dismiss();
                finish();
                ActivityManager.getInstance().finishActivity(BargainBuyActivity.class);
            }
        });

        bargainBuyOverDialog = bargainBuyOver.show();

    }


    /**
     * 直接购买
     */
    @Subscribe(tags = {@Tag("BargainBuyDetailsActivity.goBuy")})
    public  void goBuy(String nothing){
        Intent intent = new Intent(BargainBuyDetailsActivity.this, ProductDetailsActivity.class);
        intent.putExtra("id", bargainBuy.getGoods_id());
        startActivity(intent);
    }


    /**
     * 返回的订单ID
     */
    class BargainBuyOrderID {
        /**
         * order_id : 6123707
         */
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }


    @Override
    public void initToolBar() {
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


    /**
     * 获取分享信息 右上角按钮
     * mwy 2016/10/26
     */
    private void getShareInfo() {
        params.clear();
        if (BuildingMaterialApp.user != null && BuildingMaterialApp.user.getId() != null) {
            params.put("uid", BuildingMaterialApp.user.getId());
        }
        params.put("gid", id);
        params.put("type", "2");//精选特卖商品：1 一元换购：2
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.GET_SHARE_DATA, params).toJson())
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
                        if ("200".equals(responseBean.getStatus())) {
                            tv_share.setVisibility(View.VISIBLE);
                            tv_share.setOnClickListener(BargainBuyDetailsActivity.this);
                            shareProjectInfo = gson.fromJson(responseBean.getData(), ShareProjectInfo.class);
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share://分享
                if (BuildingMaterialApp.user == null || BuildingMaterialApp.user.getId() == null) {
                    hintDialog = new HintDialog(BargainBuyDetailsActivity.this);
                    hintDialog.setTitleText(getString(R.string.share_hint_about_login))
                            .setDefultCancelListener()
                            .setConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    hintDialog.dismiss();
                                    ShareUtils.share(BargainBuyDetailsActivity.this,shareProjectInfo,null);
                                }
                            });
                    hintDialog.show();
                } else {
                    ShareUtils.share(BargainBuyDetailsActivity.this,shareProjectInfo,null);
                }
                break;
            case R.id.linearLayout_buy:// 购买显示Dialog
                if (bargainBuy == null) {
                    getBargainBuyCommodity(id);
                } else {
                    if (detailsFragment == null) {
                        detailsFragment = new BargainBuyDetailsFragment(bargainBuy, BargainBuyDetailsActivity.this);
                    }
                    detailsFragment.show(getSupportFragmentManager(), "BargainBuyDetails");
                }
                break;
            case R.id.linearLayout_service:
                Intent intent = new Intent(BargainBuyDetailsActivity.this, ServiceCustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.linearLayout_phone:
                new AlertDialog.Builder(BargainBuyDetailsActivity.this)
                        .setTitle("确认拨打客服电话吗?")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4006891616"));
                                BargainBuyDetailsActivity.this.startActivity(intent);
                            }
                        }).setPositiveButton("取消", null).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hintDialog != null && hintDialog.isShowing()) {
            hintDialog.dismiss();
        }
        if (oneYuanBuyChanceDialog != null && oneYuanBuyChanceDialog.isShowing()) {
            oneYuanBuyChanceDialog.dismiss();
        }
    }
}
