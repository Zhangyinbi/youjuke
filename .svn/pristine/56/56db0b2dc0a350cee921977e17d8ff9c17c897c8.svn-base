package com.youjuke.swissgearlibrary.widgets.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.youjuke.swissgearlibrary.R;


/**
 * 描述:
 *  Doalog加载动画
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-19 19:47
 */
public class ShapeLoadingDialog {

    private Context mContext;
    private Dialog mDialog;
    private LoadingView mLoadingView;
    private View mDialogContentView;
    private Activity activity;


    public ShapeLoadingDialog(Context context) {
        this.mContext=context;
        activity= (Activity) context;
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        mDialog = new Dialog(mContext, R.style.custom_dialog);
        mDialogContentView= LayoutInflater.from(mContext).inflate(R.layout.layout_dialog,null);
        mLoadingView= (LoadingView) mDialogContentView.findViewById(R.id.loadView);
        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(true);
        /*mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN&&
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.finishAfterTransition();//不然返回时没有过度动画
                    }
                }catch (Exception e){
                }
                activity.finish();
            }
        });*/
        mDialog.setCancelable(true);
    }

    public void setBackground(int color){
        GradientDrawable gradientDrawable= (GradientDrawable) mDialogContentView.getBackground();
        gradientDrawable.setColor(color);
    }

    public void setLoadingText(CharSequence charSequence){
        mLoadingView.setLoadingText(charSequence);
    }

    public void show(){
        mDialog.show();

    }


    public boolean isShowing(){
        return mDialog.isShowing();
    }
    public void dismiss(){
        mDialog.dismiss();
    }

    public Dialog getDialog(){
        return  mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
    }

}
