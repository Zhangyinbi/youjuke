package com.youjuke.buildingmaterialmall.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述: 第三方登录信息
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-11-02 14:06
 */

public class ThirdPartyInfo implements Parcelable {
    private String id;
    private String nickName;
    private String avatar;//头像url
    private String platform;//平台  wx qq

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    private String unionid;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ThirdPartyInfo{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", platform='" + platform + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nickName);
        dest.writeString(this.avatar);
        dest.writeString(this.platform);
        dest.writeString(this.unionid);
    }

    public ThirdPartyInfo() {
    }

    protected ThirdPartyInfo(Parcel in) {
        this.id = in.readString();
        this.nickName = in.readString();
        this.avatar = in.readString();
        this.platform = in.readString();
        this.unionid = in.readString();
    }

    public static final Parcelable.Creator<ThirdPartyInfo> CREATOR = new Parcelable.Creator<ThirdPartyInfo>() {
        @Override
        public ThirdPartyInfo createFromParcel(Parcel source) {
            return new ThirdPartyInfo(source);
        }

        @Override
        public ThirdPartyInfo[] newArray(int size) {
            return new ThirdPartyInfo[size];
        }
    };
}
