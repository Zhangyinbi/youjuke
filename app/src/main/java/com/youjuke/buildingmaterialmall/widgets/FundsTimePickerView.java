package com.youjuke.buildingmaterialmall.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelTime;
import com.youjuke.buildingmaterialmall.R;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述: 付款时间选择器
 * ------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2016-12-26 17:11
 */
public class FundsTimePickerView  extends BasePickerView implements View.OnClickListener {


    private final TextView cancel;
    private final TextView setting;
    WheelTime wheelTime;
    private TimePickerView.OnTimeSelectListener timeSelectListener;
    public FundsTimePickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.funds_time_picker, contentContainer);
        cancel = (TextView) findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(this);
        setting = (TextView) findViewById(R.id.tv_setting);
        setting.setOnClickListener(this);
        LinearLayout ll= (LinearLayout) findViewById(R.id.ll);//消除点击本区域消失
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        LinearLayout ll_content= (LinearLayout) findViewById(R.id.ll_content);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.pop_enter_anim);
        ll_content.startAnimation(animation);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, com.bigkoo.pickerview.TimePickerView.Type.YEAR_MONTH_DAY);
        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

//    /**

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
        void onTimeNotSetting();
    }
    public void setOnTimeSelectListener(TimePickerView.OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                if (timeSelectListener!=null){
                    timeSelectListener.onTimeNotSetting();
                }
                dismiss();
                break;
            case R.id.tv_setting:
                if (timeSelectListener != null) {
                    try {
                        Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                        timeSelectListener.onTimeSelect(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                dismiss();
                break;
        }
    }


}
