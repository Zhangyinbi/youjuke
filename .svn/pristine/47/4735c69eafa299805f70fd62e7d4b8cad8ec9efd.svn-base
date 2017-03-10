package com.youjuke.buildingmaterialmall.module.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.youjuke.buildingmaterialmall.R;

/**
 * 描述:
 *  Recy
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-09 13:40
 * #0001    ``````        添加了标签图片联动
 */


public class ExpandableViewHoldersUtil {

    public static void openH(final ImageView view, final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.start();
                }
            });
            animator.start();

        }
        else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
        }
    }

    public static void closeH(final ImageView view, final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
                @Override public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);

                }
            });
            animator.start();
        }
        else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
        }
    }

    public static interface Expandable {
        public View getExpandView();
        public ImageView getTagImageView();//#0001
    }



    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;

        public void bind(VH holder, int pos) {
            if (pos == _opened) {
                ExpandableViewHoldersUtil.openH(holder.getTagImageView(),holder, holder.getExpandView(), false);

            }
            else {
                ExpandableViewHoldersUtil.closeH(holder.getTagImageView(),holder, holder.getExpandView(), false);

            }
        }

        @SuppressWarnings("unchecked")
        public void toggle(VH holder) {
            if (_opened == holder.getPosition()) {
                _opened = -1;
                ExpandableViewHoldersUtil.closeH(holder.getTagImageView(),holder, holder.getExpandView(), true);
                holder.getTagImageView().setImageResource(R.mipmap.btn_more);//#0001

            }
            else {
                int previous = _opened; //第一次点击并且记录
                _opened = holder.getPosition();
                ExpandableViewHoldersUtil.openH(holder.getTagImageView(),holder, holder.getExpandView(), true);
                holder.getTagImageView().setImageResource(R.mipmap.btn_arrow2);//#0001

                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);
                if (oldHolder != null){
                    ExpandableViewHoldersUtil.closeH(holder.getTagImageView(),oldHolder, oldHolder.getExpandView(), true);
                    oldHolder.getTagImageView().setImageResource(R.mipmap.btn_more);//#0001
                }
            }
        }
    }

}
