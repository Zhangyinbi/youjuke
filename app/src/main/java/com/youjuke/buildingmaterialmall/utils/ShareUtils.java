package com.youjuke.buildingmaterialmall.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.ShareProjectInfo;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.RetryWhenNetworkException;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.Map;

import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 描述: 分享工具
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-24 10:45
 */

public class ShareUtils {
    private  Context context;
    private String api;
    private Map<String, Object> params;
    private PlatformActionListener listener;
    private MyObserver observer;

    public ShareUtils with(Context context){
        this.context = context;
        return this;
   }

    public ShareUtils setApi(String api){
        this.api = api;
        return this;
    }

    public ShareUtils setParams(Map<String, Object> params){
        this.params = params;
        return this;
    }

    public ShareUtils listener(PlatformActionListener listener){
        if (listener != null){
           this.listener = listener;
        }
        return this;
    }

    /**
     * 在请求完分享数据时 抛出的回调
     * @param observer
     * @return
     */
    public ShareUtils subscribe(MyObserver observer){
        this.observer = observer;
        return this;
    }

    /**
     * 分享方式2: 封装了对shareProjectInfo的请求
     */
    public void toShare(){
        getShareInfo();
    }

    /**
     * 分享方式1: 在有shareProjectInfo时 直接弹窗分享窗口
     * @param context
     * @param shareProjectInfo
     * @param listener
     */
    public static void share(Context context,ShareProjectInfo shareProjectInfo,PlatformActionListener listener){
        showSharePop(context, shareProjectInfo, listener);
    }

    /**
     * 显示分享弹窗
     * @param context
     * @param shareProjectInfo
     * @param listener
     */
    private static void showSharePop(Context context, ShareProjectInfo shareProjectInfo, PlatformActionListener listener) {
        if (shareProjectInfo == null) {
            ToastUtil.show(context,"获取分享信息失败");
            return;
        }
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareProjectInfo.getShare_title());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareProjectInfo.getShare_link());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareProjectInfo.getShare_desc());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(shareProjectInfo.getShare_image());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareProjectInfo.getShare_link());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.youjuke));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareProjectInfo.getShare_link());
        if (listener !=null){
            oks.setCallback(listener);
        }
        // 启动分享GUI
        oks.show(context);
    }


    private void share(ShareProjectInfo shareProjectInfo){
        showSharePop(context, shareProjectInfo, listener);
    }

    /**
     * 获取分享信息
     * @return
     */
    private void getShareInfo(){
        ToastUtil.show(context,"分享中..");
        RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(api, params).toJson())
                .retryWhen(new RetryWhenNetworkException())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (observer != null){
                            observer.onError(e);
                        }
                        e.printStackTrace();
                        ToastUtil.show(context,"服务器出错,请重试");
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        if (observer != null) {
                            observer.onNextBefore(responseBean);
                        }
                        if ("200".equals(responseBean.getStatus())) {
                            ShareProjectInfo  shareProjectInfo
                                    = new Gson().fromJson(responseBean.getData(), ShareProjectInfo.class);
                            share(shareProjectInfo);
                        }
                        if (observer != null) {
                            observer.onNextAfter(responseBean);
                        }
                    }
                });
    }

    public interface MyObserver{
        void onError(Throwable e);
        void onNextBefore(ResponseBean responseBean);
        void onNextAfter(ResponseBean responseBean);
    }
}
