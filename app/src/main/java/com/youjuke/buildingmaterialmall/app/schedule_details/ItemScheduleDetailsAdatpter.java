package com.youjuke.buildingmaterialmall.app.schedule_details;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ScheduleDetails;
import com.youjuke.buildingmaterialmall.module.anim.ExpandableViewHoldersUtil;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * 描述:
 *  item 里面的详细情况适配
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-22 10:55
 */
public class ItemScheduleDetailsAdatpter extends BaseRecyclerAdapter {

    private int index;
    private  ScheduleDetails.InfoBean.NoteBean noteBean;

    public ItemScheduleDetailsAdatpter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        final ViewHolder viewHolder= (ViewHolder) holder;
            if (mDatas!=null) {
                noteBean = (ScheduleDetails.InfoBean.NoteBean) mDatas.get(position);
                viewHolder.textTitle.setText(noteBean.getNote());
                viewHolder.textDetails.setText(noteBean.getCreated());
            }
    }


    @Override
    public int getItemViewType(int position) {

        if (position==0){
            index=position;
            return 0;
        }else {
            index=position;
            return position;

        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        switch (viewType){
            case 0:
                view = mInflater.inflate(R.layout.recycler_item_card_specific_detail_start, parent, false);
                break;
            default:
                view = mInflater.inflate(R.layout.recycler_item_card_specific_detail_course, parent, false);
                break;

        }

        holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public int getItemCount() {
        if (mDatas!=null) {
            return mDatas.size();
        }else {
            return 0;
        }
    }

    private class ViewHolder extends BaseRecyclerViewHolder{


        private TextView textTitle;

        private TextView textDetails;




        private void assignViews() {
            textTitle = (TextView)view. findViewById(R.id.text_title);
            textDetails = (TextView) view.findViewById(R.id.text_details);
        }

        private View view;

        public ViewHolder(View mview) {
            super(mview);
            view=mview;
                assignViews();

        }
    }
}
