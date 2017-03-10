package com.youjuke.buildingmaterialmall.app.schedule_details;

import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ScheduleDetails;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * 排期详情
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-21 17:10
 */
public class ScheduleDetailsActivity extends BaseActivity {

    private Toolbar toolBar;
    private TextView toolText;
    private ScheduleDetailsAdapter detailsAdapter;
    private RecyclerView recyclerView;
    private ScheduleDetails scheduleDetails = null;
    private LinearLayout linearLayoutSubscribe;
    private TextView textSubscribeTime;
    private String orderId;
    private String goodsId;
    private TextView textCpus;
    private TextView textTasks;

    private void assignViews() {

        textSubscribeTime = (TextView) findViewById(R.id.text_subscribe_time);
        linearLayoutSubscribe = (LinearLayout) findViewById(R.id.linearLayout_subscribe);
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        toolText = (TextView) findViewById(R.id.tool_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textCpus = (TextView) findViewById(R.id.text_Cpus);
        textTasks = (TextView) findViewById(R.id.text_Tasks);
        detailsAdapter = new ScheduleDetailsAdapter(scheduleDetails, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailsAdapter);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        orderId = getIntent().getStringExtra(ApiContstants.ORDER_ID);
        goodsId = getIntent().getStringExtra(ApiContstants.GOODS_ID);
        GetNodeTemplate();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_schedule_details;
    }

    @Override
    public void initToolBar() {
        toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolBar);
        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
//			mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void GetNodeTemplate() {
        mDialog.showDialog();
        params.clear();
        params.put(ApiContstants.USER_ID, BuildingMaterialApp.user.getId());
        params.put(ApiContstants.ORDER_ID, orderId);
        params.put(ApiContstants.GOODS_ID, goodsId);
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.GET_NODE_TEMPLATE, params).toJson())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {

                                       @TargetApi(Build.VERSION_CODES.N)
                                       @SuppressLint("SetTextI18n")
                                       @Override
                                       public void call(ResponseBean responseBean) {

                                           L.i("GetNodeTemplate" + responseBean.toString());

                                           if (responseBean.getStatus().equals("400")) {

                                               //不存在
                                               linearLayoutSubscribe.setVisibility(View.VISIBLE);
                                               recyclerView.setVisibility(View.GONE);
                                               textSubscribeTime.setText("专业人员正在对您所购买的商品");
                                               textSubscribeTime.setTextColor(Color.parseColor("#808080"));
                                               textTasks.setText("及购买地址等信息进行审核");
                                               textCpus.setText("审核成功后,系统自动显示订单进度哦");

                                           } else if (responseBean.getStatus().equals("200")) {

                                               scheduleDetails = gson.fromJson(responseBean.getData(),
                                                       new TypeToken<ScheduleDetails>() {
                                                       }.getType());

                                               if (scheduleDetails.getFllow_time() == null && scheduleDetails.getInfo().size() <= 0) {

                                                   linearLayoutSubscribe.setVisibility(View.VISIBLE);
                                                   recyclerView.setVisibility(View.GONE);
                                                   textSubscribeTime.setText("专业人员正在对您所购买的商品");
                                                   textSubscribeTime.setTextColor(Color.parseColor("#808080"));
                                                   textTasks.setText("及购买地址等信息进行审核");
                                                   textCpus.setText("审核成功后,系统自动显示订单进度哦");

                                               } else if (scheduleDetails.getFllow_time() != null
                                                       && scheduleDetails.getFirst_node_set_time() == null
                                                       ) {

                                                   linearLayoutSubscribe.setVisibility(View.VISIBLE);
                                                   recyclerView.setVisibility(View.GONE);

                                                   SpannableStringBuilder builder = new SpannableStringBuilder("暂约"
                                                           +
                                                           scheduleDetails.getFllow_time() + scheduleDetails.getFirst_node());
                                                   builder.setSpan(new ForegroundColorSpan(Color.parseColor("#f42e2f")),
                                                           2, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                                   textSubscribeTime.setText(builder);

                                               } else {

                                                   L.d("开始刷新");

                                                   linearLayoutSubscribe.setVisibility(View.GONE);
                                                   recyclerView.setVisibility(View.VISIBLE);
                                                   detailsAdapter.addAll(scheduleDetails);
                                                   detailsAdapter.notifyDataSetChanged();
                                               }
                                           }

                                           mDialog.dimissDialog();


                                       }

                                   }, new Action1<Throwable>() {

                                       @Override
                                       public void call(Throwable throwable) {

                                           throwable.printStackTrace();
                                           ToastUtil.show(ScheduleDetailsActivity.this, "抱歉获取数据出错，请稍后再试。");
                                           mDialog.dimissDialog();

                                       }

                                   }
                        )

        );
    }

}
