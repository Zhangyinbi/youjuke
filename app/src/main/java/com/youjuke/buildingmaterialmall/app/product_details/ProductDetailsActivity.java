package com.youjuke.buildingmaterialmall.app.product_details;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.WebView;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.WrapActivity;
import com.youjuke.buildingmaterialmall.app.buy.TakeOrderActivity;
import com.youjuke.buildingmaterialmall.app.login.LoginActivity;
import com.youjuke.buildingmaterialmall.app.receipt_address_compile.ReceiptAddressCompileActivity;
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.buildingmaterialmall.entity.OrderInfo;
import com.youjuke.buildingmaterialmall.entity.PickProjects;
import com.youjuke.buildingmaterialmall.entity.ProjectInfo;
import com.youjuke.buildingmaterialmall.entity.ShareProjectInfo;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.utils.ShareUtils;
import com.youjuke.buildingmaterialmall.widgets.AmountView;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.FlowLayout;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagAdapter;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagFlowLayout;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;
import com.youjuke.swissgearlibrary.utils.TextHelper;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.youjuke.buildingmaterialmall.R.id.img_project_icon;
import static com.youjuke.buildingmaterialmall.R.id.tv_btn_sure;

/**
 * 描述:
 * 精选特卖 产品详情
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-14 17:56
 * #0001    mwy          2016-09-22  主要逻辑编写
 */
public class ProductDetailsActivity extends WrapActivity implements View.OnClickListener {

    //private WebViewClient client;
    private LinearLayout ll_project_detail_nav;
    private LinearLayout ll_service;
    private LinearLayout ll_phone;
    private TextView tv_buy_now;
    private TextView tv_share;
    private ProductDetailsWebViewClient client;
    private BottomSheetDialog dialog;
    private String id;

    private ProjectInfo projectInfo;
    private Toolbar project_detail_toolbar;
    private OrderInfo orderInfo;//返回生成的订单

    //----dialogView
    private RelativeLayout mRlPlane;
    private TextView mTvProjectPrice;
    private TextView mTvProjectSurplus;
    private ImageView mImgClose;
    private TextView mTvTitleYjkYiBuTie;
    private View mLineProjectInfo;
    private TextView mTvTitleProjectType;
    private TagFlowLayout mTlProjectType;
    private TextView mTextView;
    private AmountView mAmountView;
    private TextView mTvBtnSure;
    private ImageView mImgProjectIcon;
    private AlertDialog builderRegisterDialog;
    private AlertDialog.Builder builderRegister;

    private int currentTagIndex = 0;//dialog中 分类 当前选中index下标
    private int classifications_id = -1;//购买商品的择分类id
    private int projectCount = 1;//购买商品的数量
    private int goods_storage = 0;//产品库存
    private List<PickProjects> projectsList = new ArrayList<PickProjects>();//购买商品的集合
    private List<OrderInfo.GoodItemsBean> goodsList = new ArrayList<>();//返回的生成订单商品集合
    private ShareProjectInfo shareProjectInfo;//分享商品信息的实体
    private int goods_status = -1;

    private void assignViews() {
        mTBSWebView = (WebView) findViewById(R.id.TBS_web_view);
        ll_project_detail_nav = (LinearLayout) findViewById(R.id.ll_project_detail_nav);
        ll_service = (LinearLayout) findViewById(R.id.ll_service);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        tv_buy_now = (TextView) findViewById(R.id.tv_buy_now);
        tv_share = (TextView) findViewById(R.id.tv_share);
        project_detail_toolbar = (Toolbar) findViewById(R.id.project_detail_toolbar);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        id = getIntent().getStringExtra("id");

        //client=new WebViewClient(){};
        client = new ProductDetailsWebViewClient(this);

        mTBSWebView.setWebViewClient(client);
        goods_status = getIntent().getIntExtra("goods_status", -1);
        //商品状态（1-上架 2-下架）
        if (goods_status == 2) {
            mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_NEW_PRODUCTDETAILS + "&id=" + id);
        } else {
            mTBSWebView.loadUrl(BuildingMaterialApp.YOU_JU_KE_PRODUCTDETAILS + "&id=" + id);
        }

        ll_service.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        tv_buy_now.setOnClickListener(this);
        //获取该商品的分享信息
        getShareInfo();
        //获取商品Dilog显示信息
        getPopUpWindowOrderInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initToolBar() {

        project_detail_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_service://联系客服
                Intent intent = new Intent(ProductDetailsActivity.this, ServiceCustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_phone://电话
                new AlertDialog.Builder(ProductDetailsActivity.this)
                        .setTitle("确认拨打客服电话吗?")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4006891616"));
                                ProductDetailsActivity.this.startActivity(intent);
                            }
                        }).setPositiveButton("取消", null).show();
                break;
            case R.id.tv_buy_now://立即购买
                //百度统计点击量
                StatService.onEvent(this, "立即抢购", "eventLabel", 1);
                // getPopUpWindowOrderInfo();
                if (projectInfo == null) {
                    getPopUpWindowOrderInfo();
                } else {
                    dialog.show();
                }

                break;
            case R.id.tv_share://分享
                //百度统计点击量
                StatService.onEvent(this, "商品详情界面分享", "eventLabel", 1);
                ShareUtils.share(this, shareProjectInfo, null);
                break;
            default:
                break;
        }
    }

    /**
     * @param Classification
     */
    private void showBargainBuyRegister(final String Classification) {
        L.d("显示缺货登记");
        builderRegister = new AlertDialog.Builder(ProductDetailsActivity.this, R.style.MyDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(ProductDetailsActivity.this);
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
                    ToastUtil.show(ProductDetailsActivity.this, "登记姓名不能为空");
                } else if (mobile.getText().length() <= 0) {
                    ToastUtil.show(ProductDetailsActivity.this, "手机号不能为空");
                } else if (!Validator.isMobile(mobile.getText().toString())) {
                    ToastUtil.show(ProductDetailsActivity.this, "手机号格式不正确");
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
        params.put("classification_id", classification_id);
//        params.put("redemption_id", id);
        params.put("goods_id", projectInfo.getGoods_id());
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
                                    ToastUtil.show(ProductDetailsActivity.this, responseBean.getData() + "");
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ProductDetailsActivity.this, responseBean.getMessage() + "");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(ProductDetailsActivity.this, "请求失败,请检查网络状态\n或者稍后在试");
                            }
                        })
        );

    }//commitRegister for end

    /**
     * 初始化dialog
     */
    private void initDialog() {
        if (dialog == null) {
            dialog = new BottomSheetDialog(ProductDetailsActivity.this, R.style.MyBottomSheetStyle);
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        final View dialogView = LayoutInflater.from(ProductDetailsActivity.this)
                .inflate(R.layout.bottom_sheet_dialog_buy_project_info, null);
        dialog.setContentView(dialogView);
        //初始化dialogView
        assigndialogView();
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Glide.with(ProductDetailsActivity.this)
                .load(ApiContstants.IMG_URL + projectInfo.getGoods_image())
                .into(mImgProjectIcon);
        TextHelper.setText(mTvProjectPrice,
                MoneySimpleFormat.getMoneyType(projectInfo.getClassifications().get(0).getSale_price()));
        SpannableStringBuilder builder = new SpannableStringBuilder("剩余" + projectInfo.getClassifications().get(0).getNum() + "件");
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvProjectSurplus.setText(builder);

        TagAdapter<ProjectInfo.ClassificationsBean> tagAdapter =
                new TagAdapter<ProjectInfo.ClassificationsBean>(projectInfo.getClassifications()) {

                    @Override
                    public View getView(FlowLayout parent, int position, ProjectInfo.ClassificationsBean classificationsBean) {
                        TextView tv = (TextView) LayoutInflater.from(ProductDetailsActivity.this)
                                .inflate(R.layout.flow_layout_item_project_type,
                                        parent, false);
                        tv.setText(classificationsBean.getName());
                        return tv;
                    }
                };
        //默认选中第一个
        tagAdapter.setSelectedList(0);
        classifications_id = Integer.parseInt(projectInfo.getClassifications()
                .get(0).getClassifications_id());
        mTlProjectType.setOnTagClickListener(new MyOnTagClickListener());
        mTlProjectType.setAdapter(tagAdapter);

        //初始化加减按钮
        mAmountView.setGoods_storage(Integer.parseInt(projectInfo.getClassifications().get(0).getNum()));
        //设置默认选择的第一个的分类的按钮
        goods_storage = Integer.parseInt(projectInfo.getClassifications().get(0).getNum());
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                //ToastUtil.show(ProductDetailsActivity.this,"Amount=>  " + amount);
                String sale_price = projectInfo.getClassifications().get(currentTagIndex).getSale_price();
                float price = Float.parseFloat(sale_price);
                //取前面两位小数
                price = (float) (Math.round(price * amount * 100)) / 100;
                TextHelper.setText(mTvProjectPrice,
                        MoneySimpleFormat.getMoneyType(price));
                projectCount = amount;
            }
        });
        //确认按钮er
        mTvBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //没有选择任何分
                if (currentTagIndex == -1) {
                    ToastUtil.show(ProductDetailsActivity.this, "您还没选择分类");
                } else {
                    if (dialog.isShowing()) dialog.dismiss();
                    if (BuildingMaterialApp.user == null) {
                        startActivity(new Intent(ProductDetailsActivity.this, LoginActivity.class));
                    } else if (projectInfo.getInventory().equals("0")) {
                        // 缺货登记
                        showBargainBuyRegister(projectInfo.getClassifications().get(0).getClassifications_id());
                    } else {
                        //生成订单
                        projectsList.clear();
                        projectsList.add(new PickProjects(classifications_id + "", projectCount + ""));
                        saveOrderInfo();
                    }
                }
            }
        });
        if (goods_storage <= 0) {
            if (goods_storage <= 0) {
                mTvBtnSure.setText("库存不足");
                mTvBtnSure.setClickable(true);
            } else {
                StatService.onEvent(ProductDetailsActivity.this, "换购商品详情页确认按钮点击量", "pass", 1);
                mTvBtnSure.setText("确认");
                mTvBtnSure.setClickable(true);
            }
        }

        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 获取购买时弹窗商品信息
     */
    public void getPopUpWindowOrderInfo() {
        params.clear();
        params.put("type", "1");//0:表示当前秒杀商品 1非当前秒杀商品
        params.put("id", id);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.SECK_KILL_DATA, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        L.i("当前doOnSubscribe所在线程：", Thread.currentThread().getName());
                        showProgressDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        //初始化dialog
                        if (projectInfo == null) return;
                        initDialog();
                        //dialog.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mCustomProgressDialog.isShowing()) mCustomProgressDialog.dismiss();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("当前onNext所在线程：", Thread.currentThread().getName());
                        if ("200".equals(responseBean.getStatus())) {
                            projectInfo = gson.fromJson(responseBean.getData(), ProjectInfo.class);
                            //缺货登记 tx 2016-11-10
                            if (projectInfo.getInventory().equals("0")) {
                                tv_buy_now.setText("缺货登记");
                                tv_buy_now.setBackgroundColor(Color.parseColor("#808080"));
                            }
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(ProductDetailsActivity.this, responseBean.getMessage());

                        } else {
                            ToastUtil.show(ProductDetailsActivity.this, "请求失败");
                        }
                        mCustomProgressDialog.dismiss();
                    }
                });
        compositeSubscription.add(subscribe);
    }


    private void assigndialogView() {
        mRlPlane = (RelativeLayout) dialog.findViewById(R.id.rl_plane);
        mTvProjectPrice = (TextView) dialog.findViewById(R.id.tv_project_price);
        mTvProjectSurplus = (TextView) dialog.findViewById(R.id.tv_project_surplus);
        mImgClose = (ImageView) dialog.findViewById(R.id.img_close);
        mTvTitleYjkYiBuTie = (TextView) dialog.findViewById(R.id.tv_title_yjk_yi_bu_tie);
        mTvTitleProjectType = (TextView) dialog.findViewById(R.id.tv_title_project_type);
        mTlProjectType = (TagFlowLayout) dialog.findViewById(R.id.tl_project_type);
        mTextView = (TextView) dialog.findViewById(R.id.textView);
        mAmountView = (AmountView) dialog.findViewById(R.id.amount_view);
        mTvBtnSure = (TextView) dialog.findViewById(tv_btn_sure);
        mImgProjectIcon = (ImageView) dialog.findViewById(img_project_icon);
    }


    /**
     * flowLayout tag点击事件
     */
    class MyOnTagClickListener implements TagFlowLayout.OnTagClickListener {

        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            //如果是点击的是已选择的tag 也就是取消选择
            if (currentTagIndex == position) {
                currentTagIndex = -1;
            } else {
                goods_storage = Integer.parseInt(projectInfo.getClassifications().get(position).getNum());
                if (goods_storage <= 0) {
                    mTvBtnSure.setText("库存不足");
                    mTvBtnSure.setClickable(false);
                } else {
                    mTvBtnSure.setText("确认");
                    mTvBtnSure.setClickable(true);
                }
                currentTagIndex = position;
                TextHelper.setText(mTvProjectPrice,
                        MoneySimpleFormat.getMoneyType(projectInfo.getClassifications().get(position).getSale_price()));
                SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                        + projectInfo.getClassifications().get(position).getNum() + "件");
                builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                        2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mTvProjectSurplus.setText(builder);
                //重置产品数量
                mAmountView.setGoods_storage(Integer.parseInt(projectInfo.getClassifications().get(position).getNum()));
                mAmountView.setAmount(1);
                classifications_id = Integer.parseInt(projectInfo.getClassifications().get(position).getClassifications_id());
                projectCount = 0;
            }

            return true;
        }
    }

    //生成订单
    private void saveOrderInfo() {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("goods_info", projectsList);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
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
                        ToastUtil.show(ProductDetailsActivity.this, "生成订单失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.e("getPopUpWindowOrderInfo返回数据 ：" + responseBean.toString());
                        if ("200".equals(responseBean.getStatus())) {
                            orderInfo = gson.fromJson(responseBean.getData(), new TypeToken<OrderInfo>() {
                            }.getType());
                            goodsList.clear();
                            goodsList.addAll(orderInfo.getGood_items());
                            Intent intent = new Intent(ProductDetailsActivity.this, TakeOrderActivity.class);
                            intent.putExtra("orderInfo", orderInfo);
                            startActivity(intent);
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(ProductDetailsActivity.this, responseBean.getMessage());
                            //如果没有收货地址
                            if ("4000".equals(responseBean.getError())) {
                                Intent intent = new Intent(ProductDetailsActivity.this, ReceiptAddressCompileActivity.class);
                                intent.putExtra("siwtch", 2);
                                intent.putExtra("classifications_id", classifications_id);
                                intent.putExtra("projectCount", projectCount);
                                startActivity(intent);
                            }
                        } else {
                            ToastUtil.show(ProductDetailsActivity.this, "请求失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Subscribe(tags = @Tag("goToOtherProduct"))
    public void goToOtherProduct(String id) {
        Intent intent = new Intent(ProductDetailsActivity.this, ProductDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    /**
     * 获取分享信息
     */
    private void getShareInfo() {
        params.clear();
        if (BuildingMaterialApp.user != null && BuildingMaterialApp.user.getId() != null) {
            params.put("uid", BuildingMaterialApp.user.getId());
        }
        params.put("gid", id);
        params.put("type", "1");//精选特卖商品：1 一元换购：2
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
                        L.e("getShareInfo返回数据 ：" + responseBean.getData());
                        if ("200".equals(responseBean.getStatus())) {
                            tv_share.setVisibility(View.VISIBLE);
                            tv_share.setOnClickListener(ProductDetailsActivity.this);
                            shareProjectInfo = gson.fromJson(responseBean.getData(), ShareProjectInfo.class);

                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    //重写百度统计的两个方法，测试商品详情页面的浏览量
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(this, "一元换购商品详情页浏览量");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "一元换购商品详情页浏览量");
    }
}
