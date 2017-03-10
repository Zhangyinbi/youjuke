package com.youjuke.buildingmaterialmall.app.order;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.buy.PayWayActivity;
import com.youjuke.buildingmaterialmall.app.service_customer.ServiceCustomerActivity;
import com.youjuke.buildingmaterialmall.entity.OrderDetails;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.HintDialog;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.DateUtil;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;
import com.youjuke.swissgearlibrary.utils.TextHelper;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单详情activity   2016年9月19日  mwy
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar orderDetailToolbar;
    private EasyRecyclerView recyclerView;
    private RelativeLayout rlPayment;
    private TextView tvRealPay;
    private TextView tvToPay;
    private TextView tvTitleRealPay;
    private TextView tvCancelOrder;
    private HintDialog mHintDialog = null;
    private LinearLayout headType1;
    private TextView tvOrderStatus;
    private ImageView imgOrderStatus;
    private LinearLayout headType2;
    private TextView tvOrderStatusDfk;
    private TextView tvOrderStatusDfkTime;
    private ImageView imgOrderStatusDfk;
    private RelativeLayout rvUserInfo;
    private TextView tvUserName;
    private TextView tvUserPhone;
    private ImageView imgAddress;
    private TextView tvAddress;
    private TextView tvTitleTotalPrice;
    private TextView tvTotalPrice;
    private TextView tvTitleRedLuckyMoneyDeduction;
    private TextView tvRedLuckyMoneyDeduction;
    private TextView tvTitleRealPayment;
    private TextView tvRealPayment;
    private LinearLayout llContactCustomerService;
    private LinearLayout llCallPhone;
    private TextView tvTitleOrderNo;
    private TextView tvOrderNo;
    private TextView tvTitleOrderTime;
    private TextView tvOrderTime;
    private String order_id;
    private String status_id;
    private List<OrderDetails.GoodsBean> goodsList;
    private OrderDetails orderDetails;

    private OrderDetailsAdapter adapter;

    private CountDownTimer countDownTimer;
    private TextView tvPreparePrice;

    private void assignViews() {
        StatusBarUtil.setColor(this, Color.parseColor("#00c04f"), 0);
        orderDetailToolbar = (Toolbar) findViewById(R.id.order_detail_toolbar);
        recyclerView = (EasyRecyclerView) findViewById(R.id.rv_order_details);
        rlPayment = (RelativeLayout) findViewById(R.id.rl_payment);
        tvRealPay = (TextView) findViewById(R.id.tv_real_pay);
        tvToPay = (TextView) findViewById(R.id.tv_to_pay);
        tvTitleRealPay = (TextView) findViewById(R.id.tv_title_real_pay);
        tvCancelOrder = (TextView) findViewById(R.id.tv_cancel_order);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.color_323232));

            //底部导航栏
            //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
        }*/

        order_id = getIntent().getStringExtra("order_id");
        status_id = getIntent().getStringExtra("status_id");
        if ("1".equals(status_id.trim())) {//待付款
            rlPayment.setVisibility(View.VISIBLE);
            tvToPay.setOnClickListener(this);
        } else if ("3".equals(status_id.trim())) {
            rlPayment.setVisibility(View.VISIBLE);
            tvRealPay.setVisibility(View.INVISIBLE);
            tvTitleRealPay.setVisibility(View.INVISIBLE);
            tvToPay.setText("确认收货");
            tvToPay.setOnClickListener(this);
        } else {
            rlPayment.setVisibility(View.GONE);
        }
        goodsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderDetailsAdapter(this,order_id);
        adapter.addAll(goodsList);

        recyclerView.setAdapterWithProgress(adapter);
        getOrderDetail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initToolBar() {
        orderDetailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id + "");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.ORDER_DETAILS, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.i(e.getMessage());
                        ToastUtil.show(OrderDetailsActivity.this, "获取订单详情失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if (responseBean.getData() != null && !"null".equalsIgnoreCase(responseBean.getData())) {
                       L.i("返回数据:" + responseBean.toString());
                            if ("200".equals(responseBean.getStatus())) {
                                orderDetails = gson.fromJson(responseBean.getData(), new TypeToken<OrderDetails>() {
                                }.getType());
                                adapter.clear();
                                goodsList.clear();
                                goodsList.addAll(orderDetails.getGoods());
                                if (orderDetails != null) {
                                    //添加头部和尾部
                                    addHeadAndFoot();
                                    if ("1".equals(status_id.trim())) {//待付款
                                        tvCancelOrder.setVisibility(View.VISIBLE);
                                        tvCancelOrder.setOnClickListener(OrderDetailsActivity.this);
                                    }
                                }
                                adapter.setStatus_id(orderDetails.getStatus_id());
                                adapter.addAll(orderDetails.getGoods());
                            } else {
                                ToastUtil.show(OrderDetailsActivity.this, "获取订单详情失败");
                            }
                        } else {
                            ToastUtil.show(OrderDetailsActivity.this, "获取订单详情失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * 添加头部和尾部
     */
    private void addHeadAndFoot() {

        //添加头部
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                LinearLayout header = (LinearLayout) LayoutInflater.from(OrderDetailsActivity.this)
                        .inflate(R.layout.list_item_order_details_head, null);

                assignHeadViews(header);
                if ("1".equals(orderDetails.getStatus_id().trim())) {//待付款
                    headType2.setVisibility(View.VISIBLE);
                    headType1.setVisibility(View.GONE);
                    tvRealPay.setText(MoneySimpleFormat.getMoneyType(orderDetails.getAmount()));
                    //添加倒计时
                    countDownTimer = new CountDownTimer(orderDetails.getEx_time()*1000,1000) {
                        @Override
                        public void onTick(long l) {
                            orderDetails.setEx_time(orderDetails.getEx_time()-1);
                            String date = DateUtil.secondToDHMS(orderDetails.getEx_time());
                            tvOrderStatusDfkTime.setText("剩" + date + "自动关闭");
                        }

                        @Override
                        public void onFinish() {
                            finish();
                            ToastUtil.show(OrderDetailsActivity.this,"订单已关闭");
                        }
                    }.start();

                } else {
                    headType2.setVisibility(View.GONE);
                    headType1.setVisibility(View.VISIBLE);
                    tvOrderStatus.setText(orderDetails.getStatus());
                    setStatusImg(imgOrderStatus, orderDetails.getStatus_id());
                }
                tvUserName.setText(orderDetails.getConsignee());
                tvUserPhone.setText(orderDetails.getMobile());
                String address = orderDetails.getAccept_address().replace("&nbsp;", " ");
                tvAddress.setText(address);
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        //添加尾部
        adapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                LinearLayout foot = (LinearLayout) LayoutInflater.from(OrderDetailsActivity.this)
                        .inflate(R.layout.list_item_order_details_foot, null);
                assignFootViews(foot);
                tvTotalPrice.setText(MoneySimpleFormat.getMoneyType(orderDetails.getPrice()));
                if (orderDetails.getIs_pre()==1){
                    tvPreparePrice.setText("¥ 10.00");
                }
                TextHelper.setText(tvRedLuckyMoneyDeduction, MoneySimpleFormat.getMoneyType(orderDetails.getDiscount_price()));
                //抵用卷暂时为0
                tvRealPayment.setText(MoneySimpleFormat.getMoneyType(orderDetails.getAmount()));
                tvOrderNo.setText(orderDetails.getNo());
                tvOrderTime.setText(orderDetails.getCreatedate());
                llContactCustomerService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //联系客服
                        Intent intent = new Intent(OrderDetailsActivity.this, ServiceCustomerActivity.class);
                        startActivity(intent);
                    }
                });
                llCallPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:4006891616"));
                        startActivity(intent);
                    }
                });
                return foot;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    /**
     * 初始化头部view
     *
     * @param header
     */
    private void assignHeadViews(LinearLayout header) {
        headType1 = (LinearLayout) header.findViewById(R.id.head_type1);
        tvOrderStatus = (TextView) header.findViewById(R.id.tv_order_status);
        imgOrderStatus = (ImageView) header.findViewById(R.id.img_order_status);
        headType2 = (LinearLayout) header.findViewById(R.id.head_type2);
        tvOrderStatusDfk = (TextView) header.findViewById(R.id.tv_order_status_dfk);
        tvOrderStatusDfkTime = (TextView) header.findViewById(R.id.tv_order_status_dfk_time);
        imgOrderStatusDfk = (ImageView) header.findViewById(R.id.img_order_status_dfk);
        rvUserInfo = (RelativeLayout) header.findViewById(R.id.rv_user_info);
        tvUserName = (TextView) header.findViewById(R.id.tv_user_name);
        tvUserPhone = (TextView) header.findViewById(R.id.tv_user_phone);
        imgAddress = (ImageView) header.findViewById(R.id.img_address);
        tvAddress = (TextView) header.findViewById(R.id.tv_address);
        if (orderDetails.getIs_virtual_product()==1){
            rvUserInfo.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化尾部view
     *
     * @param foot
     */
    private void assignFootViews(LinearLayout foot) {
        tvTitleTotalPrice = (TextView) foot.findViewById(R.id.tv_title_total_price);
        tvTotalPrice = (TextView) foot.findViewById(R.id.tv_total_price);
        tvTitleRedLuckyMoneyDeduction = (TextView) foot.findViewById(R.id.tv_title_red_lucky_money_deduction);
        tvRedLuckyMoneyDeduction = (TextView) foot.findViewById(R.id.tv_red_lucky_money_deduction);
        tvTitleRealPayment = (TextView) foot.findViewById(R.id.tv_title_real_payment);
        tvRealPayment = (TextView) foot.findViewById(R.id.tv_real_payment);
        llContactCustomerService = (LinearLayout) foot.findViewById(R.id.ll_contact_customer_service);
        llCallPhone = (LinearLayout) foot.findViewById(R.id.ll_call_phone);
        tvTitleOrderNo = (TextView) foot.findViewById(R.id.tv_title_order_no);
        tvOrderNo = (TextView) foot.findViewById(R.id.tv_order_no);
        tvTitleOrderTime = (TextView) foot.findViewById(R.id.tv_title_order_time);
        tvOrderTime = (TextView) foot.findViewById(R.id.tv_order_time);
        tvPreparePrice = (TextView) foot.findViewById(R.id.tv_price);
    }

    /**
     * 设置状态图片
     *
     * @param imgOrderStatus
     */
    private void setStatusImg(ImageView imgOrderStatus, String statusId) {
        if ("2".equals(statusId.trim())) {
            imgOrderStatus.setImageResource(R.mipmap.btn_dfh);
        } else if ("3".equals(statusId.trim())) {
            imgOrderStatus.setImageResource(R.mipmap.btn_dsh);
        } else if ("4".equals(statusId.trim())) {
            imgOrderStatus.setImageResource(R.mipmap.btn_dpj);
        }else if("5".equals(statusId.trim())){
            imgOrderStatus.setImageResource(R.mipmap.btn_quxiao1);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_to_pay://立即支付 或 确认收货
                if (orderDetails == null) return;
                if ("1".equals(orderDetails.getStatus_id().trim())) {//立即支付
                    //百度统计点击量
                    StatService.onEvent(this,"立即支付","eventLabel",1);
                    Intent intent2 = new Intent(this,PayWayActivity.class);
                    intent2.putExtra("order_id",order_id);
                    //1为一元夺宝，默认是0
                    intent2.putExtra("type","0");
                    startActivity(intent2);
                } else if ("3".equals(orderDetails.getStatus_id().trim())) {//确认收货
                    //初始化提示框
                    if (mHintDialog == null) {
                        mHintDialog = new HintDialog(OrderDetailsActivity.this);
                    }
                    if (mHintDialog.isShowing()) {
                        mHintDialog.dismiss();
                    }
                    mHintDialog.setTitleText("确认收货?").setDefultCancelListener()
                            .setConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mHintDialog.dismiss();
                                    confirmReceipt();
                                }
                            }).show();
                }
                break;
            case R.id.tv_cancel_order://取消订单
                //初始化提示框
                if (mHintDialog == null) {
                    mHintDialog = new HintDialog(OrderDetailsActivity.this);
                }
                if (mHintDialog.isShowing()) {
                    mHintDialog.dismiss();
                }
                mHintDialog.setTitleText("确认取消这个订单吗").setDefultCancelListener()
                        .setConfirmListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mHintDialog.dismiss();
                                cancelOrder();
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    //确认收货
    private void confirmReceipt() {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id);
        params.put("status", "4");//待收货为3  收货传4
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.TRANSFER_ORDER, params).toJson())
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
                        ToastUtil.show(OrderDetailsActivity.this, "操作失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("确认收货返回数据：" + responseBean.getData());
                        if ("200".equals(responseBean.getStatus().trim())) {
                            ToastUtil.show(getApplicationContext(), "确认收货成功");
                            RxBus.get().post("reload","3");
                            finish();
                        } else {
                            ToastUtil.show(getApplicationContext(), "操作失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    //取消订单
    private void cancelOrder() {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id);
        params.put("status", "5");//取消订单
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.TRANSFER_ORDER, params).toJson())
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
                        ToastUtil.show(OrderDetailsActivity.this, "操作失败");
                    }
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("取消订单返回数据：" + responseBean.getData());
                        if ("200".equals(responseBean.getStatus().trim())) {
                            ToastUtil.show(getApplicationContext(), "取消订单成功");
                            finish();
                        } else {
                            ToastUtil.show(getApplicationContext(), "操作失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }


}
