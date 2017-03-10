package com.youjuke.buildingmaterialmall.entity;

/**
 * Created by Administrator on 2017/1/4.
 */

public class BalanceDetailInfo {
    private String text;//标题
    private String amount;
    private String created;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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
}
