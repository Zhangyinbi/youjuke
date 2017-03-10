package com.youjuke.buildingmaterialmall.app.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
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
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import com.youjuke.swissgearlibrary.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 描述:登录页面
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-09 10:48
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar logiToolBar;
    private ClearEditText editTextMobile;
    private ClearEditText editTextPassword;
    private AppCompatButton btnLogin;
    private boolean isLogin = false;
    private LinearLayout ll_other_login_wechat;
    private LinearLayout ll_other_login_qq;
    private TextView mTvRegister;
    private TextView mForgetPassword;
    private TextView mQuickLoginByMobile;
    private Intent intent;
    private String modile;
    private String password;
    private String orderLogin;
    private User user;
    private Boolean isRelevance = true;


    private void assignViews() {
        logiToolBar = (Toolbar) findViewById(R.id.logi_tool_bar);
        editTextMobile = (ClearEditText) findViewById(R.id.edit_text_mobile);
        editTextPassword = (ClearEditText) findViewById(R.id.edit_text_password);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        ll_other_login_wechat = (LinearLayout) findViewById(R.id.ll_other_login_wechat);
        ll_other_login_qq = (LinearLayout) findViewById(R.id.ll_other_login_qq);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mForgetPassword = (TextView) findViewById(R.id.forget_password);
        mQuickLoginByMobile = (TextView) findViewById(R.id.quick_login_by_mobile);


        ll_other_login_qq.setClickable(true);
        ll_other_login_qq.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ll_other_login_wechat.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
        mQuickLoginByMobile.setOnClickListener(this);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar() {
        logiToolBar.setTitleTextColor(Color.parseColor("#ffffff"));
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

    /**
     * 单击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登陆
                modile = editTextMobile.getText().toString();
                password = editTextPassword.getText().toString();
                if (modile.length() <= 0) {
                    ToastUtil.show(LoginActivity.this, "手机号不能为空");
                } else if (password.length() <= 5) {
                    ToastUtil.show(LoginActivity.this, "密码不小于6位");
                } else if (Validator.isMobile(modile)) {
                    login();//开始登录
                } else {
                    ToastUtil.show(LoginActivity.this, "帐号应为注册过的手机号");
                }
                break;
            case R.id.ll_other_login_wechat://微信登陆
                if (isRelevance && isWeixinAvilible()) {
                    wechatLogin();
                }else {
                    ToastUtil.show(this,"未安装微信");
                }
                break;
            case R.id.ll_other_login_qq://qq登陆
                if (isRelevance&&isQQClientAvailable()) {
                    qqLogin();
                }else {
                    ToastUtil.show(this,"未安装QQ");
                }
                break;
            case R.id.tv_register://注册
                intent = new Intent(LoginActivity.this, RegisterAndForgetPasswordActivity.class);
                intent.putExtra("type", "register");
                startActivity(intent);
                break;
            case R.id.forget_password://忘记密码
                intent = new Intent(LoginActivity.this, RegisterAndForgetPasswordActivity.class);
                intent.putExtra("type", "forgetPassword");
                startActivity(intent);
                break;
            case R.id.quick_login_by_mobile://手机快捷登陆
                intent = new Intent(LoginActivity.this, LoginByMobileActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 判断微信是否安装
     *
     * @return
     */
    public static boolean isWeixinAvilible() {
        final PackageManager packageManager = BuildingMaterialApp.getInstance().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断qq是否可用
     *
     *
     * @return
     */
    public static boolean isQQClientAvailable() {
        final PackageManager packageManager =  BuildingMaterialApp.getInstance().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查第三方登录的账户是否存在数据库
     */
    private void getUserIsExists(final ThirdPartyInfo info) {


        params.clear();
        params.put("openId", info.getId());
        params.put("type", info.getPlatform());
        params.put("nickname", info.getNickName());
        params.put("avatar", info.getAvatar());
        if (info.getUnionid() != null) {//证明是点击微信登录
            params.put("unionid", info.getUnionid());

        }
        params.put("pwd", "yjk_wx_login");
        params.put("login_from", "yjkapp");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.GET_USER_IS_EXISTS, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        mDialog.showDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.i(e.getMessage());
                        mDialog.dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("返回数据:" + responseBean.toString());
                        if (responseBean.getData() != null && !"null".equalsIgnoreCase(responseBean.getData())) {
                            L.i("返回数据:" + responseBean.getData());
                            if ("200".equals(responseBean.getStatus())) {
                                //登录成功
                                ToastUtil.show(LoginActivity.this, "登录成功");
                                User user = gson.fromJson(responseBean.getData(), User.class);
                                SPUtil.setObject(LoginActivity.this, "user", user);//保存用户信息

                                BuildingMaterialApp.user = user;
                                setResult(1);
                                out();
                            } else if ("400".equals(responseBean.getStatus()) && "405".equals(responseBean.getError())) {
                                //405 无此用户  也就是未绑定的用户或未注册的用户
                                mDialog.dimissDialog();
                                Intent intent = new Intent(LoginActivity.this, ThirdPartyLoginActivity.class);
                                intent.putExtra("thirdPartyInfo", info);
                                startActivity(intent);
                            } else {
                                ToastUtil.show(LoginActivity.this, responseBean.getMessage());
                                mDialog.dimissDialog();
                            }
                        } else {
                            ToastUtil.show(LoginActivity.this, "系统繁忙,请稍后再试");
                            mDialog.dimissDialog();
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * QQ登陆
     */
    private void qqLogin() {
        mDialog.showDialog();
        isRelevance = false;
        ToastUtil.show(getApplicationContext(), "开始登录");
        ShareSDK.initSDK(getApplicationContext());
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                isRelevance = true;
                if (action == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
                    //通过DB获取各种数据
                    //platDB.getToken();
                    //platDB.getUserGender();
                    //figureurl_qq_2 100*100的头像 清晰
//                    platDB.getUserIcon();//这个获取的是figureurl_qq_1 是40*40的模糊
//                    platDB.getUserId();
//                    platDB.getUserName();
                    //遍历Map
//                    Iterator ite = res.entrySet().iterator();
//                    String info = "";
//                    while (ite.hasNext()) {
//                        Map.Entry entry = (Map.Entry) ite.next();
//                        Object key = entry.getKey();
//                        Object value = entry.getValue();
//                        info += "{" + key + "： " + value + "}+\n";
//                    }
//                    ToastUtil.show(getApplicationContext(), info, Toast.LENGTH_LONG);
                    //qq.removeAccount(true);
                    ThirdPartyInfo info = new ThirdPartyInfo();
                    info.setId(platDB.getUserId());
                    info.setNickName(platDB.getUserName());
                    info.setPlatform("qq");
                    String imag = (String) res.get("figureurl_qq_2");
                    info.setAvatar(imag);
                    getUserIsExists(info);
                    //qq.removeAccount(true);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                //ToastUtil.show(getApplicationContext(), throwable.getMessage());
                qq.removeAccount(true);
                isRelevance = true;
                mDialog.dimissDialog();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.show(getApplicationContext(), "取消登录");
                L.i("取消登陆");
                mDialog.dimissDialog();
                qq.removeAccount(true);
                isRelevance = true;
            }
        });
        qq.showUser(null);
    }

    /**
     * 微信登陆
     */
    private void wechatLogin() {
        mDialog.showDialog();
        isRelevance = false;
        ToastUtil.show(getApplicationContext(), "开始登录");
        ShareSDK.initSDK(getApplicationContext());
        final Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();//获取数平台数据DB\

                    //通过DB获取各种数据
                    //platDB.getToken();
                    //platDB.getUserGender();
//                    platDB.getUserIcon();
//                    platDB.getUserId();
//                    platDB.getUserName();
                    // ToastUtil.show(getApplicationContext(), platDB.getUserName() + "/n" + platDB.getUserIcon() + "/n" + platDB.getUserId());
                    //L.i("username: " + platDB.getUserName());//Kevin
                    //L.i("UserIcon: " + platDB.getUserIcon());//http://wx.qlogo.cn/mmopen/oPqClvCZOtwBzjpXHeaWLqg7nth2YAibsPVIGiaowN705loDFDwNib8q1dh98rYLgoV8Dfg57LkiaZibpPhcY2ia1eevHODN3k4rW7/0
                    //L.i("UserId: " + platDB.getUserId());//oQco2wS-BEiv_glohIu_h2QOnSYk
                    //wechat.removeAccount(true);
                    ThirdPartyInfo info = new ThirdPartyInfo();
                    info.setId(platDB.getUserId());
                    info.setNickName(platDB.getUserName());
                    info.setAvatar(platDB.getUserIcon());

                    info.setPlatform("wx");
                    try {
                        JSONObject json = new JSONObject(platDB.exportData());
                        info.setUnionid(json.getString("unionid"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getUserIsExists(info);
                    mDialog.showDialog();
                    //wechat.removeAccount(true);

                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                // ToastUtil.show(getApplicationContext(), throwable.getMessage());
                L.i(throwable.getMessage());
                wechat.removeAccount(true);
                mDialog.dimissDialog();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.show(getApplicationContext(), "取消登录");
                L.i("取消登陆");
                wechat.removeAccount(true);
                mDialog.dimissDialog();
            }
        });
        wechat.showUser(null);
    }

    /**
     * 登录
     */
    public void login() {
        params.clear();
        params.put(ApiContstants.MOBIlE, modile);
        params.put(ApiContstants.PASSWORD, password);
        params.put("login_from", "yjkapp");
        RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.ACCOUNTS_LOGIN, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        if (responseBean.getStatus().equals("200")) {
                            //登录成功
                            ToastUtil.show(LoginActivity.this, "登录成功");
                            user = gson.fromJson(responseBean.getData(), User.class);

                            SPUtil.setObject(LoginActivity.this, "user", user);//保存用户信息
                            BuildingMaterialApp.user = user;
                            isLogin = true;
                            setResult(1);
                            out();
                        } else if (responseBean.getStatus().equals("400")) {
                            ToastUtil.show(LoginActivity.this, "" + responseBean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.show(LoginActivity.this, getString(R.string.login_fail_please_check_net_or_try_later));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        isRelevance = true;
        mDialog.dimissDialog();
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

        mDialog.dimissDialog();
        this.finish();
    }

}
