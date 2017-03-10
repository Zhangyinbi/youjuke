package com.youjuke.buildingmaterialmall.app.myasset.adapter;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.BillInfo;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/19.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyHolder> {

    private Context context;
    private final LayoutInflater inflater;
    private ArrayList<BillInfo.Bill> datas;
    public BillAdapter(Context context,ArrayList<BillInfo.Bill> datas) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.datas=datas;
    }

    @Override
    public BillAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_bill, parent, false);
        BillAdapter.MyHolder myHolder=new BillAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(BillAdapter.MyHolder holder, int position) {
        if (getItemViewType(position)==0){
            holder.llLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            holder.llLayout.setBackgroundColor(Color.parseColor("#f8f8f8"));
        }
        holder.tvMoney.setText(MoneySimpleFormat.getSimpleType(datas.get(position).getAmount()));
        holder.tvContent.setText(datas.get(position).getType());
        holder.tvDataTime.setText(datas.get(position).getCreated());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2==1){
            return 0;
        }else {
            return 1;
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private LinearLayout llLayout;
        private TextView tvDataTime;
        private TextView tvContent;
        private TextView tvMoney;
        public MyHolder(View view) {
            super(view);
            llLayout= (LinearLayout) view.findViewById(R.id.ll_layout);
            tvDataTime= (TextView) view.findViewById(R.id.tv_data_time);
            tvContent= (TextView) view.findViewById(R.id.tv_content);
            tvMoney= (TextView) view.findViewById(R.id.tv_money);
        }
    }
}
