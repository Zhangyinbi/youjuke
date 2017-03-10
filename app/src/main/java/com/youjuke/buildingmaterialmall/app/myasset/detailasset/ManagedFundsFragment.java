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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.myasset.MyAssetActivity;
import com.youjuke.buildingmaterialmall.app.myasset.adapter.ManagedFundsAdapter;
import com.youjuke.buildingmaterialmall.entity.ManagedFundsEntity;
import com.youjuke.buildingmaterialmall.entity.MyAssetInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.FundsTimePickerView;
import com.youjuke.buildingmaterialmall.widgets.TimePickerView;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.youjuke.buildingmaterialmall.app.myasset.detailasset.RebateFragment.getTime;


/**
 * description 托管资金
 * Created by Administrator on 2016/12/16.
 * author zyb 2016/12/16--13:44
 */
public class ManagedFundsFragment extends BaseFragment implements View.OnClickListener {

    public ArrayList<MyAssetInfo.Contract> contractlist = new ArrayList<>();//合同数组
    private TextView tVCollocationMoney;
    private TextView tVRealityMoney;
    private RecyclerView recyclerView;
    private ManagedFundsAdapter adapter;
    private TextView tVFundsOne;
    private TextView tVFundsTwo;
    private LinearLayout llFunds;
    private ManagedFundsEntity fundsEntity;
    private boolean timeFlag;
    private List<ManagedFundsEntity.ProductBean> productBeanList;
    private FundsTimePickerView pvTime;
    private int fundsIndex = 0;
    private AppBarLayout appBarLayout;
    private LinearLayout llNoLayout;
    private MyAssetActivity myactivity;
    private TextView tvCall;
    public static ManagedFundsFragment newInstance() {
        return new ManagedFundsFragment();
    }

    private void assignViews() {
        myactivity = (MyAssetActivity) getActivity();

        appBarLayout = (AppBarLayout) parentView.findViewById(R.id.appbarLayout);

        llFunds = (LinearLayout) parentView.findViewById(R.id.ll_funds);
        llNoLayout = (LinearLayout) parentView.findViewById(R.id.ll_no_data);
        tvCall = (TextView) parentView.findViewById(R.id.tv_call);
        tvCall.setOnClickListener(this);
        tVFundsOne = (TextView) parentView.findViewById(R.id.tV_funds_one);
        tVFundsTwo = (TextView) parentView.findViewById(R.id.tV_funds_two);
        tVCollocationMoney = (TextView) parentView.findViewById(R.id.tV_collocation_money);
        tVRealityMoney = (TextView) parentView.findViewById(R.id.tV_reality_money);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recycler_view);
        if (contractlist.size() > 1) {
            llFunds.setVisibility(View.VISIBLE);
            tVFundsOne.setText(contractlist.get(0).getContract_type());
            tVFundsTwo.setText(contractlist.get(1).getContract_type());
        }
        tVFundsTwo.setOnClickListener(this);
        tVFundsOne.setOnClickListener(this);
        productBeanList = new ArrayList<>();
        adapter = new ManagedFundsAdapter(mContext, productBeanList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ManagedFundsEntity.ProductBean>() {
            @Override
            public void onItemClick(BaseRecyclerViewHolder view, int position, final ManagedFundsEntity.ProductBean model) {
                view.itemView.findViewById(R.id.button_verify).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPickerTime(model.getId(), model.getPlay_num_status());
                    }
                });
            }

            @Override
            public void onItemLongClick(BaseRecyclerViewHolder view, int position, ManagedFundsEntity.ProductBean model) {

            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if (contractlist.size() > 0) {
            getManagedFunds(Integer.parseInt(contractlist.get(fundsIndex).getId()));

            if (timeFlag) {
                L.d("设置合同日期");
                RxBus.get().post("showPickerTime", contractlist.get(0).getId());//弹出合同时间选择
            }
        }else {
            appBarLayout.setVisibility(View.GONE);
            llNoLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_manged_funds;
    }

    @Override
    public void finishCreateView(Bundle state) {
        contractlist = getArguments().getParcelableArrayList("contractList");
        timeFlag = getArguments().getBoolean("timeFlag", false);
        L.d("合同集合:" + contractlist.size() + "--- timeFlag:" + timeFlag);

        assignViews();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getManagedFunds(int contractId) {
        params.clear();
        params.put("contract_id", contractId);
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.MANAGED_FUNDS, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.d(responseBean.getData() + "托管");
                                if ("200".equals(responseBean.getStatus())) {
                                    fundsEntity = gson.fromJson(responseBean.getData(),
                                            new TypeToken<ManagedFundsEntity>() {
                                            }.getType());
                                    initActiviy();
                                } else if ("400".equals(responseBean.getStatus())) {
                                    ToastUtil.show(mContext, "" + responseBean.getMessage());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                            }
                        })
        );
    }

    /**
     * 初始化数据
     */
    private void initActiviy() {
        tVCollocationMoney.setText(fundsEntity.getSj_tg_money());
        tVRealityMoney.setText(fundsEntity.getSj_money_use());
        productBeanList = fundsEntity.getProduct();
        adapter.addItemTop(productBeanList);
        adapter.notifyDataSetChanged();
        //fundsIndex
        if (fundsIndex==0){
            initAppBar("托管资金管理",contractlist.get(0).getContract_type());
        }else if(fundsIndex==1){
            initAppBar("托管资金管理",contractlist.get(1).getContract_type());
        }

    }



    /**
     * @param newTitle 需要显示的新的title
     */
    private void initAppBar(final String oldTitle, final String newTitle) {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float v = 1 - (float) (Math.abs(verticalOffset)) / (float) (appBarLayout.getTotalScrollRange() / 2);
                float v1 = (float) (Math.abs(verticalOffset)) / (float) (appBarLayout.getTotalScrollRange() / 2) - 1;
                if (verticalOffset == 0) {
                    if (state != ManagedFundsFragment.CollapsingToolbarLayoutState.EXPANDED) {
                        state = ManagedFundsFragment.CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        myactivity.getTvTitle().setAlpha(1);
                        myactivity.getTvTitle().setTextScaleX(1);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != ManagedFundsFragment.CollapsingToolbarLayoutState.COLLAPSED) {
                        myactivity.getTvTitle().setAlpha(1);
                        myactivity.getTvTitle().setTextScaleX(1);
                        state = ManagedFundsFragment.CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state == ManagedFundsFragment.CollapsingToolbarLayoutState.COLLAPSED) {
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
                    if (state == ManagedFundsFragment.CollapsingToolbarLayoutState.EXPANDED) {//打开到折叠
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




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tV_funds_one://合同一
                tVFundsOne.setTextColor(getResources().getColor(R.color.text_select));
                tVFundsTwo.setTextColor(getResources().getColor(R.color.text_normal));
                if (fundsIndex == 1) {
                    fundsIndex = 0;
                    getManagedFunds(Integer.parseInt(contractlist.get(fundsIndex).getId()));
                }
                break;
            case R.id.tV_funds_two://合同二
                tVFundsOne.setTextColor(getResources().getColor(R.color.text_normal));
                tVFundsTwo.setTextColor(getResources().getColor(R.color.text_select));
                if (fundsIndex == 0) {
                    fundsIndex = 1;
                    getManagedFunds(Integer.parseInt(contractlist.get(fundsIndex).getId()));
                }
                break;
            case R.id.tv_call:
                callPhone();
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

    public void showPickerTime(final String id, final String status) {
        L.d("开始选择日期");
        //时间选择器
        if (pvTime == null) {
            pvTime = new FundsTimePickerView(mContext);
        }
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.YEAR) + 10);//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                agreePayment(id, status, getTime(date));
            }

            @Override
            public void onTimeNotSetting() {
            }
        });
        //弹出时间选择器
        pvTime.show();
    }

    /**
     * 确认付款
     *
     * @param id
     * @param status
     * @param time
     */
    private void agreePayment(String id, String status, String time) {

        params.clear();
        params.put("id", id);
        params.put("cs_yz_confirm_date", time);
        params.put("play_num_status", status);

        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.AGREE_PAYMENT, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if ("200".equals(responseBean.getStatus())) {
                                    ToastUtil.show(mContext, responseBean.getMessage());
                                    getManagedFunds(Integer.parseInt(contractlist.get(fundsIndex).getId()));
                                } else if ("400".equals(responseBean.getStatus())) {
                                    ToastUtil.show(mContext, responseBean.getMessage());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                            }
                        })
        );
    }

    /**
     * 设施合同日期后刷新
     *
     * @param contractId
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("fundsRefresh")
            }
    )
    public void fundsRefresh(Integer contractId) {
        getManagedFunds(contractId);
    }
    private CollapsingToolbarLayoutState state;
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
    }


}
