package com.youjuke.buildingmaterialmall;

import com.youjuke.buildingmaterialmall.widgets.CustomProgressDialog;
import com.youjuke.swissgearlibrary.base.BaseActivity;

/**
 * 描述: 对BaseActivity 进一步封装
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-25 13:53
 */

public abstract class WrapActivity extends BaseActivity{
    protected CustomProgressDialog mCustomProgressDialog;

    /**
     * 显示一个ProgressDialog
     */
    public void showProgressDialog() {

        if (mCustomProgressDialog == null) {
            mCustomProgressDialog = new CustomProgressDialog(this,
                    R.drawable.anim_progressr);
        }
        mCustomProgressDialog.setCanceledOnTouchOutside(false);
        mCustomProgressDialog.setCancelable(true);// 设置按返回键是否关闭dialog
        if (!mCustomProgressDialog.isShowing())
            mCustomProgressDialog.show();
    }

    /**
     * 关闭 dialog
     */
    public void dimissDialog(){
        if (mCustomProgressDialog == null) return;
        if (mCustomProgressDialog.isShowing()) mCustomProgressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dimissDialog();
    }
}
