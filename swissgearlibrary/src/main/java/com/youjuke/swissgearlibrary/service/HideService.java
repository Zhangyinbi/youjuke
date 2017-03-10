package com.youjuke.swissgearlibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 描述:
 * <p>只是单纯的为了预加载点读进程，无其他意义
 * 工程:
 * #0000    Tian Xiao    2016-11-23 16:25
 */
public class HideService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
