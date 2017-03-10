package com.youjuke.buildingmaterialmall.app.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ThirdPartyInfo;
import com.youjuke.buildingmaterialmall.entity.UserInfo;
import com.youjuke.buildingmaterialmall.update.RxBus;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.glide.CircleTransform;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * 描述: 第三方登陆Activity
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-10-25 15:19
 */
public class ThirdPartyLoginActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar union_login_tool_bar;
    private ImageView mImgHead;
    private TextView mTvPlatformUserLabel;
    private TextView mTvPlatformUserName;
    private Button mBtnQuickRegister;
    private Button mBtnImmediatelyRelated;
    private String platform = "xx";
    private ThirdPartyInfo info;

    private void assignViews() {
        mImgHead = (ImageView) findViewById(R.id.img_head);
        mTvPlatformUserLabel = (TextView) findViewById(R.id.tv_platform_user_label);
        mTvPlatformUserName = (TextView) findViewById(R.id.tv_platform_user_name);
        mBtnQuickRegister = (Button) findViewById(R.id.btn_quick_register);
        mBtnImmediatelyRelated = (Button) findViewById(R.id.btn_immediately_related);
        union_login_tool_bar = (Toolbar) findViewById(R.id.union_login_tool_bar);
        mBtnQuickRegister.setOnClickListener(this);
        mBtnImmediatelyRelated.setOnClickListener(this);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();

        info = getIntent().getParcelableExtra("thirdPartyInfo");
        if (info != null) {
            Glide.with(this).load(info.getAvatar())
                    .transform(new CircleTransform(this))
                    .into(mImgHead);

            if ("wx".equals(info.getPlatform())) {
                platform = "微信";
            } else {
                platform = "QQ";
            }
            mTvPlatformUserLabel.setText("亲爱的" + platform + "用户:");
            mTvPlatformUserName.setText(info.getNickName());
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

    private void out() {
        this.finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_third_party_login;
    }

    @Override
    public void initToolBar() {
        union_login_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quick_register://快速注册并绑定
                Intent intent = new Intent(ThirdPartyLoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", info);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_immediately_related://立即关联
                Intent intentRelated=new Intent(ThirdPartyLoginActivity.this,ThirdPartyRegisterAndLoginActivity.class);
                Bundle bundleRelated = new Bundle();
                bundleRelated.putParcelable("info", info);
                intentRelated.putExtras(bundleRelated);
                startActivity(intentRelated);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
