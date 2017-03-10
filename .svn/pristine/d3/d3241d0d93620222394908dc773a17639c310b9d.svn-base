package com.youjuke.buildingmaterialmall;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import com.youjuke.buildingmaterialmall.app.bargain_buy.BargainBuyActivity;
import com.youjuke.buildingmaterialmall.app.message_center.MessageCenterActivity;
import com.youjuke.buildingmaterialmall.app.seckill.SeckillDetailsActivity;
import com.youjuke.swissgearlibrary.utils.L;
import cn.jpush.android.api.JPushInterface;

/**
 * 描述:
 * 自定义Receive接受推送
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-23 11:45
 */
public class BuildingMaterialReceiver extends BroadcastReceiver {
    private static final String TAG = "极光推送";
    private Gson gson = new Gson();
    private NotificationManager nm;
    private Intent intent;
    private ExtrasEntity extrasEntit;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        L.d(TAG, "onReceive - " + intent.getAction() + ", extras: ");

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            L.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            L.d(TAG, "接受到推送下来的自定义消息");
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            L.d("收到了自定义消息。消息内容是：" + message);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            L.d("收到了自定义消息。extra是：" + extra);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            L.d(TAG, "接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            L.d(TAG, "用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            L.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        L.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        L.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        L.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";

        extrasEntit = gson.fromJson(extras, ExtrasEntity.class);
        if (extrasEntit.getTitle() != null) {//一元换购

            intent = new Intent(context, BargainBuyActivity.class);
            intent.putExtra("link", extrasEntit.getWebUrl());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (extrasEntit.getType() != null) {
            switch (extrasEntit.getType().trim()) {
                case "1"://消息中心
                    intent = new Intent(context, MessageCenterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                case "0"://爆款秒杀
                    L.d("跳转爆款秒杀");
                    intent = new Intent(context, SeckillDetailsActivity.class);
                    intent.putExtra("url", extrasEntit.getWebUrl());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                default:
                    break;
            }
        } else {
            L.d("消息中心");
            intent = new Intent(context, MessageCenterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }

    class ExtrasEntity {

        /**
         * webUrl : http://m.youjuke.com/redemption/detail?id=71&platform=app
         * type :  1是一元换购  0 是今日秒杀
         */

        private String webUrl;
        private String type;
        private String status_id;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus_id() {
            return status_id;
        }

        public void setStatus_id(String status_id) {
            this.status_id = status_id;
        }

        public String getWebUrl() {

            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }
    }
}


