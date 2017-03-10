package com.youjuke.buildingmaterialmall.wxapi;

import com.google.gson.annotations.SerializedName;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-15 17:22
 */
public class WxPayEntity {


    /**
     * pay_info : {"appid":"wxde06caa10050b707","partnerid":"1395000202","prepayid":"wx201611161413123055398d610336974883","package":"Sign=WXPay","noncestr":"2j3p6jrpx9rxni6e7f769bx75veg8xh2","timestamp":1479276791,"sign":"4B851A379C0DDA8CCD8BF09CA6B6640F"}
     * order_info : {"gid":0}
     */

    private PayInfoBean pay_info;
    private OrderInfoBean order_info;

    public PayInfoBean getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfoBean pay_info) {
        this.pay_info = pay_info;
    }

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public static class PayInfoBean {
        /**
         * appid : wxde06caa10050b707
         * partnerid : 1395000202
         * prepayid : wx201611161413123055398d610336974883
         * package : Sign=WXPay
         * noncestr : 2j3p6jrpx9rxni6e7f769bx75veg8xh2
         * timestamp : 1479276791
         * sign : 4B851A379C0DDA8CCD8BF09CA6B6640F
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    public static class OrderInfoBean {
        /**
         * gid : 0
         */

        private int gid;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }
    }
}
