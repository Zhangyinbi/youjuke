package com.youjuke.buildingmaterialmall.app.myasset.detailasset;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.myasset.MyAssetActivity;
import com.youjuke.buildingmaterialmall.app.myasset.adapter.BillAdapter;
import com.youjuke.buildingmaterialmall.entity.BillInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.CustomProgressDialog;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * description 账单流水详情
 * Created by Administrator on 2016/12/16.
 * author zyb 2016/12/16--13:44
 */
public class BillDetailFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvCall;
    private LinearLayout ll_no_data;//有数据消失显示ViewStub
    private RecyclerView recyclerView;
    private TextView tvHostMoney;//托管金额
    private TextView tvRealUseMoney;//实际使用金额
    private TextView tvRebateCycle;//返利周期点数
    private TextView tvRealEarnings;//实际收益
    private TextView tvReturnTheBalance;//退换余款
    private BillAdapter billAdapter;
    ArrayList<BillInfo.Bill> datas = new ArrayList<BillInfo.Bill>();
    private String contractId;
    private BillInfo billInfo;//
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bill_detail;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    private void initView() {
        contractId=((MyAssetActivity) getActivity()).getContractId();
        tvCall = $(R.id.tv_call);
        tvCall.setOnClickListener(this);
        ll_no_data = $(R.id.ll_no_data);
        getDataFromNet();
    }

    /**
     * 访问网络 请求流水数据
     */
    private void getDataFromNet() {
        params.clear();
        params.put("contract_id", contractId);
        ((MyAssetActivity)getActivity()).showProgressDialog();
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.SETTLEMENT_BILL, params).toJson())
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
                            billInfo = gson.fromJson(responseBean.getData(),BillInfo.class);
                            datas=billInfo.getProduct();
                            initData();
                        }else if ("400".equals(responseBean.getStatus())){
                            ToastUtil.show(mContext,responseBean.getMessage());
                            ll_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                });
        compositeSubscription.add(subscribe);


    }

    /**
     * 初始化viewstub并初始化数据
     */
    private void initData() {
          //如果网络访问成功并有数据
        ll_no_data.setVisibility(View.GONE);
        ViewStub viewStub = $(R.id.vs_viewStub);
        View view = viewStub.inflate();
        tvHostMoney = (TextView) view.findViewById(R.id.tv_host_money);
        tvHostMoney.setText(MoneySimpleFormat.getSimpleType(billInfo.getSj_tg_money()));
        tvRealUseMoney = (TextView) view.findViewById(R.id.tv_real_use_money);
        tvRealUseMoney.setText(MoneySimpleFormat.getSimpleType(billInfo.getSj_money_use()));
        tvRebateCycle = (TextView) view.findViewById(R.id.tv_rebate_cycle);
        tvRebateCycle.setText(billInfo.getPeriods()+"/"+billInfo.getFd_rate());
        tvRealEarnings = (TextView) view.findViewById(R.id.tv_real_earnings);
        tvRealEarnings.setText(MoneySimpleFormat.getSimpleType(billInfo.getSj_gross_income()));
        tvReturnTheBalance = (TextView) view.findViewById(R.id.tv_return_the_balance);
        tvReturnTheBalance.setText(MoneySimpleFormat.getSimpleType(billInfo.getCancel_money()));
        recyclerView = (RecyclerView) view.findViewById(R.id.rl_recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        billAdapter = new BillAdapter(mContext, datas);
        recyclerView.setAdapter(billAdapter);
    }

    public static BillDetailFragment newInstance() {
        return new BillDetailFragment();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_call:
                callPhone();
                break;
        }

    }
}