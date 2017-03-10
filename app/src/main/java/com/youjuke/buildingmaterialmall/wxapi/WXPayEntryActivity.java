package com.youjuke.buildingmaterialmall.wxapi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.buy.TakeOrderActivity;
import com.youjuke.buildingmaterialmall.widgets.OneYuanBuyChanceDialog;
import com.youjuke.buildingmaterialmall.app.order.OrderDetailsActivity;
import com.youjuke.buildingmaterialmall.entity.ChanceStep;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.L;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 微信支付回调接收页面
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, View.OnClickListener {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    private Toolbar payToolBar;
    private TextView toolBarTextView;
    private ImageView imageView;
    private TextView tvPayTitle;
    private TextView tvPayMessage;
    private Button buttonConfirm;
    private TextView textToShoppingOrder;
    private int errCode = -1;
    private LinearLayout llScene;
    private String orderId = "";

    private OneYuanBuyChanceDialog oneYuanBuyChanceDialog;
    private ChanceStep chanceStep;

    private void assignViews() {
        payToolBar = (Toolbar) findViewById(R.id.pay_tool_bar);
        toolBarTextView = (TextView) findViewById(R.id.tool_bar_text_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        tvPayTitle = (TextView) findViewById(R.id.tv_pay_title);
        tvPayMessage = (TextView) findViewById(R.id.tv_pay_message);
        buttonConfirm = (Button) findViewById(R.id.button_confirm);
        textToShoppingOrder = (TextView) findViewById(R.id.text_to_shopping_order);
        llScene = (LinearLayout) findViewById(R.id.ll_scene);
        buttonConfirm.setOnClickListener(this);
        textToShoppingOrder.setOnClickListener(this);
    }


    /**
     * @param savedInstanceState
     */
    @Override
    public void initViews(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, Color.parseColor("#00C24F"), 0);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        assignViews();
        getChanceStep();
    }

    @Override
    public int getLayoutId() {
        return R.layout.pay_result;
    }

    @Override
    public void initToolBar() {
        payToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 查询一元换购用户购买情况，提示用户如何获得购买机会
     */
    private void getChanceStep() {
        params.clear();
        if (BuildingMaterialApp.user != null && BuildingMaterialApp.user.getId() != null) {
            params.put("userId", BuildingMaterialApp.user.getId());
        }
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.REDEMPTION_BUY_NODE, params).toJson())
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
                            chanceStep = gson.fromJson(responseBean.getData(), ChanceStep.class);
                            int chanceStepInt = -1;
                            switch (chanceStep.getStep()) {
                                case "1":
                                    chanceStepInt = OneYuanBuyChanceDialog.INVITATION;
                                    break;
                                case "2":
                                    chanceStepInt = OneYuanBuyChanceDialog.BUY;
                                    break;
                                case "3":
                                    chanceStepInt = OneYuanBuyChanceDialog.SHARE;
                                    break;
                            }
                            oneYuanBuyChanceDialog =
                                    new OneYuanBuyChanceDialog(
                                            WXPayEntryActivity.this, chanceStepInt,true);
                            oneYuanBuyChanceDialog.show();
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            out();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void out() {
        if (errCode == 0) {
            //结束生成订单页
            ActivityManager.getInstance().finishActivity(TakeOrderActivity.class);
            //结束当在订单详情页时的activity
            ActivityManager.getInstance().finishActivity(OrderDetailsActivity.class);
        }
        this.finish();
    }


    /**
     * 微信支付回调
     *
     * @param result
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("WxOrderId")
            })
    public void wxPayResult(String result) {
        L.d("WxOrderId = " + result);
        orderId = result;
    }

    @Override
    public void onResp(BaseResp resp) {
        L.d("onPayFinish, errCode = " + resp.errCode);
        errCode = resp.errCode;
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            handler.sendEmptyMessage(resp.errCode);
            RxBus.get().post("wxPay", "payByOk");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {//支付成功
                imageView.setImageResource(R.mipmap.btn_zfcg1);
                tvPayTitle.setText("支付成功");
                tvPayMessage.setVisibility(View.VISIBLE);
                buttonConfirm.setText("继续逛逛");
                textToShoppingOrder.setVisibility(View.VISIBLE);
            }
            llScene.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_confirm://去主页
                out();
                break;
            case R.id.text_to_shopping_order://查看订单
                Intent intent = new Intent(WXPayEntryActivity.this, OrderDetailsActivity.class);
                intent.putExtra("order_id", orderId);
                intent.putExtra("status_id", "2");//待发货
                startActivity(intent);
                out();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (oneYuanBuyChanceDialog != null && oneYuanBuyChanceDialog.isShowing()) {
            oneYuanBuyChanceDialog.dismiss();
        }
    }
}