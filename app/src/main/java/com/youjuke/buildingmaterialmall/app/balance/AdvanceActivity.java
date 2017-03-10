package com.youjuke.buildingmaterialmall.app.balance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.WrapActivity;
import com.youjuke.buildingmaterialmall.entity.AccountInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.buildingmaterialmall.widgets.CheckAccountDialog;
import com.youjuke.buildingmaterialmall.widgets.SweetAlertDialog;
import com.youjuke.swissgearlibrary.manager.ActivityManager;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述：提现
 * Created by Administrator on 2016/12/30.
 * author zyb
 */

public class AdvanceActivity extends WrapActivity implements View.OnClickListener, TextWatcher {
    public Toolbar myAssetToolBar;
    private EditText etMoney;
    private TextView tvReminder;
    private Button btnSure;
    private String moneyBalance;

    @Override
    public void initViews(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, Color.parseColor("#00c04f"), 0);
        moneyBalance = getIntent().getStringExtra("moneyBalance");
        myAssetToolBar = (Toolbar) findViewById(R.id.tb_my_asset_bar);
        initEditText();
        tvReminder = (TextView) findViewById(R.id.tv_reminder);
        tvReminder.setText(getString(R.string.balance) + moneyBalance + getString(R.string.yuan));
        btnSure = (Button) findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        btnSure.setClickable(false);

    }

    /**
     * 设置editText
     */
    private void initEditText() {
        etMoney = (EditText) findViewById(R.id.et_money);
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(getString(R.string.please_enter_the_withdrawal_amount));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        etMoney.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
        etMoney.addTextChangedListener(this);
        etMoney.setFilters(new InputFilter[]{new InputMoney(etMoney)});
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_advance;
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(myAssetToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        myAssetToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                String trim = etMoney.getText().toString().trim();
                if (trim.endsWith(".")) {
                    trim = trim.substring(0, trim.length() - 1);
                }
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                getAccountInfo(trim);
                break;

        }

    }

    SweetAlertDialog.Builder builder;


    private String id = "";

    /**
     * 获取银行信息
     */
    private void getAccountInfo(final String money) {
        showProgressDialog();
        params.clear();
        params.put("baoming_id", BuildingMaterialApp.user.getBaoming_id());
        params.put("mobile", BuildingMaterialApp.user.getMobile());
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.WALLET_BALANCE_DETAIL, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {

                    private ArrayList<AccountInfo.Account> product;

                    @Override
                    public void onCompleted() {
                        dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(AdvanceActivity.this, getString(R.string.failed_to_get_a_bank_account_please_try_again_later));
                        dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            AccountInfo accountInfo = gson.fromJson(responseBean.getData(), AccountInfo.class);
                            product = accountInfo.getProduct();
                            id = product.get(0).getContract_id();
                            if (product != null && product.size() > 0) {
                                if (product.size() == 1) {
                                    final CheckAccountDialog dialog = new CheckAccountDialog(AdvanceActivity.this, true);
                                    dialog.
                                            setAccountClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    id = product.get(0).getContract_id();
                                                }
                                            }, product.get(0).getAmount_bank_name()).
                                            setCancelClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    getBalance(money);
                                                }
                                            }).show();
                                } else {
                                    final CheckAccountDialog dialog = new CheckAccountDialog(AdvanceActivity.this, false);
                                    dialog.setAccountClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            id = product.get(1).getContract_id();
                                            dialog.tv_camera.setBackgroundColor(Color.parseColor("#ffffff"));
                                            dialog.tv_photo.setBackgroundResource(R.drawable.color_eee_box_bottom);
                                        }
                                    }, product.get(1).getAmount_bank_name())
                                            .setAccount1ClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    id = product.get(0).getContract_id();
                                                    dialog.tv_camera.setBackgroundColor(Color.parseColor("#00C24F"));
                                                    dialog.tv_photo.setBackgroundResource(R.drawable.color_write_box_bottom);
                                                }
                                            }, product.get(0).getAmount_bank_name())
                                            .setCancelClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    getBalance(money);

                                                }
                                            }).show();
                                }
                            }
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(AdvanceActivity.this, responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    /**
     * 提现
     */
    private void getBalance(final String money) {//调接口
        showProgressDialog();
        params.clear();
        params.put("baoming_id", BuildingMaterialApp.user.getBaoming_id());
        params.put("mobile", BuildingMaterialApp.user.getMobile());
        params.put("drawal_money", money);
        params.put("contract_id", id);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.WALLET_BALANCE_DRAWAL, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                        dimissDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(AdvanceActivity.this, getString(R.string.failed_to_getMoney_please_again));
                        dimissDialog();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if ("200".equals(responseBean.getStatus())) {
                            ToastUtil.show(AdvanceActivity.this, responseBean.getMessage());
                            Intent intent = new Intent();
                            intent.putExtra("money", money);
                            setResult(RESULT_OK, intent);
                            ActivityManager.getInstance().finishActivity();
                        } else if ("400".equals(responseBean.getStatus())) {
                            ToastUtil.show(AdvanceActivity.this, responseBean.getMessage());
                        }
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (builder != null && builder.isShow()) {
            builder.dismiss();
        }
    }

    //金额的监听
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().trim().length() == 0) {
            btnSure.setBackgroundResource(R.drawable.gray_ddd_box);
            btnSure.setClickable(false);
            tvReminder.setText(getString(R.string.balance) + moneyBalance + getString(R.string.yuan));
            tvReminder.setTextColor(Color.parseColor("#808080"));
        }
        if (s.toString().trim().length() > 0) {
            if (Float.parseFloat(s.toString().trim()) > Float.parseFloat(moneyBalance.trim())) {
                tvReminder.setText(R.string.the_amount_is_more_than_can_be_withdrawal_amount);
                tvReminder.setTextColor(Color.parseColor("#f23030"));
                btnSure.setBackgroundResource(R.drawable.gray_ddd_box);
                btnSure.setClickable(false);
            } else {
                tvReminder.setText(getString(R.string.balance) + moneyBalance + getString(R.string.yuan));
                tvReminder.setTextColor(Color.parseColor("#808080"));
                if (!s.toString().trim().equals("0.") && !s.toString().trim().equals("0") && !s.toString().trim().equals("0.0") && !s.toString().trim().equals("0.00")) {
                    btnSure.setBackgroundResource(R.drawable.green_press_check);
                    btnSure.setClickable(true);
                } else {
                    btnSure.setBackgroundResource(R.drawable.gray_ddd_box);
                    btnSure.setClickable(false);
                }
            }
        }
    }

    /**
     * 限制只能输入两位小数
     *
     * @author zengchao
     */
    class InputMoney implements InputFilter {
        EditText money;

        public InputMoney(EditText money) {
            this.money = money;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            // TODO Auto-generated method stub
            if (source.toString().equals(".") && dstart == 0 && dend == 0) {//判断小数点是否在第一位
                return "";
            }
            if (source.toString().equals("0") && dstart == 0 && dend == 0) {//判断零是否在第一位
                money.setText(source + "." + dest);//给小数点前面加0
                money.setSelection(2);//设置光标
            }
            if (dest.toString().indexOf(".") != -1 && (dest.length() - dest.toString().indexOf(".")) > 2) {//判断小数点是否存在并且小数点后面是否已有两个字符
                if ((dest.length() - dstart) < 3) {//判断现在输入的字符是不是在小数点后面
                    return "";//过滤当前输入的字符
                }
            }
            return null;
        }

    }
}
