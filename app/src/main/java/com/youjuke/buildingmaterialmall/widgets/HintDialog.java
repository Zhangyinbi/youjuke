package com.youjuke.buildingmaterialmall.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.youjuke.buildingmaterialmall.R;


/**
 * Created by user on 2015/11/25.
 */
public class HintDialog extends Dialog {

    private TextView hintdialog_cancel;
    private TextView hintdialog_confirm;
    private TextView hintdialog_title;
    private View hintdialog_view;


    public HintDialog(Context context) {
        /* 设置样式 */
        super(context, R.style.MyDialogStyle);
        this.setContentView(R.layout.dialog_hint);
        hintdialog_cancel = (TextView) findViewById(R.id.hintdialog_cancel);
        hintdialog_confirm = (TextView) findViewById(R.id.hintdialog_confirm);
        hintdialog_title = (TextView) findViewById(R.id.hintdialog_title);
        hintdialog_view = (View)findViewById(R.id.hintdialog_view);
    }

    public HintDialog setCancelListener(View.OnClickListener listener){
        hintdialog_cancel.setOnClickListener(listener);
        return this;
    }
    public HintDialog setConfirmListener(View.OnClickListener listener){
        hintdialog_confirm.setOnClickListener(listener);
        return this;
    }

    public HintDialog setTitleText(String str){
        hintdialog_title.setText(str);
        return this;
    }

    public HintDialog setCancelHint(){
        hintdialog_cancel.setVisibility(View.GONE);
        hintdialog_view.setVisibility(View.GONE);
        return this;
    }

    public void show(String title){
        hintdialog_title.setText(title);
        this.show();
    }

    public HintDialog setDefultConfirmListener(){
        hintdialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HintDialog.this.dismiss();
            }
        });
        return this;
    }
    public HintDialog setDefultCancelListener(){
        hintdialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HintDialog.this.dismiss();
            }
        });
        return this;
    }

}
