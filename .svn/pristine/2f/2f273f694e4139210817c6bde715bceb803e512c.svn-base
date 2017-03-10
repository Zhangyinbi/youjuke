package com.youjuke.buildingmaterialmall.app.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ThirdPartyInfo;
import com.youjuke.buildingmaterialmall.entity.User;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:第三方绑定
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-14 11:46
 */
public class ThirdPartyRegisterAndLoginActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar logiToolBar;
    private LinearLayout linearLayoutPhoneVerifyText;
    private TextView textViewVerify;
    private LinearLayout linearLayout;
    private ClearEditText editTextMobile;
    private AppCompatButton btnVerify;
    private ClearEditText editTextCode;
    private TextView textViewLabel;
    private AppCompatButton btnLogin;
    private CountDownTimer timer;
    private CountDownTimer phoneTimer;
    private String modile;
    private String code;
    private ThirdPartyInfo info;
    private Boolean isRegister = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_third_party_register_and_login;
    }

    @SuppressLint("SetTextI18n")
    private void assignViews() {
        logiToolBar = (Toolbar) findViewById(R.id.logi_tool_bar);
        linearLayoutPhoneVerifyText = (LinearLayout) findViewById(R.id.linear_layout_phone_verify_text);
        textViewVerify = (TextView) findViewById(R.id.textView_verify);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        editTextMobile = (ClearEditText) findViewById(R.id.edit_text_mobile);
        btnVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        editTextCode = (ClearEditText) findViewById(R.id.edit_text_code);
        textViewLabel = (TextView) findViewById(R.id.textView_label);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        btnVerify.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        textViewVerify.setOnClickListener(this);
        if (info.getPlatform().toLowerCase().equals("qq")) {
            textViewLabel.setText("关联后,您的QQ帐号和优居客帐号都可以登录");
        }
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        info = bundle.getParcelable("info");
        if (info == null) {
            finish();
        }
        assignViews();
        phoneVerifyInitialize();
    }

    @Override
    public void onClick(View v) {
        modile = editTextMobile.getText().toString();
        switch (v.getId()) {
            case R.id.btn_login://登录
                if (modile.length() <= 0 || !Validator.isMobile(modile)) {
                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "手机号格式不正确,请检查下呦");
                } else if (!code.equals(editTextCode.getText().toString()) || code.length() <= 0) {
                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "验证码错误,请检查下呦");
                } else {
                    loginThirdParty();
                }
                break;
            case R.id.textView_verify://语音验证码
                PhoneVerify(modile, 1);

                break;
            case R.id.btn_verify://验证码
                PhoneVerify(modile, 0);
                break;
            default:
                break;
        }

    }

    /**
     * 手机号验证，返回200为没有注册，返回400为注册过
     * 0短信 1语音
     */
    public void PhoneVerify(String mobiles, final int type) {
        params.clear();
        params.put(ApiContstants.MOBIlE, mobiles);
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.CHECK_REGISTER, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if (responseBean.getStatus().equals("400")) {
                                    if (type == 1) {
                                        phoneVerifyUtils();
                                        getPhoneVerify();
                                    } else if (type == 0) {
                                        VerifyUtils();
                                        getCode();
                                    }
                                } else if (responseBean.getStatus().equals("200")) {
                                    //未注册
                                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "手机号未注册");
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                if (timer != null) timer.onFinish();
                                if (phoneTimer != null) phoneTimer.onFinish();
                                ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "请检查网络状态,稍后再试。");
                            }
                        })
        );
    }



    /**
     * 登录关联
     */
    private void loginThirdParty() {
        mDialog.showDialog();
        params.clear();
        params.put("openId",info.getId());
        params.put("type",info.getPlatform());
        params.put("nickname",info.getNickName());
        params.put("avatar",info.getAvatar());
        params.put("mobile",editTextMobile.getText().toString());
        params.put("pwd","yjk_wx_login");
        params.put("login_from","yjkapp");
        if (info.getUnionid()!=null){//证明是微信注册登录的
            params.put("unionid", info.getUnionid());
        }
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.SET_MALLUSER, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if (responseBean.getStatus().equals("200")){
                                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this,"注册成功");
                                    User user = gson.fromJson(responseBean.getData(), User.class);
                                    SPUtil.setObject(ThirdPartyRegisterAndLoginActivity.this, "user", user);//保存用户信息
                                    BuildingMaterialApp.user = user;
                                    L.d(responseBean.getData()+"");
                                    out();
                                }else if (responseBean.getStatus().equals("400")){
                                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this,responseBean.getMessage());
                                }
                                mDialog.dimissDialog();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();
                                ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this,"请求失败,请检查网络状态");
                            }
                        })
        );
    }

    /**
     * 获取语音验证码
     * 短信验证码为0，语音验证码为1
     * TODO 语音验证码暂定
     */
    public void getPhoneVerify() {
        params.clear();
        params.put(ApiContstants.MOBIlE, modile);
        params.put(ApiContstants.YY_TYPE, "1");
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.REGISTER_CODE, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.d(responseBean.getData() + "");
                                if (responseBean.getStatus().equals("200")) {
                                    code = responseBean.getData();
                                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "正在拨打您的电话,请注意接听。");
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "" + responseBean.getMessage());

                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "请求失败,请检查网络状态");
                            }
                        })
        );
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        params.clear();
        params.put(ApiContstants.MOBIlE, modile);
        params.put("login_from","yjkapp");
        //开始请求
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData((new RequestBean.JsonMsg(ApiContstants.GETCODE, params)).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        L.i("返回信息:" + responseBean.toString());
                        if (responseBean.getStatus().equals("400")) {
                            ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, responseBean.getMessage());
                        } else if (responseBean.getStatus().equals("200")) {
                            code = responseBean.getData();
                            ToastUtil.show(ThirdPartyRegisterAndLoginActivity.this, "验证码已发送,请注意查收");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        L.i("验证码获取时错误:" + throwable.toString());
                    }
                });
        compositeSubscription.add(subscribe);
    }


    /**
     * 请求服务器，判断语音验证码是否正常，如果正常则显示，否则隐藏语音验证。
     */
    private void phoneVerifyInitialize() {
        params.clear();
        params.put("lock", "YYYZM");
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.CHECK_YY, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.d(responseBean.getData() + "");
                                if (responseBean.getStatus().equals("200")) {
                                    if (responseBean.getData().equals("0")) {
                                        linearLayoutPhoneVerifyText.setVisibility(View.GONE);
                                    }

                                } else if (responseBean.getStatus().equals("400")) {
                                    linearLayoutPhoneVerifyText.setVisibility(View.GONE);
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                linearLayoutPhoneVerifyText.setVisibility(View.GONE);
                            }
                        })
        );
    }

    /**
     * 语音验证码倒计时
     */
    private void phoneVerifyUtils() {

        phoneTimer = new CountDownTimer(15000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                textViewVerify.setClickable(false); //设置不可点击
                textViewVerify.setText("语音验证码(" + l / 1000 + ")");  //设置倒计时时间
                textViewVerify.setTextColor(getResources().getColor(R.color.c_999999));
                textViewVerify.setTextSize(16);
            }

            @Override
            public void onFinish() {
                textViewVerify.setText("语音验证码");
                textViewVerify.setTextSize(16);
                textViewVerify.setClickable(true);//重新获得点击
                textViewVerify.setTextColor(getResources().getColor(R.color.c3dbf73));
            }
        }.start();
    }

    /**
     * 验证码倒计时
     */
    private void VerifyUtils() {
        timer = new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                btnVerify.setClickable(false); //设置不可点击
                btnVerify.setText("重新发送(" + l / 1000 + ")");  //设置倒计时时间
                btnVerify.setBackgroundResource(R.drawable.bg_identify_code_press);
                btnVerify.setTextColor(getResources().getColor(R.color.colorTextWhite));
            }

            @Override
            public void onFinish() {
                btnVerify.setText("重新发送");
                btnVerify.setClickable(true);//重新获得点击
                btnVerify.setTextColor(getResources().getColor(R.color.colorTextWhite));
                btnVerify.setBackgroundResource(R.drawable.bg_identify_code_normal);
            }
        }.start();
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(logiToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        logiToolBar.setNavigationOnClickListener(new View.OnClickListener() {
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


    /**
     * 退出
     */
    private void out() {
        if (timer != null) {
            timer.cancel();
        }
        if (phoneTimer!=null){
            phoneTimer.cancel();
        }
        if (BuildingMaterialApp.user!=null){
            ActivityManager.getInstance().finishActivity(ThirdPartyLoginActivity.class);
            ActivityManager.getInstance().finishActivity(LoginActivity.class);
        }
        this.finish();
    }

}
