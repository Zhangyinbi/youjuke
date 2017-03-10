package com.youjuke.buildingmaterialmall.app.login.register_forget_password_fragment;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-02 15:27
 */
public class ForgetPasswordEntity {

    private String mobile;
    private int showIndex;
    private String code;
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
