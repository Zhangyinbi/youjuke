package com.youjuke.swissgearlibrary.rxbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.youjuke.swissgearlibrary.rxbus.annotation.Produce;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;

import java.util.Arrays;
import java.util.List;

/**
 * 描述:RxBusOlder封装后的试用
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-07 10:51
 *
 * 注意：RxBus.get().post(stringTag,Object value) value 不能为值类型 必须为应用类型 否则无法接收的
  */
public class Text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        RxBus.get().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = @Tag("ss"))
    public void eat(String food) {
        // purpose
    }

    @Subscribe(
            thread = EventThread.IO,
            tags = {
                    @Tag("one")
            }
    )
    public void eatMore(List<String> foods) {
        // purpose
    }
//------------------------------------------------
    @Produce(tags = @Tag("ss"))
    public String produceFood() {
        return "This is bread!";
    }

    @Produce(
            thread = EventThread.IO,
            tags = {
                    @Tag("one")
            }
    )
    public List<String> produceMoreFood() {
        return Arrays.asList("This is breads!");
    }
}
