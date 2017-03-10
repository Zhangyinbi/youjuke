package com.youjuke.buildingmaterialmall.app.home;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 描述:
 * 自定义Tab的标签样式类
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-08 16:37
 */
public class TabEntity implements CustomTabEntity {

    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
