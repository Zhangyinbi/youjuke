package com.youjuke.buildingmaterialmall.app.balance.balancedetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.balance.BalanceDetailActivity;
import com.youjuke.buildingmaterialmall.entity.BalanceDetailInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseFragment;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static me.everything.android.ui.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;

/**
 * Created by Administrator on 2016/12/30.
 */

public class InComeFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private ArrayList<BalanceDetailInfo> datas = new ArrayList();
    private ImageView ivNoDetail;
    private TextView tvHintContent;
    BalanceDetailActivity balanceDetailActivity;

    public InComeFragment(BalanceDetailActivity balanceDetailActivity) {
        super();
        this.balanceDetailActivity = balanceDetailActivity;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wallet_balance_detail;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    private void initView() {
        recyclerView = $(R.id.rlv_recyclerView);
        ivNoDetail=$(R.id.iv_no_detail);
        tvHintContent=$(R.id.tv_content_hint);
        adapter = new DetailAdapter(mContext, datas, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, ORIENTATION_VERTICAL);
    }

    boolean isSuccess;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//加载网络，数据返回成功之后设置一个Tag以后不再加载数据
            if (!isSuccess)
                getData();
        }
    }

    private void getData() {
        params.clear();
        balanceDetailActivity.showProgressDialog();
        params.put("baoming_id", BuildingMaterialApp.user.getBaoming_id());
        params.put("mobile", BuildingMaterialApp.user.getMobile());
        params.put("is_type", 1);
        RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.INCOME_OUTCOME_DETAIL, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        balanceDetailActivity.dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        balanceDetailActivity.dimissDialog();
                        tvHintContent.setText(R.string.it_is_failed_to_request_net_please_try_again_later);
                        tvHintContent.setVisibility(View.VISIBLE);
                        ivNoDetail.setVisibility(View.VISIBLE);
                        ivNoDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getData();
                            }
                        });
                    }
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        isSuccess = true;
                        if ("200".equals(responseBean.getStatus())) {
                            recyclerView.setVisibility(View.VISIBLE);
                            datas = gson.fromJson(responseBean.getData(), new TypeToken<ArrayList<BalanceDetailInfo>>() {
                            }.getType());
                            adapter.setdata(datas);
                            adapter.notifyDataSetChanged();
                        } else if ("400".equals(responseBean.getStatus())) {
                            tvHintContent.setText(R.string.no_income_detail);
                            tvHintContent.setVisibility(View.VISIBLE);
                            ivNoDetail.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
