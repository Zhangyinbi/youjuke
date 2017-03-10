package com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
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
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;

import org.jetbrains.annotations.Contract;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * 忘记密码 手机输入
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-26 16:32
 */
public class PhoneFragemt extends BaseFragment implements View.OnClickListener {

    private ClearEditText editTextMobile;
    private Button buttonNext;
    private ForgetPasswordEntity passwordEntity;
    private String mobile;
    private String type;

    private void assignViews() {
        editTextMobile = (ClearEditText) parentView.findViewById(R.id.edit_text_mobile);
        buttonNext = (Button) parentView.findViewById(R.id.button_next);
        passwordEntity = new ForgetPasswordEntity();
        buttonNext.setOnClickListener(this);
    }


    @Contract(" -> !null")
    public static PhoneFragemt newInstance() {

        return new PhoneFragemt();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forget_password_phone;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_next:
                mobile = editTextMobile.getText().toString();
                if (Validator.isMobile(mobile)) {

                    PhoneVerify(mobile);//首先判断是否是注册用户

                } else {
                    ToastUtil.show(mContext, "请输入正确的手机格式");
                }
                break;

        }
    }

    /**
     * 手机号验证，返回200为没有注册，返回400为注册过
     */
    public void PhoneVerify(String mobiles) {
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
                                type = ((RegisterAndForgetPasswordActivity) getActivity()).getType();
                                if (type.equals(RegisterAndForgetPasswordActivity.REGISTER)) {
                                    //快速注册

                                    if (responseBean.getStatus().equals("400")) {
                                        //注册过提示
                                        ToastUtil.show(mContext, responseBean.getMessage() + "");

                                    } else if (responseBean.getStatus().equals("200")) {
                                        //未注册
                                        passwordEntity.setMobile(editTextMobile.getText().toString());
                                        passwordEntity.setShowIndex(RegisterAndForgetPasswordActivity.VERIFY_FRAGMENT_INDEX);
                                        passwordEntity.setShow(true);
                                        RxBus.get().post("showFragment", passwordEntity);
                                    }
                                } else {

                                    //忘记密码
                                    if (responseBean.getStatus().equals("400")) {
                                        //注册过。开始修改密码业务
                                        passwordEntity.setMobile(editTextMobile.getText().toString());
                                        passwordEntity.setShowIndex(RegisterAndForgetPasswordActivity.VERIFY_FRAGMENT_INDEX);
                                        passwordEntity.setShow(true);
                                        RxBus.get().post("showFragment", passwordEntity);

                                    } else if (responseBean.getStatus().equals("200")) {
                                        //未注册
                                        ToastUtil.show(mContext,"手机号未注册");

                                    }
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(mContext, "请检查网络状态,稍后再试。");

                            }
                        })
        );
    }
}
