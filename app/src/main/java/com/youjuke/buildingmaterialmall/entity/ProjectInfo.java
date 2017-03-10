package com.youjuke.buildingmaterialmall.entity;

import java.util.List;

/**
 * 描述: 弹窗 商品信息
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-22 20:28
 */

public class ProjectInfo {

    /**
     * goods_id : 35
     * goods_type : 2
     * goods_image : /origin/20160922/small14745469158780.jpg
     * classifications : [{"classifications_id":"53","name":"普通商品分类1","num":"14","subsidy":"0.00","seckill_price":"0.00"}]
     */

    private String goods_id;
    private String inventory;
    private String goods_type;
    private String goods_image;

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    /**
     * classifications_id : 53
     * name : 普通商品分类1
     * num : 14
     * subsidy : 0.00
     * seckill_price : 0.00
     */

    private List<ClassificationsBean> classifications;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public List<ClassificationsBean> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationsBean> classifications) {
        this.classifications = classifications;
    }

    public static class ClassificationsBean {
        private String classifications_id;
        private String name;
        private String num;
        private String subsidy;
        private String seckill_price;
        private String sale_price;

        public String getClassifications_id() {
            return classifications_id;
        }

        public void setClassifications_id(String classifications_id) {
            this.classifications_id = classifications_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getSubsidy() {
            return subsidy;
        }

        public void setSubsidy(String subsidy) {
            this.subsidy = subsidy;
        }

        public String getSeckill_price() {
            return seckill_price;
        }

        public void setSeckill_price(String seckill_price) {
            this.seckill_price = seckill_price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }
    }
}
