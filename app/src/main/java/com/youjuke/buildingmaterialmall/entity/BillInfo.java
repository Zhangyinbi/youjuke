package com.youjuke.buildingmaterialmall.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/26.
 */

public class BillInfo {
    private String cancel_money;//退还金额
    private String periods;//返利周期

    public String getSj_gross_income() {
        return sj_gross_income;
    }

    public void setSj_gross_income(String sj_gross_income) {
        this.sj_gross_income = sj_gross_income;
    }

    private String sj_gross_income;//实际收益
    public String getFd_rate() {
        return fd_rate;
    }

    public void setFd_rate(String fd_rate) {
        this.fd_rate = fd_rate;
    }

    public String getCancel_money() {
        return cancel_money;
    }

    public void setCancel_money(String cancel_money) {
        this.cancel_money = cancel_money;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getSj_money_use() {
        return sj_money_use;
    }

    public void setSj_money_use(String sj_money_use) {
        this.sj_money_use = sj_money_use;
    }

    public String getSj_tg_money() {
        return sj_tg_money;
    }

    public void setSj_tg_money(String sj_tg_money) {
        this.sj_tg_money = sj_tg_money;
    }

    public ArrayList<Bill> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Bill> product) {
        this.product = product;
    }

    private String fd_rate;//点数
    private String sj_money_use;//实际使用金额
    private String sj_tg_money;//实际托管金额
    private ArrayList<Bill> product;//流水明细
    public class Bill{
        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        private String amount;//金额
        private String created;//创建时间
        private String type;//类型
    }
}
