package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述: 检查第三方登录的账户返回的格式
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-11-02 15:49
 */

public class UserIsExistsInfo {
    private String uid;
    private String username;
    private String nickname;
    private String avatar;
    private String mobile;
    private String type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
