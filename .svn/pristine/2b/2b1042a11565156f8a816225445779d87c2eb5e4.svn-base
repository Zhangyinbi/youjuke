package com.youjuke.buildingmaterialmall.app.seckill;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.LoginActivity;
import com.youjuke.buildingmaterialmall.entity.PickProjects;
import com.youjuke.buildingmaterialmall.entity.ProjectInfo;
import com.youjuke.buildingmaterialmall.entity.SeckillDetails;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.FlowLayout;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagAdapter;
import com.youjuke.buildingmaterialmall.widgets.flowlayout.TagFlowLayout;
import com.youjuke.swissgearlibrary.rxbus.RxBus;
import com.youjuke.swissgearlibrary.utils.ToastUtil;
import java.util.List;


/**
 * 描述:
 * <p>底部弹窗Fragment
 * 工程:
 * #0000    Tian Xiao    2016-09-23 13:54
 */
@SuppressLint("ValidFragment")
public class PayDetailFragment extends DialogFragment {

    private RelativeLayout rlPlane;
    private TextView tvProjectPrice;
    private TextView tvProjectSurplus;
    private ImageView imgClose;
    private TextView tvTitleYjkYiBuTie;
    private TextView tvYjkYiBuTie;
    private View lineProjectInfo;
    private TextView tvTitleProjectType;
    private TagFlowLayout tlProjectType;
    private TextView tvBtnSure;
    private TextView textView2;
    private ImageView imgProjectIcon;
    private Dialog dialog;
    private CardView cardNext;
    private CardView cardPrivilegeHint;
    private ProjectInfo projectInfo;
    private Animation slide_left_to_left;
    private Animation slide_right_to_left;
    private RelativeLayout relativeLayoutChoose;

    private SeckillDetails seckillDetailsEntity;
    private Context context;
    private Boolean isFullKill;
    private SeckillDetails.ClassificationsBean mClassifications;
    List<PickProjects> projectsList;
    private boolean isMayBuy=false;



    private int classificationId=0;//默认第一个标签 所以为0

    @SuppressLint("ValidFragment")
    public PayDetailFragment(Context context, SeckillDetails seckillDetails, Boolean isFullKill) {

        this.context = context;
        this.seckillDetailsEntity = seckillDetails;
        this.isFullKill = isFullKill;

    }

    @SuppressLint({"SetTextI18n", "CutPasteId"})
    private void assignViews() {
        RxBus.get().register(this);
        mClassifications=new SeckillDetails.ClassificationsBean();
        slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_out);
        slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_in);
        relativeLayoutChoose = (RelativeLayout) dialog.findViewById(R.id.relative_layout_choose);
        cardPrivilegeHint = (CardView) dialog.findViewById(R.id.card_privilege_hint);
        cardNext = (CardView) dialog.findViewById(R.id.card_next);
        rlPlane = (RelativeLayout) dialog.findViewById(R.id.rl_plane);
        tvProjectPrice = (TextView) dialog.findViewById(R.id.tv_project_price);
        tvProjectSurplus = (TextView) dialog.findViewById(R.id.tv_project_surplus);
        imgClose = (ImageView)dialog.findViewById(R.id.img_close);
        tvTitleYjkYiBuTie = (TextView) dialog.findViewById(R.id.tv_title_yjk_yi_bu_tie);
        tvYjkYiBuTie = (TextView) dialog.findViewById(R.id.tv_yjk_yi_bu_tie);
        lineProjectInfo = dialog.findViewById(R.id.line_project_info);
        tvTitleProjectType = (TextView) dialog.findViewById(R.id.tv_title_project_type);
        tlProjectType = (TagFlowLayout) dialog.findViewById(R.id.tl_project_type);
        tvBtnSure = (TextView) dialog.findViewById(R.id.tv_btn_sure);
        textView2 = (TextView) dialog.findViewById(R.id.textView2);
        imgProjectIcon = (ImageView) dialog.findViewById(R.id.img_project_icon);
        cardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPrivilegeHint.startAnimation(slide_left_to_left);
                cardPrivilegeHint.setVisibility(View.GONE);
                relativeLayoutChoose.startAnimation(slide_right_to_left);
                relativeLayoutChoose.setVisibility(View.VISIBLE);

            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        tvYjkYiBuTie.setText(seckillDetailsEntity.getClassifications().get(0).getSeckill_price());


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Glide.with(context)
                .load(ApiContstants.IMG_URL + seckillDetailsEntity.getGoods_image())
                .into(imgProjectIcon);

        TagAdapter<SeckillDetails.ClassificationsBean> tagAdapter =
                new TagAdapter<SeckillDetails.ClassificationsBean>(seckillDetailsEntity.getClassifications()) {

                    @Override
                    public View getView(FlowLayout parent, int position, SeckillDetails.ClassificationsBean classificationsBean) {
                        TextView tv = (TextView) LayoutInflater.from(dialog.getContext())
                                .inflate(R.layout.flow_layout_item_project_type,
                                        parent, false);
                        tv.setText(classificationsBean.getName());
                        return tv;
                    }

                };

        isMayBuy=isMayBuymethod(0);


        //默认选中第一个
        tagAdapter.setSelectedList(0);
        tlProjectType.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                if (position==classificationId){
                    classificationId=-1;
                    isMayBuy=false;
                    SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                            +0+ "件");

                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                            2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                    textView2.setText(builder);

                }else {
                    classificationId=position;

                    if (isFullKill) {

                        cardPrivilegeHint.setVisibility(View.GONE);
                        relativeLayoutChoose.setVisibility(View.VISIBLE);
                        tvProjectSurplus.setText("");
                        tvTitleYjkYiBuTie.setText("");
                        tvProjectPrice.setText("￥" + seckillDetailsEntity.getClassifications().get(position).getSeckill_price());
                        tvYjkYiBuTie.setText("");

                    }else {

                        tvYjkYiBuTie.setText(seckillDetailsEntity.getClassifications().get(position).getSeckill_price());
                    }

                    SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                            + seckillDetailsEntity.getClassifications().get(position).getNum() + "件");

                    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                            2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                    textView2.setText(builder);

                    isMayBuy=isMayBuymethod(position);
                }


                return true;
            }
        });

        tlProjectType.setAdapter(tagAdapter);

        SpannableStringBuilder builder = new SpannableStringBuilder("剩余"
                + seckillDetailsEntity.getClassifications().get(0).getNum() + "件");

        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0033")),
                2, builder.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        textView2.setText(builder);

        tvBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isMayBuy) {

                    if (BuildingMaterialApp.user==null) {

                        ToastUtil.show(context, "请先登录");
                        startActivity(new Intent(context, LoginActivity.class));

                    }else if (BuildingMaterialApp.user!=null){

                    RxBus.get().post("getSeckillOrder",new mOrderInfo(
                            Integer.valueOf(seckillDetailsEntity.getClassifications()
                            .get(classificationId).getClassifications_id())
                            , isFullKill));
                    }

                    dialog.cancel();
                }
            }


        });

        if (isFullKill) {

            cardPrivilegeHint.setVisibility(View.GONE);
            relativeLayoutChoose.setVisibility(View.VISIBLE);
            tvProjectSurplus.setText("");
            tvTitleYjkYiBuTie.setText("");
            tvProjectPrice.setText("￥" + seckillDetailsEntity.getClassifications().get(0).getSeckill_price());
            tvYjkYiBuTie.setText("");

        }


    }


    class mOrderInfo{
        public int classifications_id;
        public boolean seckill_in_full;
        public mOrderInfo(int classifications_id, boolean seckill_in_full) {
            this.classifications_id = classifications_id;
            this.seckill_in_full = seckill_in_full;
        }
    }

    /**
     * 判断是否可以购买
     * @return
     */
    private Boolean isMayBuymethod(int potion) {
        if (seckillDetailsEntity.getClassifications().get(potion).getNum().equals("0")){

            tvBtnSure.setText("商品已售完");
            tvBtnSure.setBackgroundColor(Color.parseColor("#999999"));
            return false;
        }else {
            tvBtnSure.setText("确定");
            tvBtnSure.setBackgroundColor(Color.parseColor("#f82d2d"));
            return true;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_privilege_hint);
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


        return dialog;

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        RxBus.get().unregister(this);
        super.onCancel(dialog);
    }
}
