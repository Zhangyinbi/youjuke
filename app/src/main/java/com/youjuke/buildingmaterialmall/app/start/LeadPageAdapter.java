package com.youjuke.buildingmaterialmall.app.start;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-10-11 15:50
 */
public class LeadPageAdapter  extends PagerAdapter{


    //获得导航页的图片集
    private List<View> views;

    public LeadPageAdapter(List<View> views)
    {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewGroup)container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //对不在界面内的导航页进行删除
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }


    public void notifyDataSetChanged(List<View> views){
        this.views=views;
        notifyDataSetChanged();
    }

}
