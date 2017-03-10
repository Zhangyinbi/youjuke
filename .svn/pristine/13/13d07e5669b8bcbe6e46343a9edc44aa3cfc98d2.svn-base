package com.youjuke.buildingmaterialmall.app.myasset.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.RebateInfo;
import com.youjuke.buildingmaterialmall.widgets.SweetAlertDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/19.
 */

public class RebateAdapter extends RecyclerView.Adapter<RebateAdapter.MyHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private ArrayList<RebateInfo.ProductInfo> datas;
    private SweetAlertDialog.Builder builder;

    public RebateAdapter(Context context, ArrayList<RebateInfo.ProductInfo> datas) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_rebate, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tvNum.setText(datas.get(position).getPeriods().trim());
        SpannableString span = new SpannableString(datas.get(position).getFl_money() + "元");
        //设置需要的字体大小，已经需要设置文本起始位置
        span.setSpan(new AbsoluteSizeSpan(40), 0, datas.get(position).getFl_money().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new AbsoluteSizeSpan(24), datas.get(position).getFl_money().length()+1, datas.get(position).getFl_money().length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (datas.get(position).getButton_status() == 2) {
            holder.tvRebateMoney.setTextColor(Color.parseColor("#ff5200"));
            holder.tvRebateMoney.setText(span);
            holder.tvGetStatus.setVisibility(View.VISIBLE);
            holder.tvGetStatus.setText(R.string.received);
            holder.tvGetStatus1.setVisibility(View.GONE);
        } else if (datas.get(position).getButton_status() == 1) {
            holder.tvRebateMoney.setTextColor(Color.parseColor("#00c050"));
            holder.tvRebateMoney.setText(span);
            holder.tvGetStatus1.setVisibility(View.VISIBLE);
            holder.tvGetStatus.setVisibility(View.GONE);
            holder.tvGetStatus1.setText(R.string.get);
            holder.tvGetStatus1.setBackgroundResource(R.drawable.green_box);
            holder.tvGetStatus1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMoney(position);
                }
            });
        } else if (datas.get(position).getButton_status() == 0) {
            holder.tvRebateMoney.setTextColor(Color.parseColor("#989898"));
            holder.tvRebateMoney.setText(span);
            holder.tvGetStatus1.setVisibility(View.VISIBLE);
            holder.tvGetStatus.setVisibility(View.GONE);
            holder.tvGetStatus1.setText(R.string.not_issue);
            holder.tvGetStatus1.setBackgroundResource(R.drawable.gray_989898_box);
        }
        holder.tvRebateTime.setText("返利日期：" + datas.get(position).getFl_time());
        holder.tvGetTime.setText("领取日期：" + datas.get(position).getLq_time());
    }

    public void dialogdissmiss() {
        if (builder != null && builder.isShow()) {
            builder.dismiss();
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvRebateMoney;//本期返利金额
        private TextView tvNum;//第几期
        private TextView tvRebateTime;//返利日期
        private TextView tvGetTime;//领取日期
        private TextView tvGetStatus;//领取状态
        private TextView tvGetStatus1;//领取状态

        public MyHolder(View view) {
            super(view);
            tvRebateMoney = (TextView) view.findViewById(R.id.tv_rebate_money);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
            tvRebateTime = (TextView) view.findViewById(R.id.tv_rebate_time);
            tvGetTime = (TextView) view.findViewById(R.id.tv_get_time);
            tvGetStatus = (TextView) view.findViewById(R.id.tv_get_status);
            tvGetStatus1 = (TextView) view.findViewById(R.id.tv_get_status1);
        }
    }

    /**
     * 领取返利
     */
    private void getMoney(final int position) {
        if (builder == null) {
            builder = new SweetAlertDialog.Builder(context);
        }
        builder.setCancelable(true)
                .setMessage("领取本期返利金额")
                .setTitle("提示")
                .setCancelableoutSide(true)
                .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        if (rebateListener != null) {
                            rebateListener.onGetListener(position);//领取返利
                        }
                    }
                }).show();
    }

    OnGetRebateListener rebateListener;

    public interface OnGetRebateListener {
        void onGetListener(int position);
    }

    public void setOnGetRebateListener(OnGetRebateListener rebateListener) {
        this.rebateListener = rebateListener;
    }


}
