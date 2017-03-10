package com.youjuke.buildingmaterialmall.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.gson.Gson;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.entity.UpdateAppInfo;
import com.youjuke.buildingmaterialmall.retrofit.ApiContstants;
import com.youjuke.buildingmaterialmall.retrofit.RequestBean;
import com.youjuke.buildingmaterialmall.retrofit.ResponseBean;
import com.youjuke.buildingmaterialmall.retrofit.RetrofitManager;
import com.youjuke.buildingmaterialmall.retrofit.api.CommonService;
import com.youjuke.swissgearlibrary.utils.ApplicationUtils;
import com.youjuke.swissgearlibrary.utils.L;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zs on 2016/7/7.
 * https://github.com/shanyao0/DownLoadManager
 */
public class UpdateManager {
    private static UpdateManager instance;
    private Context mContext;
    private Dialog noticeDialog;
    private TextView tvContent;
    private NumberProgressBar number_progress_bar;

    public TextView getText() {
        return text;
    }

    private TextView text;
    private LayoutInflater inflater;

    private UpdateManager(Context context) {
        this.mContext = context;
    }

    public synchronized static UpdateManager getInstance(Context context) {
        if (instance == null) {
            instance = new UpdateManager(context);
        }
        return instance;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(final boolean isToast) {
        /**
         * 在这里请求后台接口，获取更新的内容和最新的版本号
         */
        final int verCode = ApplicationUtils.getVerCode(mContext);
        Map<String, Object> params = new HashMap<>();
        params.put("version_code", verCode);
        Subscription subscribe = RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.APP_CURRENT_VERSION, params).toJson())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBean responseBean) {
                        L.i("检测更新:" + responseBean.toString());
                        if ("200".equals(responseBean.getStatus())) {
                            if (responseBean.getData() != null) {
                                UpdateAppInfo updteAppInfo = new Gson().fromJson(responseBean.getData(), UpdateAppInfo.class);
                                if (Integer.parseInt(updteAppInfo.getVersion_code()) > verCode) {
                                    showNoticeDialog(updteAppInfo);
                                }
                            } else {
                                if (isToast) {
                                    Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

       /* // 版本的更新信息
        String version_info = "更新内容\n" + "    1. 车位分享异常处理\n" + "    2. 发布车位折扣格式统一\n" + "    ";
        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
        int nVersion_code = 2;
        if (mVersion_code < nVersion_code) {
            // 显示提示对话
            showNoticeDialog(version_info);
        } else {
            if (isToast) {
                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    /**
     * 显示更新对话框
     */
    private void showNoticeDialog(final UpdateAppInfo info) {
        // 构造对话框
        noticeDialog = new Dialog(mContext, R.style.Sweet_Alert_Dialog);
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_updata, null);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText(info.getUpdate_info() == null ? "" : info.getUpdate_info().replace("\\n","\n"));

        number_progress_bar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
        text = (TextView) view.findViewById(R.id.dialog_right_txt);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_progress_bar.setVisibility(View.VISIBLE);
                text.setText("正在下载...");
                text.setEnabled(false);
                Intent intent = new Intent(mContext, DownLoadService.class);
                intent.putExtra("versionName", info.getVersion_name());
                intent.putExtra("url", info.getPacket_name());
                mContext.startService(intent);
            }
        });
        noticeDialog.setContentView(view);
        noticeDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                }
                return false;
            }
        });

        noticeDialog.setCancelable(false);
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.show();
    }

    /**
     * 更新进度条
     *
     * @param progress
     */
    public void updateProgress(int progress) {
        if (noticeDialog == null || !noticeDialog.isShowing() || progress <= 0) {
            return;
        }
        number_progress_bar.setProgress(progress);
    }

    /**
     * 完成下载
     */
    public void finsh() {
        if (noticeDialog == null || !noticeDialog.isShowing()) {
            return;
        }
        noticeDialog.dismiss();
        //把当前工具类 置空 ，防止updateManager 在loginActivity 关闭后 再次打开时 activity 是新的
        //而 updateManager 中 mContext 还是之前loginActivity的mContext 导致dialog 无法show出来而报错
        instance = null;
    }

    /**
     * 重试
     */
    public void retry() {
        if (noticeDialog == null || !noticeDialog.isShowing()) {
            return;
        }
        text.setEnabled(true);
        text.setText("重试");
    }
}
