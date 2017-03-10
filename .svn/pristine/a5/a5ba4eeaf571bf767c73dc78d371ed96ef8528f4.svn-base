package com.youjuke.buildingmaterialmall.app.message_center;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.MessageCenterEntity;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;
import com.youjuke.swissgearlibrary.utils.L;
import java.util.List;

/**
 * 描述:  消息中心适配
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-21 14:39
 */
public class MessageCenterAdapter extends BaseRecyclerAdapter {

    private MessageCenterEntity messageCenterEntity;

    public MessageCenterAdapter(Context context, List datas) {
        super(context, datas);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
       final ViewHolder viewHolder = (ViewHolder) holder;
            L.d("加载消息"+position +"  集合长度"+mDatas.size());
            if (mDatas.size()>0) {
                messageCenterEntity= (MessageCenterEntity) mDatas.get(position);
                viewHolder.textViewTitle.setText(messageCenterEntity.getTitle());
                if (messageCenterEntity.getImage().length()>0) {
                    Glide.with(mContext).load(messageCenterEntity.getImage()).into(viewHolder.imageIcon);
                }
                viewHolder.textViewTime.setText(messageCenterEntity.getCreatedate());
                if (messageCenterEntity.getUnread_count()>0) {
                    viewHolder.textViewMessageNum.setText(messageCenterEntity.getUnread_count() + "");
                }else {
                    viewHolder.textViewMessageNum.setVisibility(View.GONE);
                }
                viewHolder.textViewLabel.setText(messageCenterEntity.getContent());
                if (position == mDatas.size()-1) {
                    viewHolder.imageViewLine.setVisibility(View.GONE);
                }
            }
        //Item单击事件
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(viewHolder, position, messageCenterEntity );
        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {

        View view = null;
        BaseRecyclerViewHolder holder = null;
        view = mInflater.inflate(R.layout.recycler_item_message, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        private ImageView imageIcon;
        private TextView textViewMessageNum;
        private TextView textViewTitle;
        private TextView textViewTime;
        private TextView textViewLabel;
        private ImageView imageViewLine;
        private LinearLayout linearLayoutItem;
        private View view;

        private void assignViews() {
            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            textViewMessageNum = (TextView) view.findViewById(R.id.textView_message_num);
            textViewTitle = (TextView) view.findViewById(R.id.textView_title);
            textViewTime = (TextView) view.findViewById(R.id.textView_time);
            textViewLabel = (TextView) view.findViewById(R.id.textView_label);
            imageViewLine = (ImageView) view.findViewById(R.id.image_line);
            linearLayoutItem = (LinearLayout) view.findViewById(R.id.linearLayout_item);
            linearLayoutItem.setClickable(true);
        }

        public ViewHolder(View mview) {
            super(mview);
            view = mview;
            assignViews();
        }
    }
}
