package com.youjuke.buildingmaterialmall.entity;

import java.util.List;

/**
 * 描述: TODO
 * ------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2016-12-23 16:57
 */
public class ManagedFundsEntity {


    /**
     * sj_tg_money : 10000.00
     * sj_money_use : 10000.00
     * product : [{"play_num":"开工阶段","play_num_status":"1","play_type":"等待确认","play_type_status":1,"expect_play_date":"2016-12-26","play_money":"2000.00","play_date":"-----------","real_play_money":"-----------","id":"11"},{"play_num":"水电阶段","play_num_status":"2","play_type":"同意付款","play_type_status":0,"expect_play_date":"2017-01-10","play_money":"2000.00","play_date":"-----------","real_play_money":"-----------","id":"12"},{"play_num":"泥木阶段","play_num_status":"3","play_type":"同意付款","play_type_status":0,"expect_play_date":"2017-02-04","play_money":"2000.00","play_date":"-----------","real_play_money":"-----------","id":"13"},{"play_num":"油漆阶段","play_num_status":"4","play_type":"同意付款","play_type_status":0,"expect_play_date":"2017-02-14","play_money":"2000.00","play_date":"-----------","real_play_money":"-----------","id":"14"},{"play_num":"竣工阶段","play_num_status":"5","play_type":"同意付款","play_type_status":0,"expect_play_date":"2017-03-06","play_money":"2000.00","play_date":"-----------","real_play_money":"-----------","id":"15"}]
     */

    private String sj_tg_money;
    private String sj_money_use;
    private List<ProductBean> product;

    public String getSj_tg_money() {
        return sj_tg_money;
    }

    public void setSj_tg_money(String sj_tg_money) {
        this.sj_tg_money = sj_tg_money;
    }

    public String getSj_money_use() {
        return sj_money_use;
    }

    public void setSj_money_use(String sj_money_use) {
        this.sj_money_use = sj_money_use;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean {
        /**
         * play_num : 开工阶段
         * play_num_status : 1
         * play_type : 等待确认
         * play_type_status : 1
         * expect_play_date : 2016-12-26
         * play_money : 2000.00
         * play_date : -----------
         * real_play_money : -----------
         * id : 11
         */

        private String play_num;
        private String play_num_status;
        private String play_type;
        private int play_type_status;
        private String expect_play_date;
        private String play_money;
        private String play_date;
        private String real_play_money;
        private String id;

        public String getPlay_num() {
            return play_num;
        }

        public void setPlay_num(String play_num) {
            this.play_num = play_num;
        }

        public String getPlay_num_status() {
            return play_num_status;
        }

        public void setPlay_num_status(String play_num_status) {
            this.play_num_status = play_num_status;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public int getPlay_type_status() {
            return play_type_status;
        }

        public void setPlay_type_status(int play_type_status) {
            this.play_type_status = play_type_status;
        }

        public String getExpect_play_date() {
            return expect_play_date;
        }

        public void setExpect_play_date(String expect_play_date) {
            this.expect_play_date = expect_play_date;
        }

        public String getPlay_money() {
            return play_money;
        }

        public void setPlay_money(String play_money) {
            this.play_money = play_money;
        }

        public String getPlay_date() {
            return play_date;
        }

        public void setPlay_date(String play_date) {
            this.play_date = play_date;
        }

        public String getReal_play_money() {
            return real_play_money;
        }

        public void setReal_play_money(String real_play_money) {
            this.real_play_money = real_play_money;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
