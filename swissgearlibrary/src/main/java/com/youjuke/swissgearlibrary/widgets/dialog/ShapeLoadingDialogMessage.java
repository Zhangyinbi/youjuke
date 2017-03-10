package com.youjuke.swissgearlibrary.widgets.dialog;

import android.content.Context;

import com.youjuke.swissgearlibrary.utils.L;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-11 15:56
 */
public class ShapeLoadingDialogMessage {
    private Context mContext;
    private ShapeLoadingDialog dialog;

    public ShapeLoadingDialogMessage(Context context) {
        mContext = context;
    }

    public static ShapeLoadingDialogMessage newInstance(Context context) {
        return new ShapeLoadingDialogMessage(context);
    }

    /**
     * 刷新的dialog是否正在显示
     * @return
     */
    public boolean isShowing(){
        if (dialog!=null)
            return dialog.isShowing();
        else
            return false;
    }
    public void showDialog() {
        if (dialog == null) dialog = new ShapeLoadingDialog(mContext);
        if (!dialog.isShowing()) dialog.show();
    }

    public void dimissDialog() {
        L.d("Dialog is dimiss");
        if (dialog == null) return;
        if (dialog.isShowing()) dialog.dismiss();
    }

}
