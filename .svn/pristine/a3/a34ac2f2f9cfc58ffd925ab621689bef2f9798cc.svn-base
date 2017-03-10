package com.youjuke.buildingmaterialmall.app.myasset;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.WrapActivity;
import com.youjuke.buildingmaterialmall.app.myasset.detailasset.BillDetailFragment;
import com.youjuke.buildingmaterialmall.app.myasset.detailasset.ManagedFundsFragment;
import com.youjuke.buildingmaterialmall.app.myasset.detailasset.RebateFragment;
import com.youjuke.buildingmaterialmall.app.myasset.detailasset.WalletBalanceFragment;
import com.youjuke.buildingmaterialmall.entity.MyAssetInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.TimePickerView;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.StatusBarCompat;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static com.youjuke.buildingmaterialmall.app.myasset.detailasset.RebateFragment.getTime;

/**
 * description 我的资金管理
 * Created by Administrator on 2016/12/16.
 * author zyb 2016/12/16--13:44
 */

public class MyAssetActivity extends WrapActivity {

    public Toolbar myAssetToolBar;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    private String  balance;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    private String contractId;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    private TextView tvTitle;
    private FrameLayout flContent;
    private int type;
    private ArrayList<Fragment> fragments=new ArrayList<>();
    private String[] titles;
    public ArrayList<MyAssetInfo.Contract> contractlist =new ArrayList<>();//合同数组
    public boolean timeFlag;//是否已经设置开工时间
    @Override
    public void initViews(Bundle savedInstanceState) {
        titles = new String[]{getString(R.string.manage_founds),getString(R.string.decorate_rebate),getString(R.string.wallet_balance_manage),getString(R.string.bill_detail_manage)};
        initFragment();
        assignViews();
        setRootView(this);
    }

    private void assignViews() {
        myAssetToolBar = (Toolbar) findViewById(R.id.tb_my_asset_bar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        type = getIntent().getIntExtra("title", -1);
        timeFlag=getIntent().getBooleanExtra("flag",true);
        balance=getIntent().getStringExtra("balance");
        contractlist = getIntent().getParcelableArrayListExtra("contractlist");
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        showFragment(type,false);

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        fragments.add(ManagedFundsFragment.newInstance());
        fragments.add(RebateFragment.newInstance());
        fragments.add(WalletBalanceFragment.newInstance());
        fragments.add(BillDetailFragment.newInstance());
    }

    /**
     * @param type 显示那个fragment
     */
    public void showFragment(int type,boolean flag) {
        this.type=type;
        tvTitle.setText(titles[type]);
        //传进来的fragment根据需要携带参数
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (type==3){
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        if (type==0){
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("contractList",contractlist);
            bundle.putBoolean("timeFlag",timeFlag);
            fragments.get(type).setArguments(bundle);
        }
        ft.replace(R.id.fl_content, fragments.get(type));
        if (flag) {//是否加入回退站
            ft.addToBackStack(null);
        }

        ft.commitAllowingStateLoss();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_asset;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(myAssetToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        myAssetToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type==3){
                    type=1;
                    tvTitle.setText(titles[type]);
                    getSupportFragmentManager().popBackStack();
                    return;
                }
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type==3){
            type = 1;
            tvTitle.setText(titles[type]);
            getSupportFragmentManager().popBackStack();
            return false;
        }else if (type==1){
             pvTime = ((RebateFragment) fragments.get(type)).pvTime;

        }
        if (pvTime!=null&&pvTime.isShowing()){
            pvTime.dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private TimePickerView pvTime;

    /**
     * 弹出时间选择器
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("showPickerTime")
            }
    )
    public void showPickerTime(final String contractId) {
        L.d("开始选择日期");
        //时间选择器
        if (pvTime == null) {
            pvTime = new TimePickerView(this);
        }
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.YEAR) + 10);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCancelable(true);
        pvTime.setCyclic(false);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                ToastUtil.show(MyAssetActivity.this, getTime(date));//传数据给后台，后台会返回数据重新刷新adapter
                setStartTime(getTime(date),contractId);//设置开工时间
            }

            @Override
            public void onTimeNotSetting() {

            }
        });
        //弹出时间选择器
        pvTime.show();
    }

    /**
     * 设置开工时间
     */
    private void setStartTime(String time,String contractId) {
        params.clear();
        showProgressDialog();
        params.put("id",contractId);
        params.put("baoming_id", BuildingMaterialApp.user.getBaoming_id());
        params.put(ApiContstants.MOBIlE,BuildingMaterialApp.user.getMobile());
        params.put("yjdk_start_time",time.trim());
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.PLAY_MONEY_SET_STARTTIME, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dimissDialog();
                        ToastUtil.show(MyAssetActivity.this,"设置开工时间失败，请重新设置");
                        pvTime.show();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            RxBus.get().post("fundsRefresh","contractId");
                        }else if ("400".equals(responseBean.getStatus())){
                            ToastUtil.show(MyAssetActivity.this,responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

}
