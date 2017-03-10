package com.youjuke.buildingmaterialmall.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;


/**
 * Created by Administrator on 2017/1/4.
 */

public class CheckAccountDialog extends Dialog {
    public TextView tv_camera;
    public TextView tv_photo;
    private TextView tv_cancel;
    public CheckAccountDialog(Context context,boolean f) {
        super(context, R.style.MyDialogStyles);
        this.setContentView(R.layout.dialog_check_account);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        initView(f);
    }

    private void initView(boolean f) {
        tv_camera= (TextView) findViewById(R.id.tv_camera);
        tv_photo= (TextView) findViewById(R.id.tv_photo);
        if (f)tv_camera.setVisibility(View.GONE);
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
    }

   public CheckAccountDialog setCancelClickListener(View.OnClickListener onClickListener){
       tv_cancel.setOnClickListener(onClickListener);
       return this;
   }public CheckAccountDialog setAccountClickListener(View.OnClickListener onClickListener,String str ){
        tv_photo.setText(str);
        tv_camera.setBackgroundColor(Color.parseColor("#ffffff"));
        tv_photo.setBackgroundResource(R.drawable.color_eee_box_bottom);
        tv_photo.setOnClickListener(onClickListener);
       return this;
   }public CheckAccountDialog setAccount1ClickListener(View.OnClickListener onClickListener,String str){
        tv_camera.setText(str);
        tv_camera.setBackgroundColor(Color.parseColor("#00C24F"));
        tv_photo.setBackgroundResource(R.drawable.color_write_box_bottom);
        tv_camera.setOnClickListener(onClickListener);
       return this;
   }

}
