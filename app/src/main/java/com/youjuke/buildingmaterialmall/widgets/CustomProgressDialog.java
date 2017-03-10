package com.youjuke.buildingmaterialmall.widgets;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.youjuke.buildingmaterialmall.R;


/**
 * 自定义请求加载进度条
 *
 */
public class CustomProgressDialog extends ProgressDialog {

    private AnimationDrawable mAnimation;
    private ImageView mImageView;
//    private int mResid;


    public CustomProgressDialog(Context context, int id) {
        super(context, R.style.MyProgressDialogStyle);
//        this.mResid = id;
        setCanceledOnTouchOutside(true );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
//        mImageView.setBackgroundResource(mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }

    private void initView() {
        setContentView(R.layout.dialog_progress);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }

}
