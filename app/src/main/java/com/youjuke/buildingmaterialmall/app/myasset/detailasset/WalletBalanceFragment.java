package com.youjuke.buildingmaterialmall.app.myasset.detailasset;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.balance.AdvanceActivity;
import com.youjuke.buildingmaterialmall.app.balance.BalanceDetailActivity;
import com.youjuke.buildingmaterialmall.app.myasset.MyAssetActivity;
import com.youjuke.swissgearlibrary.base.BaseFragment;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;


/**
 * description 钱包余额
 * Created by Administrator on 2016/12/16.
 * author zyb 2016/12/16--13:44
 */

public class WalletBalanceFragment extends BaseFragment implements View.OnClickListener{

    private TextView tvMoneyBalance;//余额
    private Button btnCheckDetail;//查看明细
    private Button btnAdvance;//提现
    private String balance;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wallet_balance;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
        getDataFromNet();
    }

    private void getDataFromNet() {
        balance=((MyAssetActivity)getActivity()).getBalance();
        tvMoneyBalance.setText(MoneySimpleFormat.getMoneyType(balance));
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvMoneyBalance = $(R.id.tv_money_balance);
        btnCheckDetail = $(R.id.btn_check_detail);
        btnAdvance = $(R.id.btn_advance);
        btnCheckDetail.setOnClickListener(this);
        btnAdvance.setOnClickListener(this);
    }

    public static WalletBalanceFragment newInstance() {
        return new WalletBalanceFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_advance://提现
                Intent intent = new Intent(mContext, AdvanceActivity.class);
                intent.putExtra("moneyBalance",balance);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_check_detail:
                startActivity(new Intent(mContext, BalanceDetailActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==activity.RESULT_OK){//提现成功
            String money=data.getStringExtra("money");
            balance = Float.parseFloat(balance) - Float.parseFloat(money.toString())+"";
            tvMoneyBalance.setText(MoneySimpleFormat.getMoneyType(balance));
        }
    }
}
