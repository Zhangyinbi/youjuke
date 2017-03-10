package com.youjuke.buildingmaterialmall.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述: 检测app更新Bean
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-29 15:14
 */

public class UpdateAppInfo implements Parcelable {

    /**
     * version_code : 1
     * version_name : 1.0
     * packet_name : http://prebk.youjuke.com/download/app-release_v1.0.apk
     * update_info : 1.上线版本v1.0
     */

    private String version_code;
    private String version_name;
    private String packet_name;
    private String update_info;

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getPacket_name() {
        return packet_name;
    }

    public void setPacket_name(String packet_name) {
        this.packet_name = packet_name;
    }

    public String getUpdate_info() {
        return update_info;
    }

    public void setUpdate_info(String update_info) {
        this.update_info = update_info;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version_code);
        dest.writeString(this.version_name);
        dest.writeString(this.packet_name);
        dest.writeString(this.update_info);
    }

    public UpdateAppInfo() {
    }

    protected UpdateAppInfo(Parcel in) {
        this.version_code = in.readString();
        this.version_name = in.readString();
        this.packet_name = in.readString();
        this.update_info = in.readString();
    }

    public static final Parcelable.Creator<UpdateAppInfo> CREATOR = new Parcelable.Creator<UpdateAppInfo>() {
        @Override
        public UpdateAppInfo createFromParcel(Parcel source) {
            return new UpdateAppInfo(source);
        }

        @Override
        public UpdateAppInfo[] newArray(int size) {
            return new UpdateAppInfo[size];
        }
    };
}
