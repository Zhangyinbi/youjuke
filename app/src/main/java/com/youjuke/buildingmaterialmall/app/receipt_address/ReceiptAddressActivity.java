package com.youjuke.buildingmaterialmall.app.receipt_address;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.receipt_address_compile.ReceiptAddressCompileActivity;
import com.youjuke.buildingmaterialmall.entity.AddressList;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 描述:
 * 收货地址页面
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-18 14:52
 */
public class ReceiptAddressActivity extends BaseActivity implements View.OnClickListener {


    private Toolbar toolBar;
    private RecyclerView recyclerView;
    private AppCompatButton button;
    private ReceiptAddressAdapter adapter;
    private List<AddressList> addressLists;
    private TextView tool_text;

    private int fromActivity;//从哪个activity进来的
    private String order_id;
    private Boolean deleting;//判断是否正在请求删除

    private void assignViews() {

        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        button = (AppCompatButton) findViewById(R.id.button);
        button.setOnClickListener(this);
        tool_text = (TextView) findViewById(R.id.tool_text);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        assignViews();
        fromActivity = getIntent().getIntExtra("fromActivity", 0);
        order_id = getIntent().getStringExtra("order_id");
        //如果从TakeOrderActivity(生成订单)进来的
        if (fromActivity == 2) {
            tool_text.setText("选择收货地址");
        }
        addressLists = new ArrayList<>();
        initializeRecyclerView();

        //请求数据
        getAddress(0, 0);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_receipt_address;
    }

    @Override
    public void initToolBar() {

        toolBar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolBar);
        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
//			mActionBar.setDisplayUseLogoEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            out();
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出
     */
    public void out() {
        this.finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button:
                Intent intent = new Intent(ReceiptAddressActivity.this, ReceiptAddressCompileActivity.class);
                // 1为添加
                intent.putExtra("siwtch", 1);

                startActivityForResult(intent, 1);
                break;

        }

    }

    /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1 && requestCode == 1) {

            getAddress(0, 1);

        } else if (requestCode == 0 && resultCode != 0) {
            getAddress(resultCode, 3);
            adapter = null;
        }

    }

    /**
     * 请求数据
     * method 1:增加 2:删除 3:编辑更新
     */
    public void getAddress(final int position, final int method) {
        mDialog.showDialog();
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.ADDRESS_LIST, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.e("getAddress返回数据 ：" + responseBean.toString());
                                addressLists = gson.fromJson(responseBean.getData(), new TypeToken<List<AddressList>>() {
                                }.getType());
                                mDialog.dimissDialog();
                                if (adapter == null) {
                                    //初始化adapter
                                    initializeRecyclerView();
                                } else if (method == 0||method == 1|method == 2) {
                                    DiffUtil.DiffResult diffResult= DiffUtil.calculateDiff(
                                            new ReceiptAddressCallBack(adapter.getDatas(),addressLists ),true);
                                    diffResult.dispatchUpdatesTo(adapter);
                                    adapter.addItemTop(addressLists);
                                } else if (method == 3) {
                                    //初始化adapter
                                    initializeRecyclerView();
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();
                            }
                        }));
    }


    /**
     * 初始化adapter
     */
    private void initializeRecyclerView() {

        adapter = new ReceiptAddressAdapter(ReceiptAddressActivity.this, addressLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReceiptAddressActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<AddressList>() {

            @Override
            public void onItemClick(BaseRecyclerViewHolder view, final int position, final AddressList model) {

                view.itemView.findViewById(R.id.ll_address_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //如果从TakeOrderActivity(生成订单)进来的
                        if (fromActivity == 2) {
                            //选择更新订单地址
                            updateOrderAddress(position, model, order_id);
                        }
                    }
                });
                view.itemView.findViewById(R.id.linearlayout_delete)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                new AlertDialog.Builder(ReceiptAddressActivity.this)
                                        .setTitle("确认删除吗?")
                                        .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //删除请求

                                                deleteAdress(position);


                                            }
                                        }).setPositiveButton("取消", null).setCancelable(false).show();
                            }
                        });

                view.itemView.findViewById(R.id.linearlayout_compile)
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ReceiptAddressActivity.this
                                        , ReceiptAddressCompileActivity.class);

                                intent.putExtra("refresh", Integer.valueOf(addressLists.get(position).getId()));
                                startActivityForResult(intent, 0);
                            }

                        });
                view.itemView.findViewById(R.id.linearLayout_set_default)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //设置默认地址
                                setDefaultAddress(position);

                            }
                        });

            }

            @Override
            public void onItemLongClick(BaseRecyclerViewHolder view, int position, AddressList model) {

            }
        });
    }

    /**
     * 设置默认地址请求
     *
     * @param position
     */
    private void setDefaultAddress(int position) {
        mDialog.showDialog();
        params.clear();
        params.put(ApiContstants.U_ID, BuildingMaterialApp.user.getId());
        params.put(ApiContstants.ID, addressLists.get(position).getId());
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.SET_DEFAULT, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                L.d("这是默认地址" + responseBean.toString());

                                adapter = null;
                                getAddress(0, 0);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        }));
    }

    /**
     * 删除请求
     */
    int deleteIndex;

    private void deleteAdress(int position) {
        deleteIndex = position;
        mDialog.showDialog();
        params.clear();
        params.put(ApiContstants.U_ID, BuildingMaterialApp.user.getId());
        AddressList addressList = addressLists.get(position);
        params.put(ApiContstants.ID, addressList.getId());
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.ADDRESS_DELETE, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<ResponseBean>() {

                            @Override
                            public void call(ResponseBean responseBean) {

                                //error='0', status='200', message='操作成功', data='true'
                                if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ReceiptAddressActivity.this, responseBean.getMessage());
                                    mDialog.dimissDialog();
                                } else if (responseBean.getStatus().equals("200")) {

                                    //ToastUtil.show(ReceiptAddressActivity.this,"删除成功");
                                    //重新获取
                                    getAddress(deleteIndex, 2);
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();

                            }
                        }));
    }

    //订单选择更改地址
    public void updateOrderAddress(int position, final AddressList addressList, String order_id) {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order_id);
        params.put("consignee", addressList.getReceive_name());
        params.put("consignee_contact", addressList.getMobile());
        params.put("accept_address", addressList.getAddress());
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.UPDATE_ORDER, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.show(ReceiptAddressActivity.this, "更改地址失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {

                        if ("200".equals(responseBean.getStatus())) {
                            //修改生成订单页面的地址
                            RxBus.get().post("updateAddress", addressList);
                            finish();
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(ReceiptAddressActivity.this, responseBean.getMessage());
                        } else {
                            ToastUtil.show(ReceiptAddressActivity.this, "请求失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }


}
