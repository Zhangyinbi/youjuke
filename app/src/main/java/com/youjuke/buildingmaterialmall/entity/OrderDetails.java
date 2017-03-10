package com.youjuke.buildingmaterialmall.entity;

import java.util.List;

/**
 * 描述: 进度详情实体类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-19 14:07
 */
public class OrderDetails {

    /**
     * id : 5293
     * no : 160926115646356167
     * status : 待付款
     * status_id : 1
     * createdate : 2016-09-26 11:56:46
     * ex_time : 250578
     * discount_price : 0
     * goods : [{"image":"/origin/20160627/small14669964989836.jpg","num":"1","gid":"13","name":"浴室柜组合现代简约马桶座便器花洒卫浴套餐组合","classification_name":"300坑距马桶E00308","good_price":"0.02","original_cost":"56.25"}]
     * consignee : 张文fang
     * mobile : 15294893103
     * accept_address : 上海&nbsp;上海&nbsp;虹口区&nbsp;ggggggggg
     * price : 0.02
     * amount : 0.02
     */

    private String id;
    private String no;
    private String status;
    private String status_id;
    private String createdate;
    private long ex_time;
    private float discount_price;
    private String consignee;
    private String mobile;
    private String accept_address;
    private String price;

    public int getIs_pre() {
        return is_pre;
    }

    public void setIs_pre(int is_pre) {
        this.is_pre = is_pre;
    }

    private int is_pre;

    public int getIs_virtual_product() {
        return is_virtual_product;
    }

    public void setIs_virtual_product(int is_virtual_product) {
        this.is_virtual_product = is_virtual_product;
    }

    private int is_virtual_product;
    private double amount;
    /**
     * image : /origin/20160627/small14669964989836.jpg
     * num : 1
     * gid : 13
     * name : 浴室柜组合现代简约马桶座便器花洒卫浴套餐组合
     * classification_name : 300坑距马桶E00308
     * good_price : 0.02
     * original_cost : 56.25
     */

    private List<GoodsBean> goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public long getEx_time() {
        return ex_time;
    }

    public void setEx_time(long ex_time) {
        this.ex_time = ex_time;
    }

    public float getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(float discount_price) {
        this.discount_price = discount_price;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccept_address() {
        return accept_address;
    }

    public void setAccept_address(String accept_address) {
        this.accept_address = accept_address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        private String image;
        private String num;
        private String gid;
        private String name;
        private String classification_name;
        private String good_price;
        private String original_cost;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassification_name() {
            return classification_name;
        }

        public void setClassification_name(String classification_name) {
            this.classification_name = classification_name;
        }

        public String getGood_price() {
            return good_price;
        }

        public void setGood_price(String good_price) {
            this.good_price = good_price;
        }

        public String getOriginal_cost() {
            return original_cost;
        }

        public void setOriginal_cost(String original_cost) {
            this.original_cost = original_cost;
        }
    }
}
