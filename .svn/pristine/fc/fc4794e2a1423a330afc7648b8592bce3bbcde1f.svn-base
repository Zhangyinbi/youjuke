package com.youjuke.buildingmaterialmall.app.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.RegisterAndForgetPasswordActivity;
import com.youjuke.buildingmaterialmall.app.receipt_address.ReceiptAddressActivity;
import com.youjuke.buildingmaterialmall.entity.ThirdPartyInfo;
import com.youjuke.buildingmaterialmall.entity.User;
import com.youjuke.buildingmaterialmall.entity.UserInfo;
import com.youjuke.buildingmaterialmall.mtoast.AnimationUtils;
import com.youjuke.buildingmaterialmall.mtoast.XToast;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.glide.CircleTransform;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-28 11:16
 * #0000       zyb          2016-11-21 13:02
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar settingToolBar;
    private AppCompatButton btnLoginOut;
    private TextView textUserName;
    private ImageView imgHead;
    private RelativeLayout rlWx;//微信绑定
    private RelativeLayout rlQq;//QQ绑定
    TextView alterPsw;//修改密码
    TextView address;//收货地址
    TextView tvWxIsBinding;
    TextView tvQqIsBinding;
    SwipeRefreshLayout srRefresh;
    private UserInfo userInfo=null;
    boolean isRelevance=true;
    boolean isFirst=true;//是否第一次登陆
    boolean isCallNet=false;//是否访问网络
    private String unionid=null;

    //    Drawable drawable = getResources().getDrawable(R.mipmap.right_arrow);
    private void assignViews() {
        rlWx= (RelativeLayout) findViewById(R.id.rl_wx);
        rlWx.setOnClickListener(this);
        rlQq= (RelativeLayout) findViewById(R.id.rl_qq);
        rlQq.setOnClickListener(this);
        alterPsw= (TextView) findViewById(R.id.tv_alter_psw);
        alterPsw.setOnClickListener(this);
        address= (TextView) findViewById(R.id.tv_address);
        address.setOnClickListener(this);
        tvWxIsBinding= (TextView) findViewById(R.id.text_wx_isBinding);
        tvQqIsBinding= (TextView) findViewById(R.id.text_qq_isBinding);
        srRefresh= (SwipeRefreshLayout) findViewById(R.id.sr_refresh);
        srRefresh.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light,android.R.color.holo_red_light);
        srRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFirst=false;
                getUserInfoFromNet(isFirst);
            }
        });
        textUserName= (TextView) findViewById(R.id.text_user_name);
        settingToolBar = (Toolbar) findViewById(R.id.logi_tool_bar);
        btnLoginOut = (AppCompatButton) findViewById(R.id.btn_login_out);
        imgHead= (ImageView) findViewById(R.id.img_head);
        btnLoginOut.setOnClickListener(this);
        textUserName.setText(BuildingMaterialApp.user.getUsername());

        //过场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN&&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgHead.setTransitionName("imgHead");
            postponeEnterTransition();
//            当然头像如果是图片如果是加载网络的最好在加载完毕的监听里才调用
            startPostponedEnterTransition();
        }

        if (!TextUtils.isEmpty(BuildingMaterialApp.user.getAvatar().trim())) {
            Glide.with(SettingActivity.this)
                    .load(BuildingMaterialApp.user.getAvatar())
                    .transform(new CircleTransform(SettingActivity.this))
                    .into(imgHead);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        isFirst=false;
        if (isCallNet)
        getUserInfoFromNet(isFirst);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        getUserInfoFromNet(isFirst);//访问网络获取信息数据
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }
    /**
     *
     * @param flag  第一次进入true，以后为false
     */
    private void getUserInfoFromNet(final boolean flag) {
        if (flag)
            mDialog.showDialog();
        params.clear();
        params.put("uid",BuildingMaterialApp.user.getId());
        compositeSubscription.add(RetrofitManager.getInstance().create(CommonService.class).
                getData(new RequestBean.JsonMsg(ApiContstants.ACCOUNT_INFO, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        if(mDialog.isShowing())
                            mDialog.dimissDialog();
                        if(srRefresh.isRefreshing())
                            srRefresh.setRefreshing(false);
                        userInfo = gson.fromJson(responseBean.getData(), UserInfo.class);
                        if (userInfo.isIs_wechat_bind()){
                            tvWxIsBinding.setTextColor(Color.parseColor("#989898"));
                            tvWxIsBinding.setText("已绑定");
                            tvWxIsBinding.setCompoundDrawables(null,null,null,null);
                        }
                        if (userInfo.isIs_qq_bind()){
                            tvQqIsBinding.setTextColor(Color.parseColor("#989898"));
                            tvQqIsBinding.setText("已绑定");
                            tvQqIsBinding.setCompoundDrawables(null,null,null,null);
                        }


                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mDialog.isShowing())
                            mDialog.dimissDialog();
                        if (srRefresh.isRefreshing())
                            srRefresh.setRefreshing(false);
                        if (!flag)
                            XToast.create(SettingActivity.this)
                                    .setText("请检查网络")
                                    .setAnimation(AnimationUtils.ANIMATION_SCALE) //Drawer Type
                                    .setDuration(XToast.XTOAST_DURATION_SHORT)
                                    .show();
                        isRelevance=false;
                    }
                }));
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(settingToolBar);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        settingToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            out();
            mDialog.dimissDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void out(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN&&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAfterTransition();//不然返回时没有过度动画
        }else {
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_out://退出登录
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("确认退出吗?")
                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //删除请求
                                SPUtil.remove(BuildingMaterialApp.getInstance(),"user");
                                BuildingMaterialApp.user=null;
                                ToastUtil.show(SettingActivity.this,"退出登录");
                                finish();

                            }
                        }).setPositiveButton("取消", null).setCancelable(false).show();
                break;
            case R.id.rl_wx://微信绑定
                isCallNet=true;
                if (null!=userInfo&&!userInfo.isIs_wechat_bind()&& isRelevance){
                    wechatLogin();
                }else if (null==userInfo){
                    ToastUtil.show(this,"请刷新最新信息");
                }
                break;
            case R.id.rl_qq://QQ绑定
                isCallNet=true;
                if (null!=userInfo&&!userInfo.isIs_qq_bind()&& isRelevance){
                    qqLogin();
                }else if (null==userInfo ){
                    ToastUtil.show(this,"请刷新最新信息");
                }
                break;
            case R.id.tv_alter_psw://修改登录密码
                alterLoginPsw();
                break;
            case R.id.tv_address://修改地址
                startActivity(new Intent(this, ReceiptAddressActivity.class));
                isCallNet=false;
                break;
        }
    }
        /**
         *修改密码
         *
         */
    private void alterLoginPsw() {
        Intent intent = new Intent(this, RegisterAndForgetPasswordActivity.class);
        if (userInfo==null){
            intent.putExtra("mobile",BuildingMaterialApp.user.getMobile());
        }else {
            intent.putExtra("mobile",userInfo.getMobile());
        }
        intent.putExtra("type", "alterPsw");
        startActivity(intent);
        isCallNet=false;
    }


    int count=0;

    /**
     * QQ登陆
     */
    private void qqLogin() {
        isRelevance = false;
        ShareSDK.initSDK(getApplicationContext());
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
                    ThirdPartyInfo info = new ThirdPartyInfo();
                    info.setId(platDB.getUserId());
                    info.setNickName(platDB.getUserName());
                    info.setPlatform("qq");
                    String imag = (String) res.get("figureurl_qq_2");
                    info.setAvatar(imag);
//                    qq.removeAccount(true);
                    goToalter(info);//去绑定
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                qq.removeAccount(true);
                count++;
                if (count>2){
                    ToastUtil.show(getApplicationContext(), "绑定失败");
                    count=0;
                    return;
                }
                qqLogin();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.show(getApplicationContext(), "取消绑定");
                qq.removeAccount(true);
            }
        });
        qq.showUser(null);
    }
    /**
     * 微信登陆
     */
    private void wechatLogin() {
        isRelevance = false;
        ShareSDK.initSDK(getApplicationContext());
        final Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
                    try {
                        unionid=null;
                        JSONObject json=new JSONObject(platDB.exportData());
                        unionid = json.getString("unionid");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ThirdPartyInfo info = new ThirdPartyInfo();
                    info.setId(platDB.getUserId());
                    info.setNickName(platDB.getUserName());
                    info.setAvatar(platDB.getUserIcon());
                    info.setPlatform("wx");
//                    wechat.removeAccount(true);
                    goToalter(info);//去绑定
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                L.i(throwable.getMessage());
                wechat.removeAccount(true);
                count++;
                if (count>2){
                    ToastUtil.show(getApplicationContext(), "绑定失败");
                    count=0;
                    return;
                }
                wechatLogin();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                ToastUtil.show(getApplicationContext(), "取消绑定");
                L.i("取消绑定");
                wechat.removeAccount(true);
            }
        });
        wechat.showUser(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRelevance=true;
    }

    private void goToalter(final ThirdPartyInfo info) {
        params.clear();
        params.put("openId",info.getId());
        params.put("type",info.getPlatform());
        params.put("nickname",info.getNickName());
        params.put("avatar",info.getAvatar());
        params.put("mobile",userInfo.getMobile());
        if (unionid!=null){//证明是绑定微信的
            params.put("unionid", unionid);
        }
        params.put("login_from","yjkapp");
        params.put("pwd","yjk_wx_login");
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
                                    User user = gson.fromJson(responseBean.getData(), User.class);
                                    SPUtil.setObject(SettingActivity.this, "user", user);//保存用户信息
                                    BuildingMaterialApp.user = user;
                                    getUserInfoFromNet(false);
                                    ToastUtil.show(SettingActivity.this,"绑定成功");
                                    Glide.with(SettingActivity.this)
                                            .load(user.getAvatar())
                                            .placeholder(R.mipmap.head_portrait)
                                            .transform(new CircleTransform(SettingActivity.this))
                                            .into(imgHead);
                                    L.d(responseBean.getData()+"");
                                }else if (responseBean.getStatus().equals("400")&&responseBean.getError().equals("405")){
                                        ToastUtil.show(SettingActivity.this,"绑定失败,此"+info.getPlatform()+"账号已被其他账号绑定过!");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(SettingActivity.this,"请求失败,请检查网络状态");
                            }
                        })
        );
    }

}
