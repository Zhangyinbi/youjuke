package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述: 装修头条
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-02-16 15:29
 */

public class TopNewsOfDecoration {
    private String id;
    private String title;
    private String browse;
    private String date;
    private String img_url;
    private String detail_url;

    public TopNewsOfDecoration() {
    }

    public TopNewsOfDecoration(String id, String title, String browse, String date, String img_url, String detail_url) {
        this.id = id;
        this.title = title;
        this.browse = browse;
        this.date = date;
        this.img_url = img_url;
        this.detail_url = detail_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }
}
