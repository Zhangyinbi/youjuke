package com.youjuke.buildingmaterialmall.app.message_center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyDetailsActivity;
import com.youjuke.buildingmaterialmall.app.product_details.ProductDetailsActivity;
import com.youjuke.buildingmaterialmall.app.schedule_details.ScheduleDetailsActivity;
import com.youjuke.buildingmaterialmall.app.seckill.SeckillDetailsActivity;
import com.youjuke.buildingmaterialmall.entity.MessageDetailsEntity;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * 描述:消息详情适配
 * <p>
 * 工程:
 * <p>
 * #0000    Tian Xiao    2016-11-22 10:12
 */
@SuppressWarnings("ALL")
public class MessageDetailsAdapter extends BaseRecyclerAdapter {

    private MessageDetailsEntity entity;
    private String type;
    private OnItemClickListener listener;
    private Intent intent;

    public MessageDetailsAdapter(Context context, List datas, String type) {
        super(context, datas);
        this.type = type;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        entity = (MessageDetailsEntity) mDatas.get(position);
        viewHolder.textViewTime.setText(entity.getCreatedate().trim());
        viewHolder.textViewTitle.setText(entity.getTitle());
        if (entity.getImage().length() > 0) {
            Glide.with(mContext).load(entity.getImage()).into(viewHolder.imageView);
        }
        viewHolder.textViewDetails.setText(entity.getContent());

        if (type.equals("1")){//如果是商品消息
            viewHolder.linearLayoutMoney.setVisibility(View.VISIBLE);
            viewHolder.textViewMoney.setText("￥ "+entity.getPrice());
            viewHolder.textViewProuctDetails.setVisibility(View.VISIBLE);
            if (entity.getGoods_status()!=null) {
                //goods_status	商品状态 （1-新品上线 2-活动结束）
                if (entity.getGoods_status().equals("2")) {
                    viewHolder.textViewImageTitle.setVisibility(View.VISIBLE);
                    viewHolder.textViewMoney.setTextColor(Color.parseColor("#999999"));
                    viewHolder.textViewTitle.setTextColor(Color.parseColor("#999999"));
                    viewHolder.textViewDetails.setTextColor(Color.parseColor("#999999"));
                    ((ViewHolder) holder).itemView.setClickable(false);
                }
            }
        }


        //Item单击事件
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(viewHolder, position, entity );
        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = null;
        BaseRecyclerViewHolder holder = null;
        view = mInflater.inflate(R.layout.recycler_item_message_details, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        public View view;
        private TextView textViewTime;
        private RelativeLayout relativeLayoutItem;
        private TextView textViewTitle;
        private ImageView imageView;
        private TextView textViewDetails;
        private LinearLayout linearLayoutMoney;
        private TextView textViewProuctDetails;
        private TextView textViewMoney;
        private TextView textViewImageTitle;

        private void assignViews() {
            textViewTime = (TextView)view.findViewById(R.id.textView_time);
            relativeLayoutItem = (RelativeLayout) view.findViewById(R.id.relativeLayout_item);
            textViewTitle = (TextView) view.findViewById(R.id.textView_title);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textViewDetails = (TextView) view.findViewById(R.id.textView_details);
            linearLayoutMoney = (LinearLayout) view.findViewById(R.id.linearLayout_money);
            textViewProuctDetails = (TextView) view.findViewById(R.id.textView_prouct_details);
            textViewMoney = (TextView) view.findViewById(R.id.textView_money);
            textViewImageTitle = (TextView) view.findViewById(R.id.textView_image_title);
            relativeLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    entity= (MessageDetailsEntity) mDatas.get(getLayoutPosition());
                    switch (type) {
                        case "0"://交易进度提醒
                            intent=new Intent(mContext, ScheduleDetailsActivity.class);
                            intent.putExtra(ApiContstants.ORDER_ID,entity.getOrder_id());
                            intent.putExtra(ApiContstants.GOODS_ID,entity.getGoods_id());
                            mContext.startActivity(intent);
                            break;
                        case "1"://商品消息
//                        goods_type	商品类型 （0-秒杀商品 1-一元换购商品）.没有精选特卖
                            if (entity.getGoods_type().equals("0")){
                                intent=new Intent(mContext, SeckillDetailsActivity.class);
                                intent.putExtra("url",entity.getUrl());
                                mContext.startActivity(intent);
                            }else  if (entity.getGoods_type().equals("1")){
                                intent=new Intent(mContext, BargainBuyDetailsActivity.class);
                                intent.putExtra("url",entity.getUrl());
                                mContext.startActivity(intent);
                            }
                            break;
                        case "2"://消息通知

                            break;
                        default:
                            break;
                    }
                }
            });
        }

        public ViewHolder(View mview) {
            super(mview);
            view = mview;
            assignViews();
        }
    }
}
