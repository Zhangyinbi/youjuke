package com.youjuke.buildingmaterialmall.app.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment.PasswordFragment;
import com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment.ForgetPasswordEntity;
import com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment.PhoneFragemt;
import com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment.VerifyFragment;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;


/**
 * 描述:快速注册和忘记密码
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-26 16:32
 * #0001    Tian Xiao    2016-11-04 快速注册和忘记密码公用一个界面。
 */
public class RegisterAndForgetPasswordActivity extends BaseActivity {

    public static final String REGISTER="register";
    public static final String ALTERPSW="alterPsw";
    public static final int VERIFY_FRAGMENT_INDEX=1;
    public static final int PASSWORD_FRAGMENT_INDEX=2;
    private static int FRAGEMTN_INDEX = 0;//默认为0
    private static String mobile = "";
    private Toolbar toolBar;
    private FrameLayout frameLayoutPager;
    private PhoneFragemt phoneFragemt;
    private Fragment[] fragments;
    private PasswordFragment passwordFragment;
    private VerifyFragment verifyFragment;
    private AlertDialog.Builder builder;
    private TextView textViewGoBack;
    private TextView textViewCancel;
    private AlertDialog dialog;
    private TextView toolbarTextTitle;
    private String type;
    private String code;
    public String getCode(){return code;}
    public String getType() {
        return type;
    }
    public String getMobile() {
        return mobile;
    }

    private void assignViews() {
        toolbarTextTitle= (TextView) findViewById(R.id.tool_bar_text_title);
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        frameLayoutPager = (FrameLayout) findViewById(R.id.frameLayout_pager);
        phoneFragemt = PhoneFragemt.newInstance();
        passwordFragment = PasswordFragment.newInstance();
        verifyFragment = VerifyFragment.newInstance();
        fragments = new Fragment[]{
                phoneFragemt, verifyFragment, passwordFragment
        };
        // 添加显示第一个fragment
        if (type.equals(ALTERPSW)){
            showFragment(2);
        }else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout_pager, fragments[FRAGEMTN_INDEX])
                    .show(phoneFragemt).commit();
        }
        if (fragments==null) {
            fragments = new Fragment[]{
                    phoneFragemt, verifyFragment, passwordFragment
            };
            // 添加显示第一个fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout_pager, fragments[FRAGEMTN_INDEX])
                    .show(phoneFragemt).commit();
        }
    }

    /**
     * 显示fragment
     * @param postion
     */
    public void showFragment(int postion) {
        if (postion < 0) {
            postion = 0;
        }
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[FRAGEMTN_INDEX]);
        if (!fragments[postion].isAdded()) {
            trx.add(R.id.frameLayout_pager, fragments[postion]);
        }
        trx.show(fragments[postion]).commit();
        FRAGEMTN_INDEX = postion;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        type="";
        type=getIntent().getStringExtra("type");
        assignViews();
        if (type.equals(REGISTER)){
            //快速注册
            toolbarTextTitle.setText("快速注册");
        }else if (type.equals(ALTERPSW)){//从这是跳转过来
            toolbarTextTitle.setText("修改密码");
            mobile=getIntent().getStringExtra("mobile");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initToolBar() {
        toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(type.equals(REGISTER)) {
                        out();
                    }else {
                        toolbarGoBack();
                    }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(type.equals(REGISTER)) {
                out();
            }else {
                toolbarGoBack();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     * toolbar 返回
     */
    public void toolbarGoBack(){
        builder=new AlertDialog.Builder(RegisterAndForgetPasswordActivity.this);
        LayoutInflater inflater = LayoutInflater.from(RegisterAndForgetPasswordActivity.this);
        View layout=inflater.inflate(R.layout.alert_dialog_change_password_activity,null);
        builder.setView(layout);
        dialog = builder.show();
        textViewGoBack= (TextView) layout.findViewById(R.id.text_go_back);
        textViewCancel= (TextView) layout.findViewById(R.id.text_cancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textViewGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                out();
            }
        });
    }


    /**
     * fragment跳转并且传参
     *
     * @param entity
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("showFragment")
            }
    )
    public void showFragment(ForgetPasswordEntity entity) {
        if (entity.getMobile() != null) {
            mobile = entity.getMobile();
        }

        if (entity.getCode()!=null){
            code=entity.getCode();
        }
        //是否跳转页面
        if (entity.isShow()) {
            showFragment(entity.getShowIndex());
        }
    }

    /**
     * 修改成功跳转
     * @param flog
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("goBack")
            }
    )
    public void goBack(String flog){//跳转
        if (flog.equals("true")){
            ActivityManager.getInstance().finishActivity(LoginActivity.class);
            out();
         //RxBus.get().post("showTab","logIn");
        }
    }

    /**
     * 退出
     */
    public void out() {
        showFragment(0);
        this.finish();
    }


}
