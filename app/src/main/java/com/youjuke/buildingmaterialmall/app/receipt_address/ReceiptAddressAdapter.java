package com.youjuke.buildingmaterialmall.app.receipt_address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.AddressList;
import com.youjuke.swissgearlibrary.base.BaseRecyclerAdapter;
import com.youjuke.swissgearlibrary.base.BaseRecyclerViewHolder;
import java.util.List;

/**
 * 描述:
 * 地址列表适配
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-18 17:42
 */
public class ReceiptAddressAdapter extends BaseRecyclerAdapter<AddressList> {
    private int positionDefault;
    private AddressList addressList;
    private List<AddressList> mAddressLists;

    public ReceiptAddressAdapter(Context context, List<AddressList> datas) {
        super(context, datas);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        mAddressLists=mDatas;
        positionDefault=position;
        addressList=mAddressLists.get(position);



        if (addressList.isIs_default()) {
            viewHolder.imageSetDefault.setImageResource(R.mipmap.btn_ok);
            viewHolder.textSetDefault.setText("默认地址");
            viewHolder.textSetDefault.setTextColor(Color.parseColor("#00C24F"));


        }else {
            viewHolder.imageSetDefault.setImageResource(R.mipmap.btn_an);
            viewHolder.textSetDefault.setText("设为默认地址");
            viewHolder.textSetDefault.setTextColor(Color.parseColor("#cccccc"));

        }



        viewHolder.textReceiveName.setText(addressList.getReceive_name());
        viewHolder.textMobile.setText(addressList.getMobile());
        viewHolder.textAddress.setText(addressList.getProvince()+addressList.getDistrict()+addressList.getAddress());



        //Item单击事件
        if (mOnItemClickListener != null) {

            mOnItemClickListener.onItemClick(viewHolder,position,addressList);
        }


    }

    @Override
    protected BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        view = mInflater.inflate(R.layout.recycler_item_recript_address, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }


    class ViewHolder extends BaseRecyclerViewHolder  {

        private View view;
        private Boolean isDefault=false;
        private TextView textReceiveName;
        private TextView textMobile;
        private TextView textAddress;
        private LinearLayout linearLayoutSetDefault;
        private ImageView imageSetDefault;
        private TextView textSetDefault;
        private LinearLayout linearlayoutCompile;
        private LinearLayout linearlayoutDelete;

        public ViewHolder(View mview) {
            super(mview);
            view=mview;
            assignViews();
        }

        private void assignViews() {
            textReceiveName = (TextView) view.findViewById(R.id.text_receive_name);
            textMobile = (TextView) view.findViewById(R.id.text_mobile);
            textAddress = (TextView) view.findViewById(R.id.text_address);
            linearLayoutSetDefault = (LinearLayout) view.findViewById(R.id.linearLayout_set_default);
            imageSetDefault = (ImageView) view.findViewById(R.id.image_set_default);
            textSetDefault = (TextView)view. findViewById(R.id.text_set_default);
            linearlayoutCompile = (LinearLayout) view.findViewById(R.id.linearlayout_compile);
            linearlayoutDelete = (LinearLayout) view.findViewById(R.id.linearlayout_delete);
        }


    }
}
