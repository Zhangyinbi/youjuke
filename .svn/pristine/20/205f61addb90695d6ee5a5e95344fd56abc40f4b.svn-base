package com.youjuke.buildingmaterialmall.app.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.buy.PayWayActivity;
import com.youjuke.buildingmaterialmall.entity.Order;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.widgets.HintDialog;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.MoneySimpleFormat;

/**
 * 描述: 订单列表适配器
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-18 19:49
 */
public class OrderAdapter extends RecyclerArrayAdapter<Order> {

    private Context context;
    private HintDialog mHintDialog = null;

    public OrderAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(parent);
    }

    class OrderViewHolder extends BaseViewHolder<Order>{

        private TextView tvOrderTime;
        private ImageView imgDelete;
        private ImageView imgOrder;
        private TextView titleOrderNo;
        private TextView orderNo;
        private TextView titleOrderStatus;
        private TextView orderStatus;
        private TextView titleOrderMoney;
        private TextView orderMoney;
        private TextView preParePrice;
        private Button btn_order_list_do;

        private void assignViews() {
            tvOrderTime = $(R.id.tv_order_time);
            imgDelete = $(R.id.img_delete);
            imgOrder = $(R.id.img_order);
            orderNo = $(R.id.order_no);
            orderStatus = $(R.id.order_status);
            orderMoney = $(R.id.order_money);
            btn_order_list_do = $(R.id.btn_order_list_do);
            preParePrice=$(R.id.tv_prepare_price);
        }



        public OrderViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_item_order);
            assignViews();
        }

        @Override
        public void setData(Order order) {
            tvOrderTime.setText(order.getCreatedate());
            orderNo.setText(order.getNo());
            orderStatus.setText(order.getStatus());
            orderMoney.setText(MoneySimpleFormat.getMoneyType(order.getPrice()));
            if (order.getDown_payment().equals("10")){
                preParePrice.setVisibility(View.VISIBLE);
            }
            Glide.with(context).load(ApiContstants.IMG_URL+order.getImage())
                    .placeholder(R.mipmap.btn_tp).centerCrop().into(imgOrder);
            if ("1".equals(order.getStatus_id().trim())){//待付款
                orderStatus.setTextColor(Color.parseColor("#f22f2f"));
                btn_order_list_do.setText("立即付款");
                btn_order_list_do.setTextColor(context.getResources().getColor(R.color.f22d2d));
                btn_order_list_do.setBackgroundResource(R.drawable.selector_text_btn_red);
                btn_order_list_do.setOnClickListener(new MyOnclickListener(2,order));
                imgDelete.setVisibility(View.GONE);
            }else if("2".equals(order.getStatus_id().trim())){//待发货
                orderStatus.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setText("查看订单");
                btn_order_list_do.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setBackgroundResource(R.drawable.selector_text_btn_black);
                btn_order_list_do.setOnClickListener(new MyOnclickListener(1,order));
                imgDelete.setVisibility(View.GONE);
            }else if("3".equals(order.getStatus_id().trim())){//待收货
                orderStatus.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setText("确认收货");
                btn_order_list_do.setTextColor(context.getResources().getColor(R.color.f22d2d));
                btn_order_list_do.setBackgroundResource(R.drawable.selector_text_btn_red);
                btn_order_list_do.setOnClickListener(new MyOnclickListener(3,order));
                imgDelete.setVisibility(View.GONE);
            }else if("4".equals(order.getStatus_id().trim())){//待评价
                orderStatus.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setText("查看订单");
                btn_order_list_do.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setBackgroundResource(R.drawable.selector_text_btn_black);
                btn_order_list_do.setOnClickListener(new MyOnclickListener(1,order));
                imgDelete.setVisibility(View.GONE);
            }else if("5".equals(order.getStatus_id().trim())){//已取消
                orderStatus.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setTextColor(context.getResources().getColor(R.color.black));
                btn_order_list_do.setBackgroundResource(R.drawable.selector_text_btn_black);
                btn_order_list_do.setOnClickListener(new MyOnclickListener(1,order));
                btn_order_list_do.setText("查看订单");
                imgDelete.setVisibility(View.VISIBLE);
                imgDelete.setOnClickListener(new MyOnDeleteListener(order));
            }
        }
    }

    class MyOnDeleteListener implements View.OnClickListener{

        private Order order;

        public MyOnDeleteListener(Order order) {
            this.order  = order;
        }

        @Override
        public void onClick(View view) {
            //初始化提示框
            if (mHintDialog == null) {
                mHintDialog = new HintDialog(context);
            }
            if (mHintDialog.isShowing()){
                mHintDialog.dismiss();
            }
            mHintDialog.setTitleText("确认删除这个订单吗").setDefultCancelListener()
                    .setConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mHintDialog.dismiss();
                            //调用OrderFragment里的deleteOrder();执行删除
                            RxBus.get().post("deleteOrder",order);
                        }
                    }).show();
        }
    }

    class MyOnclickListener implements View.OnClickListener{
        private int type;
        private Order order;

        public MyOnclickListener(int type, Order order) {
            this.type = type;
            this.order = order;
        }

        @Override
        public void onClick(View view) {
            switch (type) {
                case 1://查看订单
                    Intent intent = new Intent(context,OrderDetailsActivity.class);
                    intent.putExtra("order_id",order.getId());
                    intent.putExtra("status_id",order.getStatus_id());
                    context.startActivity(intent);
                    break;
                case 2://立即付款
                    Intent intent2 = new Intent(context,PayWayActivity.class);
                    intent2.putExtra("order_id",order.getId());
                    //1为一元夺宝，默认是0
                    intent2.putExtra("type","0");
                    context.startActivity(intent2);
                    break;
                case 3://确认收货
                    if (mHintDialog == null) {
                        mHintDialog = new HintDialog(context);
                    }
                    if (mHintDialog.isShowing()){
                        mHintDialog.dismiss();
                    }
                    mHintDialog.setTitleText("确认收货吗").setDefultCancelListener()
                            .setConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mHintDialog.dismiss();
                                    //调用OrderFragment里的confirmReceipt();执行删除
                                    RxBus.get().post("confirmReceipt",order);
                                }
                            }).show();
                    break;
                default:
                    break;
            }
        }
    }



}
