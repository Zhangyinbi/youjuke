package com.youjuke.buildingmaterialmall.entity;

import java.util.List;

/**
 * 描述: 首页实体类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-10-13 14:28
 * #0001     mwy     只保留轮播图数据
 */

public class Index {

    private List<BannerImagesBean> banner_images;

    public List<BannerImagesBean> getBanner_images() {
        return banner_images;
    }

    public void setBanner_images(List<BannerImagesBean> banner_images) {
        this.banner_images = banner_images;
    }

    public static class BannerImagesBean {
        private int type;
        private String title;
        private String img;
        private String link;
        private String target;

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Override
        public String toString() {
            return "BannerImagesBean{" +
                    "type=" + type +
                    ", title='" + title + '\'' +
                    ", img='" + img + '\'' +
                    ", link='" + link + '\'' +
                    ", target='" + target + '\'' +
                    '}';
        }
    }



}
