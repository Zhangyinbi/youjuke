package com.youjuke.buildingmaterialmall.app.buy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.OrderInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;

/**
 * 描述: 生成订单Adapter
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-25 16:12
 */

public class TakeOrderAdapter extends RecyclerArrayAdapter<OrderInfo.GoodItemsBean> {
    private Context context;
    public TakeOrderAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new TakeOrderViewHolder(parent);
    }

    class TakeOrderViewHolder extends BaseViewHolder<OrderInfo.GoodItemsBean>{
        private ImageView mImgOrder;
        private TextView mTvGoodsName;
        private TextView mTvPrice;
        private TextView mTvOldPrice;
        private TextView mTvCount;
        private TextView mTvTypeName;
        private View mRules;

        private void assignViews() {
            mImgOrder = $(R.id.img_order);
            mTvGoodsName = $(R.id.tv_goods_name);
            mTvPrice = $(R.id.tv_price);
            mTvOldPrice = $(R.id.tv_old_price);
            mTvCount = $(R.id.tv_count);
            mTvTypeName = $(R.id.tv_type_name);
            mRules = $(R.id.rules);
        }


        public TakeOrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_take_order);
            assignViews();
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void setData(OrderInfo.GoodItemsBean data) {
            Glide.with(context).load(ApiContstants.IMG_URL+data.getGood_image())
                    .placeholder(R.mipmap.btn_tp).centerCrop().into(mImgOrder);
            mTvGoodsName.setText(data.getGood_name());
            mTvPrice.setText(data.getSale_price());
            mTvOldPrice.setText(data.getClassification_original_cost());
            mTvCount.setText("x"+data.getClassification_num());
            mTvTypeName.setText("规格型号: "+data.getClassification_name());
            //添加删除线
            mTvOldPrice.setPaintFlags(mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //如果是最后一个view 隐藏分割线
            if (getLayoutPosition() == getCount()){
                mRules.setVisibility(View.GONE);
            }else{
                mRules.setVisibility(View.VISIBLE);
            }
        }
    }
}
