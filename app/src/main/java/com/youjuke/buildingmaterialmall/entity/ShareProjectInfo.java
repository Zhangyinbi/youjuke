package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述: 分享商品的信息
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-10-26 16:20
 */

public class ShareProjectInfo {

    /**
     * share_image : http://bk.img.youjuke.com/product/share/normal14720261503181.jpg
     * share_title : 测试分享文案测试分享文案123
     * share_desc : 我在优居客发现了一个不错的商品，赶快来看看吧。
     * share_link : http://prebk.youjuke.com/onsale/old_item?id=34
     */

    private String share_image;
    private String share_title;
    private String share_desc;
    private String share_link;

    public String getShare_image() {
        return share_image;
    }

    public void setShare_image(String share_image) {
        this.share_image = share_image;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
