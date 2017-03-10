package com.youjuke.buildingmaterialmall.app.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
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

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * 支付结果界面
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-15 20:25
 */

public class PaySucceedActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar payToolBar;
    private Button buttonGoHome;
    private TextView textToShoppingMall;
    private boolean goHome=true;
    private String orderId;
    private String order_type;
    private String type;//1为一元夺宝，默认是0

    private OneYuanBuyChanceDialog oneYuanBuyChanceDialog;
    private ChanceStep chanceStep;

    private void assignViews() {
        payToolBar = (Toolbar) findViewById(R.id.pay_tool_bar);
        buttonGoHome = (Button) findViewById(R.id.button_go_home);
        textToShoppingMall = (TextView) findViewById(R.id.text_to_shopping_order);
        buttonGoHome.setOnClickListener(this);
        textToShoppingMall.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        orderId=getIntent().getStringExtra("order_id");
        order_type = getIntent().getStringExtra("order_type");
        type = getIntent().getStringExtra("type");
        getChanceStep();
    }

    /**
     * 查询一元换购用户购买情况，提示用户如何获得购买机会
     */
    private void getChanceStep(){
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
                            switch (chanceStep.getStep()){
                                case "1" :
                                    chanceStepInt = OneYuanBuyChanceDialog.INVITATION;
                                    break;
                                case "2" :
                                    chanceStepInt = OneYuanBuyChanceDialog.BUY;
                                    break;
                                case "3" :
                                    chanceStepInt = OneYuanBuyChanceDialog.SHARE;
                                    break;

                            }
                            oneYuanBuyChanceDialog =
                                    new OneYuanBuyChanceDialog(
                                            PaySucceedActivity.this,chanceStepInt,true);
                            oneYuanBuyChanceDialog.show();
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_succeed;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            out();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void out() {
        this.finish();
        //结束生成订单页
        ActivityManager.getInstance().finishActivity(TakeOrderActivity.class);
        //结束当在订单详情页时的activity
        ActivityManager.getInstance().finishActivity(OrderDetailsActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_go_home://去主页
                out();
                break;
            case R.id.text_to_shopping_order://查看订单
                finish();
                //结束生成订单页
                ActivityManager.getInstance().finishActivity(TakeOrderActivity.class);
                //结束当在订单详情页时的activity
                ActivityManager.getInstance().finishActivity(OrderDetailsActivity.class);
                //跳转至订单待发货详情页
                Intent intent = new Intent(PaySucceedActivity.this, OrderDetailsActivity.class);
                intent.putExtra("order_id", orderId);
                intent.putExtra("status_id", "2");//待发货
                startActivity(intent);
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