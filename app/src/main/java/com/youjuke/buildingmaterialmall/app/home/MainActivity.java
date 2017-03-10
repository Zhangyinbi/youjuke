package com.youjuke.buildingmaterialmall.app.home;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import com.jaeger.library.StatusBarUtil;
import com.youjuke.buildingmaterialmall.BuildingMaterialApp;
import com.youjuke.buildingmaterialmall.R;
import com.youjuke.buildingmaterialmall.app.login.LoginActivity;
import com.youjuke.buildingmaterialmall.app.me.MeFragment;
import com.youjuke.buildingmaterialmall.entity.User;
import com.youjuke.buildingmaterialmall.update.UpdateManager;
import com.youjuke.buildingmaterialmall.widgets.SweetAlertDialog;
import com.youjuke.swissgearlibrary.base.BaseActivity;
import com.youjuke.swissgearlibrary.rxbus.annotation.Subscribe;
import com.youjuke.swissgearlibrary.rxbus.annotation.Tag;
import com.youjuke.swissgearlibrary.rxbus.thread.EventThread;
import com.youjuke.swissgearlibrary.utils.L;
import com.youjuke.swissgearlibrary.utils.SPUtil;
import com.youjuke.swissgearlibrary.utils.ToastUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * #0001 tianXiao  暂时替换ViewPager的切换方法，因为会重复提交
 */
public class MainActivity extends BaseActivity implements AppWakeUpListener {


    private String[] mTitles;
    private Fragment[] fragments;
    private int currentTabIndex;
    private int jPushIndex = -1;
    private RelativeLayout relativeLayoutMain;
    private FrameLayout frameLayoutPager;
    private LinearLayout linearLayoutIndex;
    private ImageView imageViewIndex;
    private TextView textIndex;
    private LinearLayout llService;
    private ImageView imageViewService;
    private TextView textViewService;
    private LinearLayout llYyhg;
    private ImageView imageYyhg;
    private TextView tvYyhg;
    private LinearLayout linearLayoutMe;
    private ImageView imageViewMe;
    private TextView textViewMe;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void assignViews() {
        relativeLayoutMain = (RelativeLayout) findViewById(R.id.relativeLayout_main);
        frameLayoutPager = (FrameLayout) findViewById(R.id.frameLayout_pager);
        linearLayoutIndex = (LinearLayout) findViewById(R.id.linearLayout_index);
        imageViewIndex = (ImageView) findViewById(R.id.image_view_index);
        textIndex = (TextView) findViewById(R.id.text_index);
        llService = (LinearLayout) findViewById(R.id.ll_service);
        imageViewService = (ImageView) findViewById(R.id.imageView_service);
        textViewService = (TextView) findViewById(R.id.textView_service);
        llYyhg = (LinearLayout) findViewById(R.id.ll_yyhg);
        imageYyhg = (ImageView) findViewById(R.id.image_yyhg);
        tvYyhg = (TextView) findViewById(R.id.tv_yyhg);
        linearLayoutMe = (LinearLayout) findViewById(R.id.linearLayout_me);
        imageViewMe = (ImageView) findViewById(R.id.imageView_me);
        textViewMe = (TextView) findViewById(R.id.textView_me);
        llYyhg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (BuildingMaterialApp.user == null) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 2);
                } else {*/
                setShowingFragment(2, true);
               /* }*/
            }
        });
        linearLayoutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BuildingMaterialApp.user == null) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 1);
                } else {
                    setShowingFragment(3, true);
                }
            }
        });
        linearLayoutIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShowingFragment(0, true);
            }
        });

        llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShowingFragment(1, true);
            }
        });
    }


    /**
     * 设置布局
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化Fragment
     */
    private void initFragments() {
        IndexFragment indexFragment = IndexFragment.newInstance();
        MeFragment meFragment = MeFragment.newInstance();
        OneYuanBuyFragment oneYuanBuyFragment = OneYuanBuyFragment.newInstance();
        ServiceFragment serviceFragment = ServiceFragment.newInstance();
        fragments = new Fragment[]{
                indexFragment, serviceFragment, oneYuanBuyFragment, meFragment
        };
        int checkFragmentPosition = getIntent().getIntExtra("checkFragmentPosition", 0);
        if (checkFragmentPosition > 0) {
            setShowingFragment(checkFragmentPosition, true);
            return;
        }
        //TODO设置背景色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            relativeLayoutMain.setBackground(getResources().getDrawable(R.drawable.home_mian_color));
        }

        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout_pager, indexFragment)
                .show(indexFragment).commit();
    }

    /**
     * Fragment切换
     */
    public void setShowingFragment(int postion, boolean inHome) {
        if (currentTabIndex != postion) {

            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            if (/*BuildingMaterialApp.user != null &&*/ inHome) {
                if (postion > currentTabIndex) {
                    //设置自定义过场动画
                    trx.setCustomAnimations(
                            R.anim.slide_right_in,
                            R.anim.slide_left_out
                    );
                } else {
                    //设置自定义过场动画
                    trx.setCustomAnimations(
                            R.anim.slide_left_in,
                            R.anim.slide_right_out
                    );
                }
            }
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[postion].isAdded()) {
                trx.add(R.id.frameLayout_pager, fragments[postion]);
            }
            //首页
            if (postion == 0) {
                imageViewService.setImageResource(R.mipmap.btn_fw_1);
                textIndex.setTextColor(Color.parseColor("#25ae5f"));
                imageViewIndex.setImageResource(R.mipmap.btn_sy_2);
                textViewService.setTextColor(Color.parseColor("#989898"));
                textViewMe.setTextColor(Color.parseColor("#989898"));
                imageViewMe.setImageResource(R.mipmap.btn_grzl_1);
                imageYyhg.setImageResource(R.mipmap.btn_yyhg_1);
                tvYyhg.setTextColor(Color.parseColor("#989898"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayoutMain.setBackground(getResources().getDrawable(R.drawable.home_mian_color));
                }
            }
            //一元换购
            if (postion == 2) {
                imageViewService.setImageResource(R.mipmap.btn_fw_1);
                textViewService.setTextColor(Color.parseColor("#989898"));
                textIndex.setTextColor(Color.parseColor("#989898"));
                imageViewIndex.setImageResource(R.mipmap.btn_sy_1);
                textViewService.setTextColor(Color.parseColor("#989898"));
                textViewMe.setTextColor(Color.parseColor("#989898"));
                imageViewMe.setImageResource(R.mipmap.btn_grzl_1);
                imageYyhg.setImageResource(R.mipmap.btn_yyhg_2);
                tvYyhg.setTextColor(Color.parseColor("#25ae5f"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayoutMain.setBackground(getResources().getDrawable(R.drawable.home_mian_color));
                }
            }

            //我的
            if (postion == 3) {
                imageViewService.setImageResource(R.mipmap.btn_fw_1);
                textViewService.setTextColor(Color.parseColor("#989898"));
                textIndex.setTextColor(Color.parseColor("#989898"));
                imageViewIndex.setImageResource(R.mipmap.btn_sy_1);
                textViewMe.setTextColor(Color.parseColor("#25ae5f"));
                imageViewMe.setImageResource(R.mipmap.btn_grzl_2);
                tvYyhg.setTextColor(Color.parseColor("#989898"));
                imageYyhg.setImageResource(R.mipmap.btn_yyhg_1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayoutMain.setBackground(getResources().getDrawable(R.drawable.home_me_color));
                }
            }
            //服务
            if (postion == 1) {
                imageViewService.setImageResource(R.mipmap.btn_fw_2);
                textViewService.setTextColor(Color.parseColor("#25ae5f"));
                textIndex.setTextColor(Color.parseColor("#989898"));
                imageViewIndex.setImageResource(R.mipmap.btn_sy_1);
                textViewMe.setTextColor(Color.parseColor("#989898"));
                imageViewMe.setImageResource(R.mipmap.btn_grzl_1);
                tvYyhg.setTextColor(Color.parseColor("#989898"));
                imageYyhg.setImageResource(R.mipmap.btn_yyhg_1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relativeLayoutMain.setBackground(getResources().getDrawable(R.drawable.home_mian_color));
                }
            }
            trx.show(fragments[postion]).commit();
            currentTabIndex = postion;
        }
    }


    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void initViews(Bundle savedInstanceState) {
        onNewIntent(getIntent());
        assignViews();
        autoLogin();
        initFragments();
        PermissionGen.needPermission(this, 100, PERMISSIONS_STORAGE);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            StoragePermissionUtil.verifyStoragePermissions(this);
//        }
//        checkUpdate();
        //setRootView(this);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, null);
    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        User user = SPUtil.getObject(this, "user", User.class);
        BuildingMaterialApp.user = user;
        JPushAlias();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        checkUpdate();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        SweetAlertDialog.Builder builder = new SweetAlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage("请前往设置开启读写SD卡权限")
                .setTitle("提示")
                .setCancelableoutSide(false)
                .setNegativeButton("取消", new SweetAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        ToastUtil.show(MainActivity.this, "请去设置里面开启SD卡权限，否则将无法正常使用");
                    }
                })
                .setPositiveButton("设置", new SweetAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        Intent intent = getAppDetailSettingIntent(MainActivity.this);
                        startActivity(intent);
                    }
                })
                .onDIsmissListener(new SweetAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        checkUpdate();
                    }
                }).show();
    }

    private Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

    private void JPushAlias() {
        if (BuildingMaterialApp.user != null) {
            if (jPushIndex == -1) {
                //极光推送
                JPushInterface.setAlias(this, //上下文对象
                        BuildingMaterialApp.user.getMobile(), //别名
                        new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                Log.d("alias", "别名注册是否成功" + i);
                                jPushIndex = i;
                            }
                        });
            }
        } else if (BuildingMaterialApp.user == null && jPushIndex != -1) {
            //极光推送
            JPushInterface.setAlias(this, //上下文对象
                    "", //别名
                    new TagAliasCallback() {//回调接口,i=0表示成功,其它设置失败
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            Log.d("alias", "别名注销是否成功" + i);
                            jPushIndex = -1;
                        }
                    });
        }
    }

    //判断用户是否登录存在
    private Boolean userExist() {
        return BuildingMaterialApp.user == null;
    }


    /**
     * 切换到一元换购
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@Tag("MainActivity.switchToOneYuanBuy")}
    )
    public void switchToOneYuanBuy(String nothing) {
        llYyhg.performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d("requestCode:" + requestCode);
        if (requestCode == 1) {
            if (BuildingMaterialApp.user != null) {
                setShowingFragment(3, false);
            } else {
                setShowingFragment(0, false);
            }
        } else if (requestCode == 2 && BuildingMaterialApp.user != null) {
            setShowingFragment(2, true);
        }
    }

    /**
     * 获取消息数量
     */
/*    private void getMessageCount() {
        params.clear();
        params.put("uid", BuildingMaterialApp.user.getId());
        compositeSubscription.add(RetrofitManager.getInstance().create(CommonService.class)
                .getData(new RequestBean.JsonMsg(ApiContstants.MESSAGE_COUNT, params).toJson())
                .compose(this.<ResponseBean>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResponseBean>() {
                    @Override
                    public void call(ResponseBean responseBean) {
                        L.d("未读消息数量" + responseBean.getMessage());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                })
        );
    }*/


    /**
     * ToolBar 设置
     */
    @Override
    public void initToolBar() {
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag("showTab")
            }
    )
    public void showTab(String s) {
        if (s.equals("jchd")) {
            setShowingFragment(1, false);
        } else if (s.equals("loginOut")) {//个人中心退出登录之后,然后跳转主页
            L.d("loginOut");
            Glide.get(this).clearMemory();
            setShowingFragment(0, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        L.d("主页获取焦点");
        super.onResume();
        JPushAlias();
    }

    /**
     * 检测更新
     */
    public void checkUpdate() {
        UpdateManager.getInstance(this).checkUpdate(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重写服务的回退事件
        if (fragments[currentTabIndex] instanceof ServiceFragment) {
            boolean flag = ((ServiceFragment) fragments[currentTabIndex]).onKeyDown(keyCode, event);
            return flag ? true : super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        OpenInstall.getWakeUp(this, this);
    }

    @Override
    public void onWakeUpFinish(AppData appData, Error error) {
        if (error == null) {
//            Log.e("MainActivity", "OpenInstall wakeup-data : " + appData.toString());//一键跳转的地方
        } else {
//            Log.e("MainActivity", "error : " + error.toString());
        }
    }
}
