package com.youjuke.buildingmaterialmall.app.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.home.MainActivity;
import com.youjuke.buildingmaterialmall.widgets.CircleIndicator.CircleIndicator;
import com.youjuke.swissgearlibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-11 15:02
 */
public class LeadActivity extends BaseActivity implements View.OnClickListener {


    //图片资源
    private static final int[] guideImages={R.mipmap.lead1, R.mipmap.lead2,R.mipmap.lead3};
    private ViewPager viewpager;
    private List<View> views;
    private LeadPageAdapter pageAdapter;
    private CircleIndicator indicator;
    private Button buttonSkip;
    private Button buttonOut;

    private void assignViews() {
        buttonOut = (Button) findViewById(R.id.button_out);
        buttonSkip = (Button) findViewById(R.id.button_skip);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        buttonSkip.setOnClickListener(this);
        buttonOut.setOnClickListener(this);
        buttonOut.setVisibility(View.GONE);
        views = new ArrayList<View>();
        pageAdapter = new LeadPageAdapter(views);
        viewpager.setAdapter(pageAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    buttonSkip.setVisibility(View.VISIBLE);
                }else {
                    buttonSkip.setVisibility(View.GONE);
                }
                if (position==2){
                    buttonOut.setVisibility(View.VISIBLE);
                }else {
                    buttonOut.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        // 初始化引导图片列表
        for (int i = 0; i < guideImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            //防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //加载图片资源
            Glide.with(this).load(guideImages[i]).into(iv);
            views.add(iv);
        }
        pageAdapter.notifyDataSetChanged(views);
        indicator.setViewPager(viewpager);
        pageAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        initData();
        setRootView(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lead;
    }

    @Override
    public void initToolBar() {
    }

    public void skip(){
        startActivity(new Intent(LeadActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        skip();
        finish();
    }
}
