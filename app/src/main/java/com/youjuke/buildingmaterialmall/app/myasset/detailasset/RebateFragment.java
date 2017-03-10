package com.youjuke.buildingmaterialmall.app.myasset.detailasset;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.myasset.MyAssetActivity;
import com.youjuke.buildingmaterialmall.app.myasset.adapter.RebateAdapter;
import com.youjuke.buildingmaterialmall.entity.MyAssetInfo;
import com.youjuke.buildingmaterialmall.entity.RebateInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.TimePickerView;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * description 返利
 * Created by Administrator on 2016/12/16.
 * author zyb 2016/12/16--13:44
 */

public class RebateFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvBillDetail;
    private TextView tvExpectEarnings;//预期收益
    private String expectEarnings;
    private TextView tvRealEarnings;//实际收益
    private String realEarnings;
    private TextView tvCommenceData;//提醒mesg
    private boolean commencedata;
    private TextView tvCall;//客服电话
    private LinearLayout ll_no_data;//有数据消失显示ViewStub
    private RecyclerView recyclerView;
    private RebateAdapter adapter;
    boolean isGetDataFromNet = true;
    public TimePickerView pvTime;
    private ArrayList<RebateInfo.ProductInfo> datas = new ArrayList<RebateInfo.ProductInfo>();
    private MyAssetActivity myactivity;
    private String newTitle;
    private AppBarLayout app_bar_layout;
    private MyAssetActivity activity;
    private String contractId;
    private RebateInfo rebateInfo;
    private RebateInfo rebateInfo1;
    public ArrayList<MyAssetInfo.Contract> contractlist;
    private TextView tv_contract_one;
    private TextView tv_contract_two;
    private boolean flag;//viewStub是否已经被绘制
    int count = 0;//选的第一个还是第二个

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rebate;
    }

    @Override
    public void finishCreateView(Bundle state) {
        activity = (MyAssetActivity) getActivity();
        contractlist = activity.contractlist;
        initview();
    }

    /**
     * 初始化控件并处理逻辑
     */
    private void initview() {
        tvCall = $(R.id.tv_call);
        tvCall.setOnClickListener(this);
        ll_no_data = $(R.id.ll_no_data);
        myactivity = (MyAssetActivity) getActivity();
        if (contractlist.size() != 0) {//如果有合同再判断有没有开工时间
            contractId = contractlist.get(0).getId();
            if (isGetDataFromNet) {
                getdataFromNet(contractId);
                if (activity.timeFlag) {//如果没有开工时间
                    showPickerTime();
                }
            } else {
                if (!flag) {
                    initView1();
                }
                if (count == 0) {
                    initData(rebateInfo);
                } else if (count == 1) {
                    initData(rebateInfo1);
                }
            }
        }else {
            ll_no_data.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求合同数据
     */
    private void getdataFromNet(final String contractId) {

        ((MyAssetActivity)getActivity()).showProgressDialog();
        params.clear();
        params.put("contract_id", contractId);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.MY_REBATE, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(mContext,getString(R.string.please_check_the_network_or_try_again_later));
                        ll_no_data.setVisibility(View.VISIBLE);
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            if (count == 0) {
                                rebateInfo = gson.fromJson(responseBean.getData(), RebateInfo.class);
                                if (flag){
                                    initData(rebateInfo);
                                }
                            } else if (count == 1) {
                                rebateInfo1 = gson.fromJson(responseBean.getData(), RebateInfo.class);
                                initData(rebateInfo1);
                            }
                            if (!flag) {
                                initView1();
                                initData(rebateInfo);
                            }
                        }else if ("400".equals(responseBean.getStatus())){
                            ToastUtil.show(mContext,responseBean.getMessage());
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });
        compositeSubscription.add(subscribe);


    }

    /**
     * 初始化viewStub
     */
    private void initView1() {
        ViewStub viewStub = $(R.id.vs_viewStub);
        View view = viewStub.inflate();
        flag = true;
        tv_contract_one = (TextView) view.findViewById(R.id.tv_contract_one);
        tv_contract_one.setOnClickListener(this);
        tv_contract_two = (TextView) view.findViewById(R.id.tv_contract_two);
        tv_contract_two.setOnClickListener(this);
        tvExpectEarnings = (TextView) view.findViewById(R.id.tv_expect_earnings);
        tvRealEarnings = (TextView) view.findViewById(R.id.tv_real_earnings);
        app_bar_layout = (AppBarLayout) view.findViewById(R.id.app_bar_layout);
        tvBillDetail = (TextView) view.findViewById(R.id.tv_bill_detail);
        tvBillDetail.setOnClickListener(this);
        tvRealEarnings = (TextView) view.findViewById(R.id.tv_real_earnings);
        tvCommenceData = (TextView) view.findViewById(R.id.tv_commence_data);
        recyclerView = (RecyclerView) view.findViewById(R.id.rl_recycler_view);
        if (contractlist.size() == 1) {
            tv_contract_one.setText(contractlist.get(0).getContract_type());
            tv_contract_two.setVisibility(View.GONE);
        } else if (contractlist.size() == 2) {
            tv_contract_one.setText(contractlist.get(0).getContract_type());
            tv_contract_two.setText(contractlist.get(1).getContract_type());
        }
        newTitle = contractlist.get(0).getContract_type();
        initAppBar("我的返利", newTitle);
    }

    /**
     * 初始化数据
     */
    private void initData(RebateInfo rebateInfo) {
        if (count == 0) {
            tv_contract_one.setTextColor(getResources().getColor(R.color.text_select));
            tv_contract_two.setTextColor(getResources().getColor(R.color.text_normal));
            newTitle = contractlist.get(0).getContract_type();

            initAppBar("我的返利", newTitle);
        } else if (count == 1) {
            newTitle = contractlist.get(1).getContract_type();

            tv_contract_two.setTextColor(getResources().getColor(R.color.text_select));
            tv_contract_one.setTextColor(getResources().getColor(R.color.text_normal));
            initAppBar("我的返利", newTitle);
        }
        expectEarnings = rebateInfo.getYj_gross_income();
        realEarnings = rebateInfo.getSj_gross_income();
        tvCommenceData.setText(rebateInfo.getImp_msg());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (rebateInfo.getProduct()!=null)
        datas = rebateInfo.getProduct();
        adapter = new RebateAdapter(mContext, datas);
        adapter.setOnGetRebateListener(new RebateAdapter.OnGetRebateListener() {
            @Override
            public void onGetListener(int position) {
                getRebate(position);
            }
        });
        recyclerView.setAdapter(adapter);
        tvExpectEarnings.setText(MoneySimpleFormat.getSimpleType(expectEarnings));
        tvRealEarnings.setText(MoneySimpleFormat.getSimpleType(realEarnings));
    }

    /**
     * @param position 点击的item
     * 领取返利
     */
    private void getRebate(final int position) {
        params.clear();
        params.put("id", datas.get(position).getId());
        params.put("contract_id", contractId);
        params.put("mobile", BuildingMaterialApp.user.getMobile());
        params.put("baoming_id",BuildingMaterialApp.user.getBaoming_id());
        ((MyAssetActivity)getActivity()).showProgressDialog();
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.RECEIVE_THE_REBATE, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(mContext, "领取失败，请重新领取");
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            datas.get(position).setButton_status(2);
                            datas.get(position).setLq_time(getTime(new Date()));
                            adapter.notifyDataSetChanged();
                        }else if ("400".equals(responseBean.getStatus())){
                            ToastUtil.show(mContext,responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * @param newTitle 需要显示的新的title
     */
    private void initAppBar(final String oldTitle, final String newTitle) {
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float v = 1 - (float) (Math.abs(verticalOffset)) / (float) (appBarLayout.getTotalScrollRange() / 2);
                float v1 = (float) (Math.abs(verticalOffset)) / (float) (appBarLayout.getTotalScrollRange() / 2) - 1;
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        myactivity.getTvTitle().setAlpha(1);
                        myactivity.getTvTitle().setTextScaleX(1);
                    }
                    tvBillDetail.setEnabled(true);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        myactivity.getTvTitle().setAlpha(1);
                        myactivity.getTvTitle().setTextScaleX(1);
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    tvBillDetail.setEnabled(false);
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        //由折叠变为中间状态时
                        if (Math.abs(verticalOffset) < appBarLayout.getTotalScrollRange() / 2) {
                            myactivity.getTvTitle().setText(oldTitle);
                            myactivity.getTvTitle().setTextScaleX(v);
                            myactivity.getTvTitle().setAlpha(v);//0-1
                        } else {
                            myactivity.getTvTitle().setText(newTitle);
                            myactivity.getTvTitle().setTextScaleX(v1);
                            myactivity.getTvTitle().setAlpha(v1);//1-0
                        }
                    }
                    if (state == CollapsingToolbarLayoutState.EXPANDED) {//打开到折叠
                        if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() / 2) {
                            myactivity.getTvTitle().setText(newTitle);
                            myactivity.getTvTitle().setTextScaleX(v1);
                            myactivity.getTvTitle().setAlpha(v1);//0-1
                        } else {
                            myactivity.getTvTitle().setText(oldTitle);
                            myactivity.getTvTitle().setTextScaleX(v);
                            myactivity.getTvTitle().setAlpha(v);//1-0
                        }
                    }
                }
            }

        });
    }

    /**
     * 弹出时间选择器
     */
    private void showPickerTime() {
        //时间选择器
        if (pvTime==null){
            pvTime = new TimePickerView(mContext);
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
                /*ToastUtil.show(mContext, getTime(date));*///传数据给后台，后台会返回数据重新刷新adapter
                setStartTime(getTime(date));//设置开工时间
            }

            @Override
            public void onTimeNotSetting() {//点击不设置
            }
        });

        //弹出时间选择器
        pvTime.show();
    }

    /**
     * 设置开工时间
     */
    private void setStartTime(String time) {
        params.clear();
        params.put("id", contractId);
        params.put("baoming_id", BuildingMaterialApp.user.getBaoming_id());
        params.put(ApiContstants.MOBIlE,BuildingMaterialApp.user.getMobile());
//        params.put("baoming_id", "5563");
        params.put("yjdk_start_time", time.trim());
        ((MyAssetActivity)getActivity()).showProgressDialog();
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.PLAY_MONEY_SET_STARTTIME, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(mContext, "设置开工时间失败，请重新设置");
                        showPickerTime();
                        ((MyAssetActivity)getActivity()).dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            getdataFromNet(contractId);//请求数据
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(mContext, responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    public static RebateFragment newInstance() {
        return new RebateFragment();
    }

    /**
     * @param v 被点击的view
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bill_detail:
                isGetDataFromNet = false;
                flag = false;
                ((MyAssetActivity) getActivity()).setContractId(contractId);
                ((MyAssetActivity) getActivity()).showFragment(3, true);
                break;
            case R.id.tv_call:
                callPhone();
                break;
            case R.id.tv_contract_one:
                count = 0;
                contractId = contractlist.get(0).getId();
                if (rebateInfo != null)
                    initData(rebateInfo);
                break;
            case R.id.tv_contract_two:
                count = 1;
                contractId = contractlist.get(1).getId();
                if (rebateInfo1 != null) {
                    initData(rebateInfo1);
                } else {
                    getdataFromNet(contractId);
                }
                break;
        }
    }


    /**
     * 拨打电话
     */
    private void callPhone() {
        new AlertDialog.Builder(mContext)
                .setTitle("确认拨打客服电话吗?")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //拨打电话
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvCall.getText().toString().trim())));
                    }
                }).setPositiveButton("取消", null).show();
    }

    /**
     * @param date 需要格式化的日期
     * @return 格式化之后的日期
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.dialogdissmiss();
        }
    }

    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
    }

}
