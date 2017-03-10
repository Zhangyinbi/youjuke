package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述:
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-20 13:56
 */
public class AddressList  {

    /**
     * id : 218
     * mobile : 15012345678
     * receive_name : 我们
     * city : 上海
     * district : 黄浦区
     * city_id : 72
     * district_id : 719
     * province_id : 9
     * province : 上海
     * address : 咯哦哦哦痛心
     * is_default : true
     */

    private String id;
    private String mobile;
    private String receive_name;
    private String city;
    private String district;
    private String city_id;
    private String district_id;
    private String province_id;
    private String province;
    private String address;
    private boolean is_default;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }
}
