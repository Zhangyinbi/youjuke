package com.youjuke.buildingmaterialmall.app.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ThirdPartyInfo;
import com.youjuke.buildingmaterialmall.entity.User;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.module.SwitchView;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.utils.Installation;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 描述:
 * 帐号密码注册界面
 * <p>
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-25 17:33
 * #0001    tian xiao    2016-11-07 更改新界面，暂停使用
 * #0002    tian xiao    2016-11-14 改为第三方注册并绑定页面使用
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar registerToolBar;
    private Button logoButton;
    private TextView textViewVerify;
    private RelativeLayout relativeLayout2;
    private ClearEditText editTextMobile;
    private ClearEditText editTextVerify;
    private Button buttonVerify;
    private ClearEditText editTextPassword;
    private ClearEditText editTextRepetitionPassword;
    private SwitchView swithchView;
    private int mMaxLenth = 20;//设置允许输入的字符长度
    private CountDownTimer timer;
    private CountDownTimer phoneTimer;
    private ThirdPartyInfo info;
    private LinearLayout linearLayoutPhoneVerify;
    private String mobile;
    private String mCode="";

    private void assignViews() {
        linearLayoutPhoneVerify= (LinearLayout) findViewById(R.id.linear_layout_phone_verify);
        registerToolBar = (Toolbar) findViewById(R.id.register_tool_bar);
        logoButton = (Button) findViewById(R.id.LogoButton);
        textViewVerify = (TextView) findViewById(R.id.textView_verify);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        editTextMobile = (ClearEditText) findViewById(R.id.edit_text_mobile);
        editTextVerify = (ClearEditText) findViewById(R.id.edit_text_verify);
        buttonVerify = (Button) findViewById(R.id.button_verify);
        editTextPassword = (ClearEditText) findViewById(R.id.edit_text_password);
        editTextRepetitionPassword = (ClearEditText) findViewById(R.id.edit_text_repetition_password);
        swithchView = (SwitchView) findViewById(R.id.swithch_view);
        logoButton.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        textViewVerify.setOnClickListener(this);
        swithchView.setOnToggleChanged(new SwitchView.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editTextRepetitionPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {

                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextRepetitionPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle=new Bundle();
        bundle = getIntent().getExtras();
        info=bundle.getParcelable("info");
        if (info == null) {
            finish();
        }
        assignViews();
        phoneVerifyInitialize();//语音验证初始化
    }

    /**
     * 第三方注册并且登录
     */
    @SuppressWarnings("unused")
    private void registerAndLogin() {
        mDialog.showDialog();
        params.clear();
        params.put("openId",info.getId());
        params.put("type",info.getPlatform());
        params.put("nickname",info.getNickName());
        params.put("avatar",info.getAvatar());
        if (info.getUnionid()!=null){//证明是微信注册登录的
            params.put("unionid", info.getUnionid());
        }
        params.put("login_from","yjkapp");
        params.put("pwd","yjk_wx_login");
        params.put(ApiContstants.MOBIlE,editTextMobile.getText().toString());
        params.put(ApiContstants.PASSWORD, editTextPassword.getText().toString());
        params.put("machine_id", Installation.getUniquePsuedoID().replace("-",""));
        params.put("channel",SPUtil.get(this,"channel",getString(R.string.unknown_source)));
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
                                    ToastUtil.show(RegisterActivity.this,"注册成功");
                                    User user = gson.fromJson(responseBean.getData(), User.class);
                                    SPUtil.setObject(RegisterActivity.this, "user", user);//保存用户信息
                                    BuildingMaterialApp.user = user;
                                    L.d(responseBean.getData()+"");
                                    out();
                                }else if (responseBean.getStatus().equals("400")){
                                    ToastUtil.show(RegisterActivity.this,responseBean.getMessage());
                                }
                                mDialog.dimissDialog();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();
                                ToastUtil.show(RegisterActivity.this,"请求失败,请检查网络状态");
                            }
                        })
        );
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(registerToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        registerToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
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
                                        linearLayoutPhoneVerify.setVisibility(View.GONE);
                                    }

                                } else if (responseBean.getStatus().equals("400")) {
                                    linearLayoutPhoneVerify.setVisibility(View.GONE);
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                linearLayoutPhoneVerify.setVisibility(View.GONE);
                            }
                        })
        );
    }

    /**
     * 验证码倒计时
     */
    private void VerifyUtils() {
        timer = new CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {

                buttonVerify.setClickable(false); //设置不可点击
                buttonVerify.setText("重新发送(" + l / 1000 + ")");  //设置倒计时时间
                buttonVerify.setTextSize(16);
                buttonVerify.setBackgroundResource(R.drawable.bg_identify_code_press);
            }

            @Override
            public void onFinish() {

                buttonVerify.setText("重新发送");
                buttonVerify.setTextSize(16);
                buttonVerify.setClickable(true);//重新获得点击
                buttonVerify.setBackgroundResource(R.drawable.bg_identify_code_normal);
            }
        }.start();
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
     * 获取语音验证码
     * 短信验证码为0，语音验证码为1
     * TODO 语音验证码暂定
     */
    public void getPhoneVerify(final int type) {

        params.clear();
        params.put(ApiContstants.MOBIlE, mobile);
        params.put(ApiContstants.YY_TYPE, type+"");
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
                                    mCode = responseBean.getData();
                                    if (type==0){
                                        ToastUtil.show(RegisterActivity.this, "短信已发送,请注意查收。");

                                    }else if(type==1) {
                                        ToastUtil.show(RegisterActivity.this, "正在拨打您的电话,请注意接听。");
                                    }
                                } else if (responseBean.getStatus().equals("400")) {
                                    timerByEnd();
                                    ToastUtil.show(RegisterActivity.this, "" + responseBean.getMessage());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                timerByEnd();
                                ToastUtil.show(RegisterActivity.this, "请求失败,请检查网络状态");
                            }
                        })
        );
    }

    /**
     * 手机号验证，返回200为没有注册，返回400为注册过
     * 0短信 1语音
     */
    public void PhoneVerify(String mobiles, final int type) {
        if (type==0){
            VerifyUtils();
        }else if(type==1){
            phoneVerifyUtils();
        }
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
                                    ToastUtil.show(RegisterActivity.this, "帐号已注册");
                                    timerByEnd();
                                    //注册过
                                } else if (responseBean.getStatus().equals("200")) {
                                    getPhoneVerify(type);//发送验证码
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                timerByEnd();
                                ToastUtil.show(RegisterActivity.this, "请检查网络状态,稍后再试。");
                            }
                        })
        );
    }

    private void timerByEnd(){
        if (timer!=null){
            buttonVerify.setText("重新发送");
            buttonVerify.setTextSize(16);
            buttonVerify.setClickable(true);//重新获得点击
            buttonVerify.setBackgroundResource(R.drawable.bg_identify_code_normal);
            timer.cancel();

        }
        if (phoneTimer!=null){
            phoneTimer.cancel();
            textViewVerify.setText("语音验证码");
            textViewVerify.setTextSize(16);
            textViewVerify.setClickable(true);//重新获得点击
            textViewVerify.setTextColor(getResources().getColor(R.color.c3dbf73));
        }
    }

    /**
     * 单击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        mobile=editTextMobile.getText().toString();
        switch (v.getId()) {
            case R.id.button_verify://验证码获取
                if (Validator.isMobile(mobile)) {
                    PhoneVerify(mobile,0);
               } else {
                    ToastUtil.show(RegisterActivity.this, "手机号格式不正确,请检查下呦");
                }
                break;
            case R.id.textView_verify://语音验证码
                ToastUtil.show(this, "请注意接听电话呦");
                PhoneVerify(mobile,1);
                break;
            case R.id.LogoButton://登录
                if (mobile.length()<=0||!Validator.isMobile(mobile)){
                    ToastUtil.show(RegisterActivity.this, "手机号格式不正确,请检查下呦");
                }else if (!mCode.equals(editTextVerify.getText().toString())){
                    ToastUtil.show(RegisterActivity.this, "验证码错误,请检查下呦");
                }else if(!editTextPassword.getText().toString().equals(editTextRepetitionPassword.getText()
                .toString())){
                    ToastUtil.show(RegisterActivity.this, "两次密码不相同,请检查下呦");
                }else {
                    registerAndLogin();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            out();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void out() {
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
