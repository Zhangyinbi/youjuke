package com.youjuke.buildingmaterialmall.app.bargain_buy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.LoginActivity;
import com.youjuke.buildingmaterialmall.entity.BargainBuy;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.FlowLayout;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagAdapter;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagFlowLayout;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-28 17:20
 */
@SuppressLint("ValidFragment")
public class BargainBuyDetailsFragment extends DialogFragment {

    private Dialog dialog;
    private BargainBuy bargainBuy;
    private Context context;
    private RelativeLayout rlPlane;
    private TextView tvProjectPrice;
    private TextView tvProjectSurplus;
    private View lineProjectInfo;
    private TextView tvTitleProjectType;
    private TagFlowLayout tlProjectType;
    private TextView tvBtnSure;
    private TextView tvTitleYjkYiBuTie;
    private ImageView imgProjectIcon;
    private ImageView image_close;
    private int classificationId=0;//默认第一个标签 所以为0
    private boolean isMayBuy=false;

    private void assignViews() {
        RxBus.get().register(this);
        image_close= (ImageView) dialog.findViewById(R.id.img_close);
        rlPlane = (RelativeLayout) dialog.findViewById(R.id.rl_plane);
        tvProjectPrice = (TextView) dialog.findViewById(R.id.tv_project_price);
        tvProjectSurplus = (TextView) dialog.findViewById(R.id.tv_project_surplus);
        lineProjectInfo = dialog.findViewById(R.id.line_project_info);
        tvTitleProjectType = (TextView) dialog.findViewById(R.id.tv_title_project_type);
        tlProjectType = (TagFlowLayout) dialog.findViewById(R.id.tl_project_type);
        tvBtnSure = (TextView) dialog.findViewById(R.id.tv_btn_sure);
        tvTitleYjkYiBuTie = (TextView) dialog.findViewById(R.id.tv_title_yjk_yi_bu_tie);
        imgProjectIcon = (ImageView) dialog.findViewById(R.id.img_project_icon);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @SuppressLint("ValidFragment")
    public BargainBuyDetailsFragment(BargainBuy bargainBuy, Context context) {
        this.context=context;
        this.bargainBuy=bargainBuy;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_bargain_buy_details);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();

        assert window != null;
        window.setWindowAnimations(R.style.AnimBottom);

        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        //lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        assignViews();
        Glide.with(context)
                .load(ApiContstants.IMG_URL + bargainBuy.getGoods_image())
                .into(imgProjectIcon);
        SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                + bargainBuy.getClassifications().get(0).getNum() + "件");
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        tvProjectSurplus.setText(builder);

                tvProjectPrice.setText("￥"+bargainBuy.getClassifications().get(0).getSale_price());
        TagAdapter<BargainBuy.ClassificationsBean> tagAdapter =
                new TagAdapter<BargainBuy.ClassificationsBean>(bargainBuy.getClassifications()) {
                    @Override
                    public View getView(FlowLayout parent, int position, BargainBuy.ClassificationsBean classificationsBean) {
                        TextView tv = (TextView) LayoutInflater.from(dialog.getContext())
                                .inflate(R.layout.flow_layout_item_project_type,
                                        parent, false);
                        tv.setText(classificationsBean.getName());
                        return tv;
                    }
                };

        //默认选中第一个
        tagAdapter.setSelectedList(0);

        tlProjectType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                if (position==classificationId){
                    classificationId=-1;
                    isMayBuy=false;
                    SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                            +0 + "件");
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                            2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvProjectPrice.setText("￥"+0);
                    tvProjectSurplus.setText(builder);

                }else {
                    isMayBuy=isMayBuymethod(position);
                    classificationId=position;
                    SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                            + bargainBuy.getClassifications().get(position).getNum() + "件");
                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                            2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    tvProjectPrice.setText("￥"+bargainBuy.getClassifications().get(position).getSale_price());
                    tvProjectSurplus.setText(builder);
                }

                L.d("点击标签"+classificationId);

                return true;
            }
        });

        isMayBuy=isMayBuymethod(0);

        tvBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMayBuy) {

                    if(BuildingMaterialApp.user==null){//为登录
                        ToastUtil.show(context,"请先登录");
                        startActivity(new Intent(context,LoginActivity.class));
                    }else if (BuildingMaterialApp.user!=null){

                        L.d(""+bargainBuy.getClassifications().get(classificationId).getClassifications_id());
                        RxBus.get().post("getBargainBuyOrder", new BargainBuyOrder(bargainBuy.getClassifications()
                                .get(classificationId).getName(), bargainBuy.getRedemption_id(),"11"));
                    }
                    if (dialog!=null) dialog.cancel();
                }else {//不能购买只能缺货登记
                    if (dialog!=null) dialog.cancel();
                    //发送显示缺货登记显示Dilog并且传送类型ID
                    RxBus.get().post("showBargainBuyRegister",classificationId+"");
                }
            }
        });
        tlProjectType.setAdapter(tagAdapter);
        return dialog;
    }

    /**
     * 判断是否可以购买
     * @return
     */
    private Boolean isMayBuymethod(int potion) {
        if (bargainBuy.getClassifications().get(potion).getNum().equals("0")){
            //购买按钮改变
            //tvBtnSure.setText("缺货登记");
            tvBtnSure.setBackgroundColor(Color.parseColor("#999999"));

            return false;
        }else {
            tvBtnSure.setText("确定");
            tvBtnSure.setBackgroundColor(Color.parseColor("#f82d2d"));
            return true;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        RxBus.get().unregister(this);
    }
}
