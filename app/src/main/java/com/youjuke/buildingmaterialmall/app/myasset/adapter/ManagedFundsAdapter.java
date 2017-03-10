package com.youjuke.buildingmaterialmall.app.myasset.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ManagedFundsEntity;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * 描述: 托管金额Item
 * ------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2016-12-19 13:42
 */
public class ManagedFundsAdapter extends BaseRecyclerAdapter<ManagedFundsEntity.ProductBean> {

    private ManagedFundsEntity.ProductBean productBean;

    public ManagedFundsAdapter(Context context, List<ManagedFundsEntity.ProductBean> datas) {
        super(context, datas);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mDatas.size() > 0) {
            productBean = mDatas.get(position);
            viewHolder.tVTitle.setText(productBean.getPlay_num());
            switch (productBean.getPlay_type_status()) {
                //(0/1/2/3/4)(同意付款，等待确认，已打款,结算余额未发放，结算余额已领取)
                case 0:
                    viewHolder.buttonVerify.setVisibility(View.VISIBLE);
                    viewHolder.tVTitleAwait.setVisibility(View.GONE);
                    viewHolder.lLIntoTrue.setVisibility(View.GONE);
                    viewHolder.tV_title_not_grant.setVisibility(View.GONE);
                    viewHolder.tV_title_received.setVisibility(View.GONE);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(viewHolder, position, productBean);
                    }
                    break;
                case 1:
                    viewHolder.buttonVerify.setVisibility(View.GONE);
                    viewHolder.tVTitleAwait.setVisibility(View.VISIBLE);
                    viewHolder.lLIntoTrue.setVisibility(View.GONE);
                    viewHolder.tV_title_not_grant.setVisibility(View.GONE);
                    viewHolder.tV_title_received.setVisibility(View.GONE);
                    break;
                case 2:
                    viewHolder.buttonVerify.setVisibility(View.GONE);
                    viewHolder.tVTitleAwait.setVisibility(View.GONE);
                    viewHolder.tV_title_not_grant.setVisibility(View.GONE);
                    viewHolder.tV_title_received.setVisibility(View.GONE);
                    viewHolder.lLIntoTrue.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    viewHolder.buttonVerify.setVisibility(View.GONE);
                    viewHolder.tVTitleAwait.setVisibility(View.GONE);
                    viewHolder.lLIntoTrue.setVisibility(View.GONE);
                    viewHolder.tV_title_received.setVisibility(View.GONE);
                    viewHolder.tV_title_not_grant.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    viewHolder.buttonVerify.setVisibility(View.GONE);
                    viewHolder.tVTitleAwait.setVisibility(View.GONE);
                    viewHolder.lLIntoTrue.setVisibility(View.GONE);
                    viewHolder.tV_title_not_grant.setVisibility(View.GONE);
                    viewHolder.tV_title_received.setVisibility(View.VISIBLE);
                    break;
            }
            viewHolder.tVPlanDate.setText(productBean.getExpect_play_date());
            viewHolder.tVPlanMenoy.setText(productBean.getPlay_money());
            viewHolder.tVRealityDate.setText(productBean.getPlay_date());
            viewHolder.tVRealityMoney.setText(productBean.getReal_play_money());
        }
    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        view = mInflater.inflate(R.layout.recycler_item_managed_funds, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        private View view;
        private TextView tVTitle;
        private Button buttonVerify;
        private LinearLayout lLIntoTrue;
        private TextView tVPlanDate;
        private TextView tVPlanMenoy;
        private TextView tVRealityDate;
        private TextView tVRealityMoney;
        private TextView tVTitleAwait;
        private TextView tV_title_not_grant;
        private TextView tV_title_received;

        private void assignViews() {
            tVTitle = (TextView) view.findViewById(R.id.tV_title);
            buttonVerify = (Button) view.findViewById(R.id.button_verify);
            lLIntoTrue = (LinearLayout) view.findViewById(R.id.lL_into_true);
            tVPlanDate = (TextView) view.findViewById(R.id.tV_plan_date);
            tVPlanMenoy = (TextView) view.findViewById(R.id.tV_plan_menoy);
            tVRealityDate = (TextView) view.findViewById(R.id.tV_reality_date);
            tVRealityMoney = (TextView) view.findViewById(R.id.tV_reality_money);
            tVTitleAwait = (TextView) view.findViewById(R.id.tV_title_await);
            tV_title_not_grant = (TextView) view.findViewById(R.id.tV_title_not_grant);
            tV_title_received = (TextView) view.findViewById(R.id.tV_title_received);
        }

        public ViewHolder(View mview) {
            super(mview);
            view = mview;
            assignViews();
        }
    }

}
