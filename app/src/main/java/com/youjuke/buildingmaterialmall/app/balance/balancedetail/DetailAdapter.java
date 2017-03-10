package com.youjuke.buildingmaterialmall.app.balance.balancedetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.BalanceDetailInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/3.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder> {
    private Context context;
    private ArrayList<BalanceDetailInfo> datas;
    private int type;

    public DetailAdapter(Context context, ArrayList<BalanceDetailInfo> datas,int type) {
        this.context = context;
        this.datas = datas;
        this.type=type;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        MyHolder myHolder=new MyHolder(inflater.inflate(R.layout.item_wallet_detail,parent,false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (type==0) {
            holder.tvMoney.setTextColor(Color.parseColor("#de0202"));
            holder.tvMoney.setText("+"+datas.get(position).getAmount());
            holder.ivImg.setImageResource(R.mipmap.btn_zxfk);
        }else if (type==1){
            holder.tvMoney.setTextColor(Color.parseColor("#333333"));
            holder.tvMoney.setText("-"+datas.get(position).getAmount());
            holder.ivImg.setImageResource(R.mipmap.btn_yetx);
        }
        holder.tvTitle.setText(datas.get(position).getText());
        holder.tvTime.setText(datas.get(position).getCreated());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setdata(ArrayList<BalanceDetailInfo> datas) {
        this.datas=datas;
    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView tvMoney;
        private TextView tvTitle;
        private TextView tvTime;
        private ImageView ivImg;
        public MyHolder(View itemView) {
            super(itemView);
            tvMoney= (TextView) itemView.findViewById(R.id.tv_money);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_title);
            ivImg= (ImageView) itemView.findViewById(R.id.iv_img);
            tvTime= (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
