package com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.RegisterAndForgetPasswordActivity;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import org.jetbrains.annotations.Contract;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:找回密码验证页面
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-26 16:33
 */
public class VerifyFragment extends BaseFragment implements View.OnClickListener {

    private TextView textViewVerifyInfo;
    private ClearEditText editTextVerify;
    private Button buttonVerify;
    private Button buttonNextStep;
    private TextView textViewVerify;
    private String mobile;
    private CountDownTimer timer;
    private CountDownTimer phoneTimer;
    private ForgetPasswordEntity entity;
    private LinearLayout linearLayoutPhoneVerifyText;
    private String mCode="";
    private ForgetPasswordCode forgetPasswordCode;
    private String type="";

    @Contract(" -> !null")
    public static VerifyFragment newInstance() {
        return new VerifyFragment();
    }

    @SuppressLint("SetTextI18n")
    private void assignViews() {
        entity = new ForgetPasswordEntity();
        forgetPasswordCode = new ForgetPasswordCode();
        linearLayoutPhoneVerifyText = (LinearLayout) parentView.findViewById(R.id.linearLayout_phoneVerify_text);
        textViewVerifyInfo = (TextView) parentView.findViewById(R.id.textView_verify_info);
        editTextVerify = (ClearEditText) parentView.findViewById(R.id.edit_text_verify);
        buttonVerify = (Button) parentView.findViewById(R.id.button_verify);
        buttonNextStep = (Button) parentView.findViewById(R.id.button_next_step);
        textViewVerify = (TextView) parentView.findViewById(R.id.textView_verify);
        buttonVerify.setOnClickListener(this);
        buttonNextStep.setOnClickListener(this);
        textViewVerify.setOnClickListener(this);
        mobile = ((RegisterAndForgetPasswordActivity) getActivity()).getMobile();
        textViewVerifyInfo.setText("请输入" + mobile.substring(0, 3) + "****" +
                mobile.substring(mobile.length() - 4, mobile.length()) + "收到的短信验证码");
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
                                    if (responseBean.getData().equals("1")) {
                                        linearLayoutPhoneVerifyText.setVisibility(View.VISIBLE);
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

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forget_passwoed_verify;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
        type = ((RegisterAndForgetPasswordActivity) getActivity()).getType();
        VerifyUtils();
        getForgetPasswordCode();//默认发送验证码
         phoneVerifyInitialize();//语音验证码初始
        //linearLayoutPhoneVerifyText.setVisibility(View.GONE);//语音暂时直接隐藏
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_verify://语音验证码
                phoneVerifyUtils();// 语音验证码计时
                getPhoneVerify();
                break;
            case R.id.button_verify://验证码按钮
                VerifyUtils();//验证码计时
                getForgetPasswordCode();
                break;
            case R.id.button_next_step://下一步
                if (editTextVerify.getText().length() <= 0) {
                    ToastUtil.show(mContext, "验证码不能为空");
                        L.d(mCode+"");
                } else if (mCode.length()>0) {
                    if (!editTextVerify.getText().toString().equals(mCode)) {
                        ToastUtil.show(mContext, "验证码不正确");
                    } else {
                        entity.setShowIndex(RegisterAndForgetPasswordActivity.PASSWORD_FRAGMENT_INDEX);
                        entity.setCode(mCode);
                        entity.setShow(true);
                        RxBus.get().post("showFragment", entity);
                    }
                } else {
                    ToastUtil.show(mContext, "验证码发送中~~~~~~");
                }
                break;
        }
    }

    /**
     * 获取语音验证码
     * 短信验证码为0，语音验证码为1
     * TODO 语音验证码暂定
     */
    public void getPhoneVerify() {
        params.clear();
        params.put(ApiContstants.MOBIlE, mobile);
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
                                    mCode = responseBean.getData();
                                    ToastUtil.show(mContext, "正在拨打您的电话,请注意接听。");
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(mContext, "" + responseBean.getMessage());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(mContext, "请求失败,请检查网络状态");
                            }
                        })
        );

    }


    /**
     * 验证码
     * yytype	短信验证码为0，语音验证码为1
     */
    public void getForgetPasswordCode() {
        if (type.equals(RegisterAndForgetPasswordActivity.REGISTER)) {//判断是注册获取验证码
            params.clear();
            params.put(ApiContstants.MOBIlE, mobile);
            params.put(ApiContstants.YY_TYPE, "0");
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
                                        mCode = responseBean.getData()+"";
                                        ToastUtil.show(mContext, "短信已发送,请注意查收");
                                    } else if (responseBean.getStatus().equals("400")) {
                                        ToastUtil.show(mContext, "" + responseBean.getMessage());
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    ToastUtil.show(mContext, "请求失败,请检查网络状态");
                                }
                            })
            );
        } else {
            //忘记密码获取验证码。。。
            params.clear();
            params.put("mobile", mobile);
            compositeSubscription.add(
                    RetrofitManager.getInstance().create(CommonService.class)
                            .getData(new RequestBean.JsonMsg(ApiContstants.GET_IDENTIFYING_CODE,
                                    params).toJson())
                            .compose(this.<ResponseBean>bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<ResponseBean>() {
                                @Override
                                public void call(ResponseBean responseBean) {
                                    //成功
                                    if (responseBean.getStatus().equals("200")) {
                                        ToastUtil.show(mContext, "短信已发送,请注意查收!");
                                        VerifyUtils();//验证码计时
                                        forgetPasswordCode = gson.fromJson(responseBean.getData()
                                                , ForgetPasswordCode.class);
                                        L.d(forgetPasswordCode.getCode());
                                        mCode=forgetPasswordCode.getCode();
                                    } else if (responseBean.getStatus().equals("400")) {
                                        ToastUtil.show(mContext, responseBean.getMessage() + "");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    //失败
                                    ToastUtil.show(mContext, "请求错误,请稍后再试");
                                }
                            })
            );
        }
    }

    /**
     * 获取修改密码验证码类
     */
    class ForgetPasswordCode {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        /*if (timer != null) {
            timer.cancel();
        }

        if (phoneTimer != null) {
            phoneTimer.cancel();
        }*/
    }
}
