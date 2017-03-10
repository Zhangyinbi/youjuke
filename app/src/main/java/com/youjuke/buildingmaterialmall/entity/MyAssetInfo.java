package com.youjuke.buildingmaterialmall.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class MyAssetInfo {
    private String balance;//托管资金
    private String fl_money;//领取返利
    private String wallet_balance;//钱包余额

    @Override
    public String toString() {
        return "MyAssetInfo{" +
                "balance='" + balance + '\'' +
                ", fl_money='" + fl_money + '\'' +
                ", wallet_balance='" + wallet_balance + '\'' +
                ", expect_start_time='" + expect_start_time + '\'' +
                ", contract_id=" + contract_id +
                '}';
    }

    private String expect_start_time;//开工时间

    public ArrayList<Contract> getContract_id() {
        return contract_id;
    }

    public void setContract_id(ArrayList<Contract> contract_id) {
        this.contract_id = contract_id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFl_money() {
        return fl_money;
    }

    public void setFl_money(String fl_money) {
        this.fl_money = fl_money;
    }

    public String getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public String getExpect_start_time() {
        return expect_start_time;
    }

    public void setExpect_start_time(String expect_start_time) {
        this.expect_start_time = expect_start_time;
    }

    private ArrayList<Contract> contract_id;//合同ID

    public static class Contract implements Parcelable{
        private String id;

        public Contract(String id, String contract_type) {
            this.id = id;
            this.contract_type = contract_type;
        }

        protected Contract(Parcel in) {
            id = in.readString();
            contract_type = in.readString();
        }

        public static final Creator<Contract> CREATOR = new Creator<Contract>() {
            @Override
            public Contract createFromParcel(Parcel in) {
                return new Contract(in);
            }

            @Override
            public Contract[] newArray(int size) {
                return new Contract[size];
            }
        };

        @Override
        public String toString() {
            return "Contract{" +
                    "id='" + id + '\'' +
                    ", contract_type='" + contract_type + '\'' +
                    '}';
        }

        public String getContract_type() {
            return contract_type;
        }

        public void setContract_type(String contract_type) {
            this.contract_type = contract_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String contract_type;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(contract_type);
        }
    }
}
