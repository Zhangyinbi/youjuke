package com.youjuke.buildingmaterialmall.app.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.Order;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述: 订单fragment
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-18 13:45
 */
public class OrderFragment extends BaseFragment {
    private int status_id;
    private EasyRecyclerView recyclerView;
    private List<Order> orderList;
    private OrderAdapter adapter;
    /*private boolean isLoaded = false;//是否加载过
    private boolean isShouldReLoad = false;//是否应该重新加载*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        RxBus.get().register(this);
        return super.onCreateView(inflater, container, state);
    }

    private void assignViews() {
        recyclerView = $(R.id.rv_order);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void finishCreateView(Bundle state) {
        assignViews();
        orderList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new OrderAdapter(activity);
        adapter.addAll(orderList);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                String id = orderList.get(position).getId();
                intent.putExtra("order_id", id);
                intent.putExtra("status_id", orderList.get(position).getStatus_id());
                startActivity(intent);
            }
        });
        recyclerView.setAdapterWithProgress(adapter);

    }

    public static OrderFragment getInstance(int status_id) {
        OrderFragment fragment = new OrderFragment();
        fragment.status_id = status_id;
        return fragment;
    }

    /**
     * 获取订单列表
     *
     * @param position
     */
    @Subscribe(tags = @Tag("getOrderList"))
    public void getOrderList(String position) {
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        params.put("status_id", status_id + "");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.ORDER_LIST, params).toJson())
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
                        ToastUtil.show(activity, "请求失败");
                        recyclerView.showRecycler();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("返回数据:" + responseBean.toString());
                        if (responseBean.getData() != null && !"null".equalsIgnoreCase(responseBean.getData())) {

                            if ("200".equals(responseBean.getStatus())) {
                                orderList = gson.fromJson(responseBean.getData(), new TypeToken<List<Order>>() {
                                }.getType());
                                //L.i("数据条数："+orderList.size());
                                adapter.clear();
                                adapter.addAll(orderList);

                            } else {
                                ToastUtil.show(activity, "请求失败");
                                recyclerView.showRecycler();
                            }
                            RxBus.get().post("stopRefresh", "");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * 删除订单
     */
    @Subscribe(tags = @Tag("deleteOrder"))
    public void deleteOrder(Order order) {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order.getId() + "");
        params.put("status", "0");
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.TRANSFER_ORDER, params).toJson())
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
                        ToastUtil.show(activity, "请求失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("返回数据:" + responseBean.getData());
                        if (responseBean.getData() != null && !"null".equalsIgnoreCase(responseBean.getData())) {

                            if ("200".equals(responseBean.getStatus())) {
                                //刷新
                                getOrderList(status_id + "");
                                ToastUtil.show(activity, "删除成功");
                            } else if ("400".equals(responseBean.getStatus())) {
                                ToastUtil.show(activity, "请求失败");
                            }
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    //确认收货
    @Subscribe(tags = @Tag("confirmReceipt"))
    public void confirmReceipt(Order order) {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("order_id", order.getId());
        params.put("status", "4");//待收货为3  收货传4
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.TRANSFER_ORDER, params).toJson())
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
                        ToastUtil.show(activity, "操作失败");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("确认收货返回数据：" + responseBean.getData());
                        if ("200".equals(responseBean.getStatus().trim())) {
                            ToastUtil.show(getApplicationContext(), "确认收货成功");
                            getOrderList(status_id + "");
                        } else {
                            ToastUtil.show(getApplicationContext(), "操作失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }


    @Override
    public void onResume() {
        super.onResume();
        getOrderList(status_id + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.get().unregister(this);
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
}
