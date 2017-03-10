package com.youjuke.buildingmaterialmall.app.receipt_address_compile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.buy.TakeOrderActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.entity.CityAddressList;
import com.youjuke.buildingmaterialmall.entity.CompileAddress;
import com.youjuke.buildingmaterialmall.entity.DistrictsBean;
import com.youjuke.buildingmaterialmall.entity.OrderInfo;
import com.youjuke.buildingmaterialmall.entity.PickProjects;
import com.youjuke.buildingmaterialmall.module.ClearEditText;
import com.youjuke.buildingmaterialmall.module.SwitchView;
import com.youjuke.buildingmaterialmall.module.WheelView;
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

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 描述:
 * 编辑收货地址
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-18 18:46
 */
public class ReceiptAddressCompileActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolBar;
    private ClearEditText editUserName;
    private ClearEditText editUserMobile;
    private TextView textSelectAddress;
    private ClearEditText editDetailedAddress;
    private SwitchView swithchView;
    private LinearLayout linearLayout;
    private CityAddressList cityAddressList;
    private List<DistrictsBean> districtsBeans;
    private DistrictsBean districtsBean;
    private TextView toolBarText;
    private List<String> districtsList = new ArrayList<>();
    private Button addButton;
    private int siwtch;
    private int refresh;
    private int districtsSelectedIndex;
    private int isDefault = 0;
    private CompileAddress compileAddress;
    private List<PickProjects> projectsList = new ArrayList<PickProjects>();//购买商品的集合



    private void assignViews() {

        addButton = (Button) findViewById(R.id.button);
        toolBarText = (TextView) findViewById(R.id.tool_text);
        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        editUserName = (ClearEditText) findViewById(R.id.edit_user_name);
        editUserMobile = (ClearEditText) findViewById(R.id.edit_user_Mobile);
        textSelectAddress = (TextView) findViewById(R.id.text_select_address);
        editDetailedAddress = (ClearEditText) findViewById(R.id.edit_detailed_address);
        swithchView = (SwitchView) findViewById(R.id.swithch_view);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_select_address);
        // TODO onClick无效,暂时直接
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (districtsList.size()==0){
                    getCityAddressList(true);
                    return;
                }
                showMyDialog();
            }
        });
        addButton.setOnClickListener(this);
        //获取地址详情和ID


//        swithchView.setOnToggleChanged(new SwitchView.OnToggleChanged() {
//            @Override
//            public void onToggle(boolean on) {
//                if (on) {
//
//                    isDefault = 1;
//                } else {
//                    isDefault = 0;
//                }
//            }
//        });

    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        siwtch = extras.getInt("siwtch");
        if (siwtch==2){
            int classifications_id=extras.getInt("classifications_id");
            int projectCount=extras.getInt("projectCount");
            projectsList.add(new PickProjects(classifications_id + "", projectCount + ""));
        }
        refresh = extras.getInt("refresh");
        assignViews();
        if (refresh != 0) {
            refreshAddress(refresh);
        }
    }

    /**
     * 请求并且刷新编辑数据
     *
     * @param refresh
     */
    private void refreshAddress(final int refresh) {
        mDialog.showDialog();
        params.clear();
        params.put(ApiContstants.U_ID, BuildingMaterialApp.user.getId());
        params.put(ApiContstants.ID, String.valueOf(refresh));
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.GET_ADDRESS, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                mDialog.dimissDialog();
                                if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, responseBean.getMessage());
                                } else if (responseBean.getStatus().equals("200")) {
                                    compileAddress = gson.fromJson(responseBean.getData()
                                            , new TypeToken<CompileAddress>() {
                                            }.getType());

                                    //加载编辑数据
                                    initializecompileAddress(compileAddress);
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
     * 初始化编辑数据
     *
     * @param compileAddress
     */
    @SuppressLint("SetTextI18n")
    private void initializecompileAddress(CompileAddress compileAddress) {

        editUserName.setText(compileAddress.getReceive_name());
        editUserMobile.setText(compileAddress.getMobile());
        editDetailedAddress.setText(compileAddress.getAddress());
        districtsBeans = compileAddress.getDistricts();

        for (DistrictsBean dis : districtsBeans) {
            districtsList.add(dis.getDistrict_name());
        }

        for (int i = 0; i < districtsBeans.size(); i++) {

            if (districtsBeans.get(i).getDistrict_id().equals(compileAddress.getUser_district_id())) {
                textSelectAddress.setText(("上海市" + districtsBeans.get(i).getDistrict_name()));
                districtsSelectedIndex = i;
            }

        }

        if (compileAddress.isIs_default()) {
            swithchView.setToggleOn(true);

        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_receipt_address_compile;
    }

    @Override
    public void initToolBar() {
        if (siwtch == 1) {
            toolBarText.setText("新增收货地址");
        }
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
                finish();
            }
        });
    }

    /**
     * 显示选择区域
     */
    private void showMyDialog() {

        View outerView = LayoutInflater.from(ReceiptAddressCompileActivity.this).inflate(R.layout.wheel_view, null);
        final WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);

        wv.setItems(districtsList);

        new AlertDialog.Builder(ReceiptAddressCompileActivity.this)
                .setView(outerView)
                .setTitle("上海市")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textSelectAddress.setText("上海市" + wv.getSeletedItem());
                        districtsSelectedIndex = wv.getSeletedIndex();
                    }
                }).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCityAddressList(false);
    }

    /**
     * 获取地址详情和ID
     */
    public void getCityAddressList(final boolean flag) {
        mDialog.showDialog();
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.GET_ADDRESS, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, responseBean.getMessage());
                                } else if (responseBean.getStatus().equals("200")) {
                                    cityAddressList = gson.fromJson(responseBean.getData(),
                                            new TypeToken<CityAddressList>() {
                                            }.getType());

                                    //保存区域信息
                                    districtsBeans = cityAddressList.getDistricts();

                                    for (DistrictsBean dis : districtsBeans) {
                                        districtsList.add(dis.getDistrict_name());
                                    }
                                }
                                if (flag){
                                    showMyDialog();
                                }

                                    mDialog.dimissDialog();


                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtil.show(ReceiptAddressCompileActivity.this,"请检查网络");
                                    mDialog.dimissDialog();

                            }
                        }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:

                if (editUserName.getText().length() <= 0) {

                    ToastUtil.show(this, "请输入收货人");

                } else if (editUserMobile.getText().length() <= 0) {

                    ToastUtil.show(this, "请输入手机号");

                }else if (textSelectAddress.getText().equals("点击选择")) {

                    ToastUtil.show(this, "请选择区域");

                } else if (editDetailedAddress.getText().length() <= 0) {
                    ToastUtil.show(this, "请输入详细地址");

                }else if (districtsBeans==null) {
                    ToastUtil.show(this, "请选择所在地区");
                }
                else {


                    if (siwtch == 1) {
                        //确认添加地址
                        addAddress();
                    } else if (refresh != 0) {
                        //确认编辑
                        compileAddressReturn();
                    }else if (siwtch==2){
                        //当前页面是从商品详情生成订单时没有地址跳进来
                        addAddress();
                    }


                }


                break;
        }
    }

    /**
     * 确认编辑
     */
    private void compileAddressReturn() {
       mDialog.showDialog();
        params.clear();
        //用户ID
        params.put(ApiContstants.U_ID, BuildingMaterialApp.user.getId());
        params.put(ApiContstants.ID, String.valueOf(refresh));
        //手机号
        params.put(ApiContstants.MOBIlE, editUserMobile.getText().toString());
        // TODO 因为只有上海所有先暂时写死
        params.put(ApiContstants.PROVINCE_ID, "9");
        // TODO 因为只有上海所有先暂时写死
        params.put(ApiContstants.CITY_ID, "72");
        //收货人姓名
        params.put(ApiContstants.RECEIVE_NAME, editUserName.getText().toString());
        params.put(ApiContstants.DISTRICT_ID, districtsBeans.get(districtsSelectedIndex).getDistrict_id());
        params.put(ApiContstants.ADDRESS, editDetailedAddress.getText().toString());

        if (swithchView.getToggleOn()) {
            isDefault = 1;
        } else {
            isDefault = 0;
        }

        params.put(ApiContstants.IS_DEFAULT, String.valueOf(isDefault));
        //添加地址请求
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.ADDRESS_SAVE, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                //取消加载动画。。
                                mDialog.dimissDialog();
                                //error='4004', status='400', message='手机号格式不正确'
                                //error='0', status='200', message='操作成功', data='true'
                                if (responseBean.getStatus().equals("200")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, "修改成功");
                                    // 返回1让地址页面刷新
                                    setResult(refresh);
                                    ReceiptAddressCompileActivity.this.finish();
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, "修改失败");
                                }


                                L.d(responseBean.toString());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();
                            }
                        }));
    }

    /**
     * 确认添加地址
     */
    private void addAddress() {
        mDialog.showDialog();
        params.clear();
        //用户ID
        params.put(ApiContstants.U_ID, BuildingMaterialApp.user.getId());
        //手机号
        params.put(ApiContstants.MOBIlE, editUserMobile.getText().toString());
        // TODO 因为只有上海所有先暂时写死
        params.put(ApiContstants.PROVINCE_ID, "9");
        // TODO 因为只有上海所有先暂时写死
        params.put(ApiContstants.CITY_ID, "72");
        //收货人姓名
        params.put(ApiContstants.RECEIVE_NAME, editUserName.getText().toString());
        params.put(ApiContstants.DISTRICT_ID, districtsBeans.get(districtsSelectedIndex).getDistrict_id());
        params.put(ApiContstants.ADDRESS, editDetailedAddress.getText().toString());

        if (swithchView.getToggleOn()) {
            isDefault = 1;
        } else {
            isDefault = 0;
        }

        params.put(ApiContstants.IS_DEFAULT, String.valueOf(isDefault));
        //添加地址请求
        compositeSubscription.add(
                RetrofitManager.getInstance().create(CommonService.class)
                        .getData(new RequestBean.JsonMsg(ApiContstants.ADDRESS_SAVE, params).toJson())
                        .compose(this.<ResponseBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<ResponseBean>() {
                            @Override
                            public void call(ResponseBean responseBean) {
                                //取消加载动画。。
                                mDialog.dimissDialog();
                                //error='4004', status='400', message='手机号格式不正确'
                                //error='0', status='200', message='操作成功', data='true'
                                if (responseBean.getStatus().equals("200")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, "添加成功");
                                    // 返回1让地址页面刷新
                                    if (siwtch==2){
                                        saveOrderInfo();
                                    }else {
                                        setResult(1);
                                        ReceiptAddressCompileActivity.this.finish();
                                    }
                                } else if (responseBean.getStatus().equals("400")) {
                                    ToastUtil.show(ReceiptAddressCompileActivity.this, "手机号格式不正确");
                                }


                                L.d(responseBean.toString());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mDialog.dimissDialog();
                            }
                        }));

    }
    private OrderInfo orderInfo;//返回生成的订单
    private List<OrderInfo.GoodItemsBean> goodsList = new ArrayList<>();//返回的生成订单商品集合
    //生成订单
    private void saveOrderInfo() {
        params.clear();
        params.put("user_id", BuildingMaterialApp.user.getId());
        params.put("goods_info", projectsList);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.SAVE_ORDER, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtil.show(ReceiptAddressCompileActivity.this, "生成订单失败");
                    }
                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            L.e("getPopUpWindowOrderInfo返回数据 ：" + responseBean.toString());
                            orderInfo = gson.fromJson(responseBean.getData(), new TypeToken<OrderInfo>() {
                            }.getType());
                            goodsList.clear();
                            goodsList.addAll(orderInfo.getGood_items());
                            Intent intent = new Intent(ReceiptAddressCompileActivity.this, TakeOrderActivity.class);
                            intent.putExtra("orderInfo", orderInfo);
                            startActivity(intent);
                            ReceiptAddressCompileActivity.this.finish();
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(ReceiptAddressCompileActivity.this, "生成订单失败");
                        } else {
                            ToastUtil.show(ReceiptAddressCompileActivity.this, "请求失败");
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }
}
