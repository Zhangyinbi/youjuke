package com.youjuke.buildingmaterialmall.app.me;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.home.DecorationTrackingActivity;
import com.youjuke.buildingmaterialmall.app.message_center.MessageCenterActivity;
import com.youjuke.buildingmaterialmall.app.myasset.MyAssetActivity;
import com.youjuke.buildingmaterialmall.app.order.OrderActivity;
import com.youjuke.buildingmaterialmall.app.setting.SettingActivity;
import com.youjuke.buildingmaterialmall.entity.MyAssetInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.glide.CircleTransform;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述: 个人中心
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-12 10:12
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private TextView textViewMessageNum;
    private RelativeLayout relativeLayoutMessgaeNum;
    private ImageView imgHead;
    private TextView tv_user_name;
    private ImageView tv_setting;
    private ImageView imageView_message;
    private LinearLayout llManagedFunds;
    private TextView tvManagedFunds;//托管资金
    private LinearLayout llGetRebate;
    private TextView tvGetRebate;//领取返利
    private LinearLayout llWalletBalance;
    private TextView tvWalletBalance;//钱包余额
    private TextView tvMyDecorate;
    private TextView tvMyOrder;
    private TextView tvAccountSecurity;
    private Intent intent;
    ArrayList<MyAssetInfo.Contract> contractlist = new ArrayList<MyAssetInfo.Contract>();
    private String expect_start_time;
    private MyAssetInfo myAssetInfo;

    private void assignViews() {
        llManagedFunds = $(R.id.ll_managed_funds);
        tvManagedFunds = $(R.id.tv_managed_funds);
        llGetRebate = $(R.id.ll_get_rebate);
        tvGetRebate = $(R.id.tv_get_rebate);
        llWalletBalance = $(R.id.ll_wallet_balance);
        tvWalletBalance = $(R.id.tv_wallet_balance);
        tvMyDecorate = $(R.id.tv_my_decorate);
        tvMyOrder = $(R.id.tv_my_order);
        tvAccountSecurity = $(R.id.tv_account_security);
        imgHead = $(R.id.img_head);
        imageView_message = $(R.id.message_imageView);
        tv_user_name = $(R.id.tv_user_name);
        tv_setting = $(R.id.tv_setting);
        relativeLayoutMessgaeNum = $(R.id.relativeLayout_message_num);
        textViewMessageNum = $(R.id.textView_message_num);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
        llManagedFunds.setOnClickListener(this);
        llGetRebate.setOnClickListener(this);
        llWalletBalance.setOnClickListener(this);
        tvMyDecorate.setOnClickListener(this);
        tvMyOrder.setOnClickListener(this);
        tvAccountSecurity.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        imageView_message.setOnClickListener(this);
        textViewMessageNum.setOnClickListener(this);
        relativeLayoutMessgaeNum.setOnClickListener(this);
        //加载用户头像 用户名
        setHeadAndName();
        //获取消息数量
        getMessageCount();
        //资金数据信息
        getMoneyInfoFromNet();
    }

    /**
     * 获取我的资金数据信息
     */
    private void getMoneyInfoFromNet() {
        L.d("开始获取我的资产信息");
        params.clear();
        params.put(ApiContstants.BAOMING_ID, BuildingMaterialApp.user.getBaoming_id());
        params.put(ApiContstants.MOBIlE, BuildingMaterialApp.user.getMobile());
//        params.put(ApiContstants.BAOMING_ID,"5563");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.MY_ASSETS, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            myAssetInfo = gson.fromJson(responseBean.getData(), MyAssetInfo.class);
                            tvManagedFunds.setText(MoneySimpleFormat.getSimpleType(myAssetInfo.getBalance()));
                            tvGetRebate.setText(MoneySimpleFormat.getSimpleType(myAssetInfo.getFl_money()));
                            tvWalletBalance.setText(MoneySimpleFormat.getSimpleType(myAssetInfo.getWallet_balance()));
                            expect_start_time = myAssetInfo.getExpect_start_time();
                            contractlist = myAssetInfo.getContract_id();
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(mContext, responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);


    }

    private void setHeadAndName() {
        if (BuildingMaterialApp.user != null) {

            if (!TextUtils.isEmpty(BuildingMaterialApp.user.getAvatar().trim())) {
                Glide.with(activity)
                        .load(BuildingMaterialApp.user.getAvatar())
                        .transform(new CircleTransform(activity))
                        .into(imgHead);
            }
            imgHead.setOnClickListener(this);
            tv_user_name.setText(BuildingMaterialApp.user.getUsername());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_managed_funds://托管资金
                intent = new Intent(mContext, MyAssetActivity.class);
                intent.putExtra("title", 0);
                if (expect_start_time == null || expect_start_time.isEmpty()) {
                    intent.putExtra("flag", true);
                } else {
                    intent.putExtra("flag", false);
                }
                intent.putParcelableArrayListExtra("contractlist", contractlist);
                startActivity(intent);
                break;
            case R.id.ll_get_rebate://预计收益
                intent = new Intent(mContext, MyAssetActivity.class);
                intent.putParcelableArrayListExtra("contractlist", contractlist);
                if (expect_start_time == null || expect_start_time.isEmpty()) {
                    intent.putExtra("flag", true);
                } else {
                    intent.putExtra("flag", false);
                }
                intent.putExtra("title", 1);
                startActivity(intent);
                break;
            case R.id.ll_wallet_balance://钱包余额
                intent = new Intent(mContext, MyAssetActivity.class);
                intent.putExtra("title", 2);
                if (myAssetInfo!=null){
                    intent.putExtra("balance", myAssetInfo.getWallet_balance());
                }else {
                    intent.putExtra("balance", tvWalletBalance.getText().toString().trim());
                }
                startActivity(intent);
                break;
            case R.id.tv_my_decorate://我的装修
                intent = new Intent(mContext, DecorationTrackingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_my_order://我的订单
                toOrderList(0);
                break;
            case R.id.tv_account_security://账号与安全
            case R.id.tv_setting://设置
            case R.id.img_head:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //如果大于5.0添加一个头像过场动画 imgHead,"imgHead"
                    Pair<View, String> p = new Pair<View, String>(imgHead, "imgHead");//haderIv是头像控件
                    startActivity(new Intent(mContext, SettingActivity.class)
                            , ActivityOptions.makeSceneTransitionAnimation((Activity) mContext
                                    , p).toBundle());
                } else if (BuildingMaterialApp.user != null) {
                    startActivity(new Intent(mContext, SettingActivity.class));
                }

                break;
            case R.id.relativeLayout_message_num:
            case R.id.message_imageView://消息中心
                startActivity(new Intent(mContext, MessageCenterActivity.class));
                break;
            default:

                break;
        }
    }

    /**
     * 跳转到订单类表
     *
     * @param type
     */
    private void toOrderList(int type) {
        Intent intent = new Intent(activity, OrderActivity.class);
        intent.putExtra("status_id", type);
        startActivity(intent);
    }


    /**
     * 获取消息数量
     */
    private void getMessageCount() {
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        compositeSubscription.add(RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.MESSAGE_COUNT, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void call(ResponseBean responseBean) {
                        if (responseBean.getStatus().equals("200")) {
                            MessageNum num = gson.fromJson(responseBean.getData(), MessageNum.class);
                            if (num.getMessage_count() != null && num.getMessage_count().equals("0")) {
                                textViewMessageNum.setVisibility(View.GONE);
                            } else if (num.getMessage_count() != null && num.getMessage_count().length() > 0) {
                                textViewMessageNum.setVisibility(View.VISIBLE);
                                textViewMessageNum.setText(num.getMessage_count() + "");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }

    class MessageNum {
        String message_count;

        public String getMessage_count() {
            return message_count;
        }

        public void setMessage_count(String message_count) {
            this.message_count = message_count;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (BuildingMaterialApp.user != null) {
            setHeadAndName();
            //获取消息数量
            getMessageCount();
            getMoneyInfoFromNet();
        } else {
            RxBus.get().post("showTab", "loginOut");
        }
    }
}
