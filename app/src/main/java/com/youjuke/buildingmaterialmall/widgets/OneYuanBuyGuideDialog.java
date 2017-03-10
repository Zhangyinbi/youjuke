package com.youjuke.buildingmaterialmall.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.youjuke.buildingmaterialmall.R;
import com.youjuke.swissgearlibrary.rxbus.RxBus;

/**
 * 描述: 一元换购引导弹窗
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-20 10:58
 */

public class OneYuanBuyGuideDialog extends Dialog{

    private ImageView mImgClose;
    private ImageView mImgContent;

    public OneYuanBuyGuideDialog(Context context) {
        super(context, R.style.fullWindowDialogStyle);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_one_yuan_guide);
        mImgClose = (ImageView) findViewById(R.id.img_close);
        mImgContent = (ImageView) findViewById(R.id.img_to_one_yuan_buy);
        mImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()){
                    dismiss();
                }
            }
        });
        mImgContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()){
                    dismiss();
                }
                RxBus.get().post("MainActivity.switchToOneYuanBuy","nothing");
            }
        });
    }
}
