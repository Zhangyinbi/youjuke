package com.youjuke.buildingmaterialmall.app.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.home.TabEntity;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.widgets.LazyViewPager;

import java.util.ArrayList;

public class OrderActivity extends BaseActivity {

    private Toolbar orderToolBar;
    private CommonTabLayout orderTabs;
    private FrameLayout flFragmentContainer;
    private LazyViewPager view_page_order;
    private SwipeRefreshLayout swipe_refresh_layout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String titles[] = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;
    private int status_id = 0;

    //1	待付款
    //2	待发货
    //3	待收货
    //4	待评价
    //5	已取消


    private void assignViews() {
        orderToolBar = (Toolbar) findViewById(R.id.order_tool_bar);
        orderTabs = (CommonTabLayout) findViewById(R.id.order_tabs);
        flFragmentContainer = (FrameLayout) findViewById(R.id.fl_fragment_container);
        view_page_order = (LazyViewPager) findViewById(R.id.view_page_order);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.holo_blue_light);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        Intent intent = getIntent();
        status_id = intent.getIntExtra("status_id", 0);
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(OrderFragment.getInstance(i));
            mTabEntities.add(new TabEntity(titles[i], 0, 0));
        }
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        view_page_order.setAdapter(myPagerAdapter);
        orderTabs.setTabData(mTabEntities);
        //加载逻辑在OrderFragment里的onResume
        orderTabs.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                view_page_order.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        view_page_order.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                orderTabs.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view_page_order.setCurrentItem(status_id);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RxBus.get().post("getOrderList", status_id + "");
            }
        });
    }

    /**
     * 停止刷新
     */
    @Subscribe(tags = @Tag("stopRefresh"))
    public void stopRefresh(String s) {
        if (swipe_refresh_layout.isRefreshing()) {
            swipe_refresh_layout.setRefreshing(false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initToolBar() {
        orderToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fm.beginTransaction().commitAllowingStateLoss();
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
