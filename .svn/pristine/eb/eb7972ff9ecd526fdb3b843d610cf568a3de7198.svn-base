package com.youjuke.buildingmaterialmall.app.buy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.PayOrderInfo;
import com.youjuke.buildingmaterialmall.entity.PayResult;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.wxapi.WxPayEntity;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述: 选择支付方式的activity
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-23 16:38
 */

public class PayWayActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mPayWayToolbar;
    private View mTakeOrderRules;
    private LinearLayout mLlWeiXinPay;
    private ImageView mImgRadioWx;
    private LinearLayout mLlAliPay;
    private ImageView mImgRadioZfb;
    private Button mBtnSureToPay;
    private String order_id;
    private String order_type;
    private String type;//1为一元夺宝，默认是0
    private IWXAPI iwxapi;
    private int payWay = 0;
    private static final int SDK_PAY_FLAG = 1;

    private void assignViews() {
        mPayWayToolbar = (Toolbar) findViewById(R.id.pay_way_toolbar);
        mTakeOrderRules = findViewById(R.id.take_order_rules);
        mLlWeiXinPay = (LinearLayout) findViewById(R.id.ll_wei_xin_pay);
        mImgRadioWx = (ImageView) findViewById(R.id.img_radio_wx);
        mLlAliPay = (LinearLayout) findViewById(R.id.ll_ali_pay);
        mImgRadioZfb = (ImageView) findViewById(R.id.img_radio_zfb);
        mBtnSureToPay = (Button) findViewById(R.id.btn_sure_to_pay);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        mLlWeiXinPay.setOnClickListener(this);
        mLlAliPay.setOnClickListener(this);
        mBtnSureToPay.setOnClickListener(this);
        order_id = getIntent().getStringExtra("order_id");
        order_type = getIntent().getStringExtra("order_type");
        type = getIntent().getStringExtra("type");
        iwxapi = WXAPIFactory.createWXAPI(PayWayActivity.this, null);
        iwxapi.registerApp(BuildingMaterialApp.WX_PAY_API_KEY);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_way;
    }

    @Override
    public void initToolBar() {
        mPayWayToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_wei_xin_pay:
                mImgRadioWx.setImageResource(R.mipmap.btn_xz);
                mImgRadioZfb.setImageResource(R.mipmap.btn_wxz);
                payWay = 0;
                break;
            case R.id.ll_ali_pay:
                mImgRadioWx.setImageResource(R.mipmap.btn_wxz);
                mImgRadioZfb.setImageResource(R.mipmap.btn_xz);
                payWay = 1;
                break;
            case R.id.btn_sure_to_pay:
                if (payWay == 1) {
                    aliPay();
                } else if (payWay == 0) {
                    if (!iwxapi.isWXAppInstalled()) {
                        ToastUtil.show(this, "没有安装微信");
                    } else if (!iwxapi.isWXAppSupportAPI()) {
                        ToastUtil.show(this, "当前版本不支持支付功能");
                    } else {
                        wxPay();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 微信支付
     * AppID：wxde06caa10050b707
     * 应用签名：248f1e86353c1d1ac653dc33614ddba8
     * 包名：com.youjuke.buildingmaterialmall
     * key=w8ZZhWQfdpJRoMuMqKsupxtWVTSB4Zxs
     */
    private void wxPay() {
        ToastUtil.show(this, "正在跳转微信支付");
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id);
        params.put("type", type);
        compositeSubscription.add(RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.APP_WXPAY_PARAMS, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {

                            L.d("微信支付请求订单", "" + responseBean.getData());
                            WxPayEntity wxPay = gson.fromJson(responseBean.getData(), WxPayEntity.class);
                            PayReq payReq = new PayReq();
                            payReq.appId = wxPay.getPay_info().getAppid();
                            L.d("appId=" + wxPay.getPay_info().getAppid());
                            payReq.partnerId = wxPay.getPay_info().getPartnerid();
                            L.d("partnerId=" + wxPay.getPay_info().getPartnerid());
                            payReq.prepayId = wxPay.getPay_info().getPrepayid();
                            L.d("prepayId=" + wxPay.getPay_info().getPrepayid());
                            payReq.nonceStr = wxPay.getPay_info().getNoncestr();
                            L.d("nonceStr=" + wxPay.getPay_info().getNoncestr());
                            payReq.timeStamp = wxPay.getPay_info().getTimestamp();
                            L.d("timeStamp=" + wxPay.getPay_info().getTimestamp());
                            payReq.packageValue = wxPay.getPay_info().getPackageX();
                            L.d("packageValue=" + wxPay.getPay_info().getPackageX());
                            payReq.sign = wxPay.getPay_info().getSign();
                           // payReq.sign=appSign(wxPay);
                            L.d("sign=" + wxPay.getPay_info().getSign());
                           // payReq.extData=wxPay.getPay_info().getTrade_type();
                            Boolean b = iwxapi.sendReq(payReq);
                            L.d("微信开始支付请求" + b);
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(PayWayActivity.this, "" + responseBean.getMessage(), 5000);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.show(PayWayActivity.this, "请求失败", 5000);
                    }
                })
        );

    }
//    /**
//     * 微信支付签名算法sign
//     *
//     * @param parameters
//     * @return
//     */
//    public static String createSign(SortedMap<Object, Object> parameters) {
//
//        StringBuffer sb = new StringBuffer();
//        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
//        Iterator it = es.iterator();
//        while (it.hasNext()) {
//            @SuppressWarnings("rawtypes")
//            Map.Entry entry = (Map.Entry) it.next();
//            String k = (String) entry.getKey();
//            Object v = entry.getValue();
//            if (null != v && !"".equals(v) && !"sign".equals(k)
//                    && !"key".equals(k)) {
//                sb.append(k + "=" + v + "&");
//            }
//        }
//        sb.append("key="); //KEY是商户秘钥
//        String sign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        return sign;
//
//    }
//
//
//    private String appSign(WxPayEntity payEntity){
//        // 把参数的值传进去SortedMap集合里面
//        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
//        parameters.put("appid", payEntity.getPay_info().getAppid());
//        parameters.put("noncestr", payEntity.getPay_info().getNoncestr());
//        parameters.put("package", payEntity.getPay_info().getPackageX());
//        parameters.put("partnerid",payEntity.getPay_info().getPartnerid());
//        parameters.put("prepayid", payEntity.getPay_info().getPrepayid());
//        parameters.put("timestamp",payEntity.getPay_info().getTimestamp());
//        String sign= createSign(parameters);
//
//        L.d("我的sign"+sign);
//        return sign;
//    }

    //支付宝支付
    public void aliPay() {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id);
        params.put("type", type);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.APP_ALIPAY_PARAMS, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(PayWayActivity.this, "请求失败", 5000);
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            final PayOrderInfo payOrderInfo = gson.fromJson(responseBean.getData(), PayOrderInfo.class);
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PayWayActivity.this);
                                    L.i("字符串：" + payOrderInfo.getAlipay_params());
                                    String result = alipay.pay(payOrderInfo.getAlipay_params(), true);
                                    Log.i("msp 支付结果", result);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(PayWayActivity.this, "" + responseBean.getMessage(), 5000);
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    Log.i("msp 支付结果 resultInfo", resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    Log.i("msp 支付结果 resultStatus", resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayWayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                        //跳转支付成功页
                        Intent intent = new Intent(PayWayActivity.this, PaySucceedActivity.class);
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("status_id", "2");
                        intent.putExtra("order_type",order_type);
                        startActivity(intent);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")/*||TextUtils.equals(resultStatus, "6004")*/) {
                            Toast.makeText(PayWayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            Toast.makeText(PayWayActivity.this, "支付已取消", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayWayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 微信支付回调
     *
     * @param result
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("wxPay")
            })
    public void wxPayResult(String result) {
        if (result.equals("payByOk")) {
            RxBus.get().post("WxOrderId", order_id);
            finish();
        }
    }

}
