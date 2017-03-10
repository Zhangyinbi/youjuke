package com.youjuke.buildingmaterialmall.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class RebateInfo {
    @Override
    public String toString() {
        return "RebateInfo{" +
                "sj_gross_income='" + sj_gross_income + '\'' +
                ", imp_msg='" + imp_msg + '\'' +
                ", yj_gross_income='" + yj_gross_income + '\'' +
                ", product=" + product +
                '}';
    }

    private String sj_gross_income;//实际总收益

    public String getSj_gross_income() {
        return sj_gross_income;
    }

    public void setSj_gross_income(String sj_gross_income) {
        this.sj_gross_income = sj_gross_income;
    }

    public String getImp_msg() {
        return imp_msg;
    }

    public void setImp_msg(String imp_msg) {
        this.imp_msg = imp_msg;
    }

    private String imp_msg;


    public String getYj_gross_income() {
        return yj_gross_income;
    }

    public void setYj_gross_income(String yj_gross_income) {
        this.yj_gross_income = yj_gross_income;
    }

    public ArrayList<ProductInfo> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductInfo> product) {
        this.product = product;
    }

    private String yj_gross_income;//预计总收益
    private ArrayList<ProductInfo> product;//列表信息
    public class ProductInfo{


        public String getFl_money() {
            return fl_money;
        }

        public void setFl_money(String fl_money) {
            this.fl_money = fl_money;
        }

        public String getFl_time() {
            return fl_time;
        }

        public void setFl_time(String fl_time) {
            this.fl_time = fl_time;
        }

        public String getLq_time() {
            return lq_time;
        }

        public void setLq_time(String lq_date) {
            this.lq_time = lq_date;
        }

        public String getPeriods() {
            return periods;
        }

        public void setPeriods(String periods) {
            this.periods = periods;
        }

        public String getId() {
            return fl_id;
        }

        public void setId(String id) {
            this.fl_id = id;
        }

        public int getButton_status() {
            return button_status;
        }

        public void setButton_status(int button_status) {
            this.button_status = button_status;
        }

        private String fl_money;//本期返利金额
        private String fl_time;//本期返利时间
        private String lq_time;//本期领取时间
        private String periods;//第几期
        private String fl_id;//返利条目的id
        private int button_status;//返利状态

    }
}
