package com.youjuke.buildingmaterialmall.app.message_center;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.MessageDetailsEntity;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * <p>
 * 工程:消息中心详情
 * #0000    Tian Xiao    2016-11-21 17:04
 */
public class MessageDetailsActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar messageToolBar;
    private TextView messageToolBarTitle;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutMessageNull;
    private MessageDetailsAdapter adapter;
    private String type;
    private String title;
    private List<MessageDetailsEntity> messageDetailsList;
    private MessageDetailsEntity entity;

    private void assignViews() {
        messageToolBar = (Toolbar) findViewById(R.id.Message_tool_bar);
        messageToolBarTitle = (TextView) findViewById(R.id.Message_tool_bar_title);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutMessageNull = (LinearLayout) findViewById(R.id.linearLayout_message_null);
        messageToolBarTitle.setText(title);
        messageDetailsList = new ArrayList<>();
        adapter = new MessageDetailsAdapter(this, messageDetailsList,type);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        assignViews();
        initViewsMessageDetails();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_details;
    }

    /**
     * 请求详情数据
     */
    public void initViewsMessageDetails() {
        mDialog.showDialog();
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        params.put("type", type);
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.MESSATE_INFO, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.d("消息详情---" + responseBean.getData());
                                if (responseBean.getStatus().equals("200")) {

                                    messageDetailsList = gson.fromJson(responseBean.getData(),
                                            new TypeToken<List<MessageDetailsEntity>>() {
                                            }.getType());

                                    if (messageDetailsList.size() > 0) {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        showMessage();
                                    } else {
                                        linearLayoutMessageNull.setVisibility(View.VISIBLE);
                                    }

                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(MessageDetailsActivity.this, "" + responseBean.getMessage());
                                }
                                mDialog.dimissDialog();

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                L.d("消息详情 error---" +throwable);

                                ToastUtil.show(MessageDetailsActivity.this, "请求失败"+throwable);
                            }
                        })
        );

    }

    /**
     * 显示消息集合
     */
    private void showMessage() {
        adapter.addItemTop(messageDetailsList);
        adapter.notifyDataSetChanged();
        mDialog.dimissDialog();
    }


    @Override
    public void initToolBar() {
        setSupportActionBar(messageToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        messageToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    private void out() {
        finish();
        mDialog.dimissDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            out();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }
}
