package com.youjuke.buildingmaterialmall.app.schedule_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ScheduleDetails;
import com.youjuke.buildingmaterialmall.module.anim.ExpandableViewHoldersUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.List;


/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-22 14:08
 */
public class ScheduleDetailsAdapter extends RecyclerView.Adapter<ScheduleDetailsAdapter.mViewHolder> {


    private ExpandableViewHoldersUtil.KeepOneH<mViewHolder> keepOne;
    //private int tagFlog=-1;

    private Context mcontext;
    private ScheduleDetails scheduleDetails;
    private int done;


    public ScheduleDetailsAdapter(ScheduleDetails mScheduleDetai, Context context) {
        scheduleDetails = mScheduleDetai;
        mcontext = context;
        keepOne = new ExpandableViewHoldersUtil.KeepOneH<mViewHolder>();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new mViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_schedule_details, parent, false));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.bind(position);
        holder.addList(scheduleDetails.getInfo().get(position).getNote());

//    user_id # 用户编号
//    order_id # 订单编号
//    goods_id # 订单对应的商品编号
//    first_node_set_time # 第一节点确认时间
//    created # 创建时间
//    data # info数组主键为节点（参考文档最下的键值对数组）
//    note # 当前节点的备注信息
//    day # 预计天数
//    node_type # 节点类型
//    node_type # 节点类型
//    plan_date # 节点排期
//    done # 0-未开始 2-进行中 1-已完成
//    tips # 友情提示
//    actual_date # 实际完成时间
        holder.textTitle.setText(scheduleDetails.getInfo().get(position).getNode());
        holder.textTime.setText(scheduleDetails.getInfo().get(position).getPlan_date());

                done = Integer.valueOf(scheduleDetails.getInfo().get(position).getDone());
                if (done == 0) {

                    holder.textState.setText("未开始");
                    holder.imageTitle.setImageResource(R.mipmap.btn_wks);
                    holder.textTitle.setTextColor(Color.parseColor("#cccccc"));
                    holder.textTime.setTextColor(Color.parseColor("#cccccc"));
                    holder.frameHint.setVisibility(View.GONE);
                    holder.cardItem.setClickable(false);

                    holder.layoutState.setBackgroundResource(R.mipmap.btn_tx4);
                } else if (done == 2) {
                    holder.layoutState.setBackgroundResource(R.mipmap.btn_tx3);
                    holder.textState.setText("进行中");
                    holder.imageTitle.setImageResource(R.mipmap.btn_jxz);
                    if (scheduleDetails.getInfo().get(position).getUrl_tips().length()>0) {

                        holder.frameHint.setVisibility(View.VISIBLE);
                    }else {
                        holder.frameHint.setVisibility(View.GONE);

                    }

                }else if(done ==1) {
                    holder.frameHint.setVisibility(View.GONE);
                    holder.imageTitle.setImageResource(R.mipmap.btn_ywc);
                    holder.layoutState.setBackgroundResource(R.mipmap.btn_tx2);
                    holder.textState.setText("已完成");
                    if (scheduleDetails.getInfo().get(position).getNode_tips() != null) {
                        int mStatus=Integer.valueOf(scheduleDetails.getInfo().get(position).getNode_tips().getStatus());

                        if (mStatus == 2) {
                            holder.layoutState.setBackgroundResource(R.mipmap.btn_tx1);
                            holder.textState.setText(scheduleDetails.getInfo().get(position)
                                    .getNode_tips().getRemark());

                        } else if(mStatus == 1){
                            holder.textState.setText(scheduleDetails.getInfo().get(position)
                                    .getNode_tips().getRemark());
                        }
                    }
                }



    }


    public  void addAll(ScheduleDetails scheduleDetails) {
        this.scheduleDetails=scheduleDetails;

    }





    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public int getItemCount() {
        if(scheduleDetails!=null) {
            return scheduleDetails.getInfo().size();
        }else
        {
            return 0;
        }
    }

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            ExpandableViewHoldersUtil.Expandable {

        private ItemScheduleDetailsAdatpter adatpter;

        private View view;

        private CardView cardItem;
        private ImageView imageTitle;
        private TextView textTitle;
        private FrameLayout frameHint;
        private TextView textTime;
        private RelativeLayout layoutState;
        private TextView textState;
        private ImageView imageTag;
        private RecyclerView cardSpecificDetailsRecyclerView;

        private List<ScheduleDetails.InfoBean.NoteBean> noteBeanList;

        private void assignViews() {

            cardItem = (CardView) view.findViewById(R.id.card_item);
            imageTitle = (ImageView) view.findViewById(R.id.image_title);
            textTitle = (TextView) view.findViewById(R.id.text_title);
            frameHint = (FrameLayout) view.findViewById(R.id.frame_hint);
            textTime = (TextView) view.findViewById(R.id.text_time);
            layoutState = (RelativeLayout) view.findViewById(R.id.relative_layout_state);
            textState = (TextView) view.findViewById(R.id.text_state);
            imageTag = (ImageView) view.findViewById(R.id.image_tag);
            cardSpecificDetailsRecyclerView = (RecyclerView) view.findViewById(R.id.card_specific_details_recycler_view);
        }


        public void bind(int pos) {

            keepOne.bind(this, pos);

        }

        public mViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            assignViews();
            itemView.setOnClickListener(this);

            adatpter = new ItemScheduleDetailsAdatpter(mcontext, noteBeanList);
            cardSpecificDetailsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            cardSpecificDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
            cardSpecificDetailsRecyclerView.setAdapter(adatpter);
            frameHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mcontext,FriendshipTipsActivity.class);

                    if (scheduleDetails.getInfo().get(getLayoutPosition()).getUrl_tips().length()>0) {
                        intent.putExtra("FriendShipUrl",
                                scheduleDetails.getInfo().get(getLayoutPosition()).getUrl_tips());
                        mcontext.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {


                    keepOne.toggle(this);


        }


        public void addList(List<ScheduleDetails.InfoBean.NoteBean> noteBeen){
            noteBeanList=noteBeen;
            //noinspection unchecked
            adatpter.addItemTop(noteBeanList);
        }
        //隐藏部分
        @Override
        public View getExpandView() {
            return cardSpecificDetailsRecyclerView;
        }

        //标签部分
        @Override
        public ImageView getTagImageView() {
            return imageTag;
        }
    }
}
