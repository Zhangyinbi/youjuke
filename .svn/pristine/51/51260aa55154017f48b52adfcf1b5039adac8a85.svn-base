package com.youjuke.buildingmaterialmall.app.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.Inspiration;
import com.youjuke.buildingmaterialmall.widgets.InspirationView;
import com.youjuke.swissgearlibrary.utils.DensityUtil;
import com.youjuke.swissgearlibrary.utils.ScreenUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.List;

/**
 * 描述: 寻找灵感Adapter
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-16 15:19
 */

public class InspirationAdapter extends RecyclerView.Adapter<InspirationAdapter.InspirationVH> {
    private Context context;
    private List<Inspiration> inspirations;
    private InspirationView inspirationView;
    private int screenWidth;
    private int dix;

    public InspirationAdapter(Context context, List<Inspiration> inspirations) {
        this.context = context;
        this.inspirations = inspirations;
    }

    @Override
    public InspirationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InspirationVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_inspiration, null));
    }

    @Override
    public void onBindViewHolder(InspirationVH holder, final int position) {

        //获取屏幕的宽
        screenWidth = ScreenUtil.getScreenWidth(context);
        int height = DensityUtil.dp2px(context, 100);
        //recycleviw之间的间距
        int item = DensityUtil.dp2px(context, 10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2 - item, height);
        holder.mImgItemInspiration.setLayoutParams(layoutParams);

        Glide.with(context).load(inspirations.get(position).getImg_url()).into(holder.mImgItemInspiration);
        holder.mTvItemInspirationTitle.setText(inspirations.get(position).getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inspirationView != null && inspirationView.isShowing()) {
                    inspirationView.dismiss();
                    return;
                }
                if (inspirations.get(position) == null || inspirations.get(position).getImgs().size() <= 0) {
                    ToastUtil.show(context, "没有找到灵感图片");
                    return;
                }
                inspirationView = new InspirationView(context, inspirations.get(position));
                inspirationView.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return inspirations.size();
    }

    class InspirationVH extends RecyclerView.ViewHolder {
        private ImageView mImgItemInspiration;
        private TextView mTvItemInspirationTitle;
        private View view;

        private void assignViews() {
            mImgItemInspiration = (ImageView) view.findViewById(R.id.img_item_inspiration);
            mTvItemInspirationTitle = (TextView) view.findViewById(R.id.tv_item_inspiration_title);
        }

        public InspirationVH(View itemView) {
            super(itemView);
            view = itemView;
            assignViews();
        }
    }
}
