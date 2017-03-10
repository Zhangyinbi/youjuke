package com.youjuke.buildingmaterialmall.entity;


/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-28 20:18
 */
public class BargainBuyDetailsProject {
    private String classification;//商品分类id


    public BargainBuyDetailsProject(int classifications_id) {
        this.classification= String.valueOf(classifications_id);


    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }



}
