package com.youjuke.buildingmaterialmall.app.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.TopNewsOfDecoration;

import java.util.List;

/**
 * 描述: 装修头条Adapter
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-16 15:32
 */

public class TopNewsOfDecorationAdapter extends RecyclerView.Adapter<TopNewsOfDecorationAdapter.TopNewsOfDecorationVH> {

    private Context context;
    private List<TopNewsOfDecoration> topNewsOfDecorations;

    public TopNewsOfDecorationAdapter(Context context, List<TopNewsOfDecoration> topNewsOfDecorations) {
        this.context = context;
        this.topNewsOfDecorations = topNewsOfDecorations;
    }

    @Override
    public TopNewsOfDecorationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopNewsOfDecorationVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_top_news_decoration,null));
    }

    @Override
    public void onBindViewHolder(TopNewsOfDecorationVH holder, final int position) {
        Glide.with(context).load(topNewsOfDecorations.get(position).getImg_url()).into(holder.mImgTopNewsDecoration);
        holder.mTvTopNewsTitle.setText(""+topNewsOfDecorations.get(position).getTitle());
        holder.mTvTopNewsViewedCount.setText(""+topNewsOfDecorations.get(position).getBrowse());
        holder.mTvTopNewsDate.setText(""+topNewsOfDecorations.get(position).getDate());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TopNewsOfDecorationDetailsActivity.class);
                intent.putExtra("url",topNewsOfDecorations.get(position).getDetail_url());
                intent.putExtra("title",context.getString(R.string.top_news_of_decoration_details));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topNewsOfDecorations.size() <= 0 ? 0 : topNewsOfDecorations.size();
    }

    class TopNewsOfDecorationVH extends RecyclerView.ViewHolder{
        private ImageView mImgTopNewsDecoration;
        private TextView mTvTopNewsTitle;
        private TextView mTvTopNewsViewedCount;
        private TextView mTvTopNewsDate;
        private View view;

        private void assignViews() {
            mImgTopNewsDecoration = (ImageView) view.findViewById(R.id.img_top_news_decoration);
            mTvTopNewsTitle = (TextView) view.findViewById(R.id.tv_top_news_title);
            mTvTopNewsViewedCount = (TextView) view.findViewById(R.id.tv_top_news_viewed_count);
            mTvTopNewsDate = (TextView) view.findViewById(R.id.tv_top_news_date);
        }

        public TopNewsOfDecorationVH(View itemView) {
            super(itemView);
            view = itemView;
            assignViews();
        }
    }
}
