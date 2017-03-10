package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述: 订单实体类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-18 19:39
 */
public class Order {

    /**
     * id : 82
     * image : /origin/20160621/small14665082769490.jpg
     * status : 已取消
     * status_id : 5
     * createdate : 2016-06-22 20:04:29
     * no : 160622200429432294
     * price : 0.2
     */

    private String id;
    private String image;
    private String status;
    private String status_id;
    private String createdate;
    private String down_payment;
    private double price;

    private String no;

    public String getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(String down_payment) {
        this.down_payment = down_payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
