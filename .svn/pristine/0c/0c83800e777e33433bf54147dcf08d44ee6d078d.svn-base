package com.youjuke.buildingmaterialmall.app.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.schedule_details.ScheduleDetailsActivity;
import com.youjuke.buildingmaterialmall.entity.OrderDetails;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;

import static com.youjuke.buildingmaterialmall.R.id.rules;

/**
 * 描述: 订单详情适配器
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-19 14:30
 */
public class OrderDetailsAdapter extends RecyclerArrayAdapter<OrderDetails.GoodsBean> {

    private Context context;
    private String orderId;
    private String status_id;
    public OrderDetailsAdapter(Context context,String orderId) {
        super(context);
        this.context = context;
        this.orderId=orderId;
    }

    @Override
    public OrderDetailsViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderDetailsViewHolder(parent);
    }

    class OrderDetailsViewHolder extends BaseViewHolder<OrderDetails.GoodsBean>{

        private ImageView mImgOrder;
        private TextView mTvGoodsName;
        private TextView mTvPrice;
        private TextView mTvOldPrice;
        private TextView mTvCount;
        private TextView mTvTypeName;
        private Button mBtnCheckScheduler;
        private View mRules;

        private void assignViews() {
            mImgOrder = $(R.id.img_order);
            mTvGoodsName = $(R.id.tv_goods_name);
            mTvPrice = $(R.id.tv_price);
            mTvOldPrice = $(R.id.tv_old_price);
            mTvCount = $(R.id.tv_count);
            mTvTypeName = $(R.id.tv_type_name);
            mBtnCheckScheduler = $(R.id.btn_check_scheduler);
            mRules = $(rules);
        }


        public OrderDetailsViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_order_details);
            assignViews();
        }

        @Override
        public void setData(final OrderDetails.GoodsBean goods) {
            Glide.with(context).load(ApiContstants.IMG_URL+goods.getImage())
                    .placeholder(R.mipmap.btn_tp).centerCrop().into(mImgOrder);
            mTvGoodsName.setText(goods.getName());
            mTvTypeName.setText("规格型号: "+goods.getClassification_name());
            mTvPrice.setText(MoneySimpleFormat.getMoneyType(goods.getGood_price()));
            mTvOldPrice.setText(MoneySimpleFormat.getMoneyType(goods.getOriginal_cost()));
            mTvCount.setText("x"+goods.getNum());
            //如果是最后一个view 隐藏分割线
            if (getLayoutPosition() == getCount()){
                mRules.setVisibility(View.GONE);
            }else{
                mRules.setVisibility(View.VISIBLE);
            }

            //添加删除线
            mTvOldPrice.setPaintFlags(mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(!TextUtils.isEmpty(status_id)){
                //如果是待付款或已取消的状态 则隐藏 查看进度的按钮
                if ("1".equals(status_id)||"5".equals(status_id)){
                    mBtnCheckScheduler.setVisibility(View.GONE);
                }else{
                    mBtnCheckScheduler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent=new Intent(context, ScheduleDetailsActivity.class);
                            intent.putExtra(ApiContstants.GOODS_ID,goods.getGid());
                            intent.putExtra(ApiContstants.ORDER_ID,orderId);
                            getContext().startActivity(new Intent(intent));
                        }
                    });
                }
            }
        }
    }

    public void setStatus_id(String status_id){
        this.status_id = status_id;
    }
}
