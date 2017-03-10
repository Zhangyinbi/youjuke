package com.youjuke.buildingmaterialmall.app.message_center;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.MessageCenterEntity;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * 消息通知
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-14 15:03
 */
public class MessageCenterActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar MessageToolBar;
    private RecyclerView recyclerViewMessage;
    private LinearLayout linearLayoutMessageNull;
    private List<MessageCenterEntity> messageList;
    private MessageCenterAdapter centerAdapter;

    private void assignViews() {
        MessageToolBar = (Toolbar) findViewById(R.id.Message_tool_bar);
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recycler_view_message);
        linearLayoutMessageNull = (LinearLayout) findViewById(R.id.linearLayout_message_null);
        messageList=new ArrayList<>();
        centerAdapter=new MessageCenterAdapter(this,messageList);
        recyclerViewMessage.setLayoutManager(new LinearLayoutManager(MessageCenterActivity.this));
        recyclerViewMessage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMessage.setAdapter(centerAdapter);
        //noinspection unchecked
        centerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecyclerViewHolder view, final int position, Object model) {

                view.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MessageCenterActivity.this, MessageDetailsActivity.class);
                        intent.putExtra("type",messageList.get(position).getType()+"");
                        intent.putExtra("title",messageList.get(position).getTitle()+"");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onItemLongClick(BaseRecyclerViewHolder view, int position, Object model) {

            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        if (BuildingMaterialApp.user != null) {
            initViewsMessage();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    /**
     * 初始化message
     */
    private void initViewsMessage() {

        mDialog.showDialog();
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());

       compositeSubscription.add( RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.MESSATE_TITLE, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        L.d(responseBean.getData());
                        if (responseBean.getStatus().equals("200")){
                            messageList = gson.fromJson(responseBean.getData(), new TypeToken<List<MessageCenterEntity>>() {
                            }.getType());
                            L.d(messageList.get(1).getTitle()+"这是第2个标题");
                            if (messageList.size()>0){
                                showMessage();//显示获取的纤细
                            }else {
                                linearLayoutMessageNull.setVisibility(View.VISIBLE);
                            }
                        }else {
                            linearLayoutMessageNull.setVisibility(View.VISIBLE);
                        }
                        mDialog.dimissDialog();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mDialog.dimissDialog();
                        ToastUtil.show(MessageCenterActivity.this,"网络请求失败");
                    }
                })
       );
    }

    /**
     * 显示获取的信息
     */
    private void showMessage() {
        recyclerViewMessage.setVisibility(View.VISIBLE);
        centerAdapter.addItemTop(messageList);
        centerAdapter.notifyDataSetChanged();
    }


    @Override
    public void initToolBar() {
        setSupportActionBar(MessageToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        MessageToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    private void out() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            out();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
