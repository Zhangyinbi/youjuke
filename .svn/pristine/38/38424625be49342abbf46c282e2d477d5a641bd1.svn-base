package com.youjuke.buildingmaterialmall.widgets.banner;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyActivity;
import com.youjuke.buildingmaterialmall.app.common_web.CommonActivity;
import com.youjuke.buildingmaterialmall.entity.Index;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * <p/>
 * 自定义Banner无限轮播控件
 * #0000  tian xiao
 * #0001  tian xiao 轮播后退有问题。停用
 */
@Deprecated
public class BannerView extends RelativeLayout implements BannerAdapter.ViewPagerOnItemClickListener
{

    private ViewPager viewPager;

    private LinearLayout points;
    private SwipeRefreshLayout swipeRefreshLayout;

    private void assignViews() {
        viewPager = (ViewPager) findViewById(R.id.widget_banner_viewpager);
        points = (LinearLayout) findViewById(R.id.widget_banner_points_group);
    }


    private CompositeSubscription compositeSubscription;

    //默认轮播时间，3s
    private int delayTime = 5;

    private List<ImageView> imageViewList;

    private BannerAdapter bannerAdapter;

    private Context context;

    private List<Index.BannerImagesBean> bannerList;

    //选中显示Indicator
    private int selectRes = R.drawable.shape_dots_select;

    //非选中显示Indicator
    private int unSelcetRes = R.drawable.shape_dots_default;

    public BannerView(Context context)
    {

        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs)
    {

        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr)
    {

        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        assignViews();
        imageViewList = new ArrayList<>();
    }

    private LinearLayout.LayoutParams params;

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public BannerView delayTime(int time)
    {

        this.delayTime = time;
        return this;
    }

    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes)
    {

        this.selectRes = selectRes;
        this.unSelcetRes = unselcetRes;
    }


    /**
     * 屏幕像素
     * @param context
     * @param dipValue
     * @return
     */
    public  int dip2px(Context context, float dipValue)
    {

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        if (swipeRefreshLayout!=null) {
            this.swipeRefreshLayout = swipeRefreshLayout;
        }
    }


    /**
     * 图片轮播需要传入参数
     */
    public void build(List<Index.BannerImagesBean> list)
    {

        destory();

        if (list.size() == 0)
        {
            this.setVisibility(GONE);
            return;
        }

        bannerList = new ArrayList<>();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();
        if (pointSize == 2)
        {
            bannerList.addAll(list);
        }
        //判断是否清空 指示器点
        if (points.getChildCount() != 0)
        {
            points.removeAllViewsInLayout();
        }

        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++)
        {
            View dot = new View(context);
            dot.setBackgroundResource(unSelcetRes);
            params = new LinearLayout.LayoutParams(
                    dip2px(context, 5),
                    dip2px(context, 5));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }

        points.getChildAt(0).setBackgroundResource(selectRes);

        for (int i = 0; i < bannerList.size(); i++)
        {
            ImageView hImageView = new ImageView(context);
            Glide.with(context)
                    .load(bannerList.get(i).getImg())
                    .into(hImageView);
            imageViewList.add(hImageView);
        }

        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners();

        if (swipeRefreshLayout!=null) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int pos) {

                    pos = pos % pointSize;
                    for (int i = 0; i < points.getChildCount(); i++) {
                        points.getChildAt(i).setBackgroundResource(unSelcetRes);
                    }
                    points.getChildAt(pos).setBackgroundResource(selectRes);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                    switch (state) {
                        case ViewPager.SCROLL_STATE_IDLE:
                            if (isStopScroll) {
                                startScroll();
                            }
                            break;
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            stopScroll();
                            compositeSubscription.unsubscribe();
                            break;
                    }
                }
            });
        }
        bannerAdapter = new BannerAdapter(imageViewList);
        viewPager.setAdapter(bannerAdapter);

        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        swipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setmViewPagerOnItemClickListener(this);

        //图片开始轮播
        startScroll();
    }

    private boolean isStopScroll = false;

    /**
     * 图片开始轮播
     */
    private void startScroll()
    {

        compositeSubscription = new CompositeSubscription();
        isStopScroll = false;
        Subscription subscription = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>()
                {

                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {


                    }

                    @Override
                    public void onNext(Long aLong)
                    {

                        if (isStopScroll)
                        {
                            return;
                        }
                        isStopScroll = true;
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
        compositeSubscription.add(subscription);
    }

    /**
     * 图片停止轮播
     */
    private void stopScroll()
    {
        isStopScroll = true;
    }

    public void destory()
    {

        if (compositeSubscription != null)
        {
            compositeSubscription.unsubscribe();
        }
    }

    /**
     * 设置ViewPager的Item点击回调事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position)
    {

        if (position == 0)
        {
            position = bannerList.size() - 1;
        } else
        {
            position -=1;

        }
        Index.BannerImagesBean bean = bannerList.get(position);
        if (bean.getType()==0){
            Intent intent=new Intent(context, CommonActivity.class);
            intent.putExtra("url",bean.getLink());
            intent.putExtra("title",bean.getTitle());
            context.startActivity(intent);
        }else if(bean.getType()==1){
            if (bean.getTarget().equals("一元超值购")) {

                Intent intent=new Intent(context, BargainBuyActivity.class);
                intent.putExtra("link",bean.getLink());
                context.startActivity(intent);
            }
        }
    }
}
