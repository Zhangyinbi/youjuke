package com.youjuke.buildingmaterialmall.entity;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-11-21 14:28
 */
public class MessageCenterEntity {

    /**
     * title : 交易进度提醒
     * content : 箭牌爆款喷射虹吸坐便器静音又节水的顶顶顶顶顶订单订单呵呵哈哈
     * image :
     * type : 0
     * createdate : 2016-11-16
     * unread_count : 0
     */
    private String title;
    private String content;
    private String image;
    private String type;
    private String createdate;
    private int unread_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }
}
