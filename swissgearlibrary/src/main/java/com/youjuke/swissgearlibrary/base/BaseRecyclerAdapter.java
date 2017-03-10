package com.youjuke.swissgearlibrary.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * 描述:
 * RecyclerAdapter封装
 *
 * 工程:
 * #0000    Tian Xiao    创建日期: 2016-07-08 10:28
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas ;
    public OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (datas != null) {
            mDatas = datas;
        }else {
            mDatas=new LinkedList<>();
            mDatas = datas;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas.size() > 0) {
            count = mDatas.size();
        }
        return count;
    }

    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
    }

    public void remove(int position) {
        mDatas.remove(position);
    }

    public void removeAll() {
        mDatas.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerViewHolder view, int position, T model);

        void onItemLongClick(BaseRecyclerViewHolder view, int position, T model);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        showViewHolder(holder,position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHOldeHolder(parent, viewType);
    }


    protected abstract void showViewHolder(BaseRecyclerViewHolder holder, int position);

    /***
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseRecyclerViewHolder createViewHOldeHolder(ViewGroup parent, int viewType);



}
