package com.youjuke.buildingmaterialmall.entity;

import java.util.List;

/**
 * 描述:
 * <p>
 * 工程:
 * #0000    Tian Xiao    2016-09-20 16:06
 */
public class CityAddressList {


    /**
     * city_id : 72
     * city_name : 上海
     * provinces_id : 9
     */

    private CitiesBean cities;
    /**
     * province_id : 9
     * province_name : 上海
     */

    private List<ProvinciesBean> provincies;
    /**
     * district_id : 717
     * district_name : 浦东新区
     * city_id : 72
     */

    private List<DistrictsBean> districts;

    public CitiesBean getCities() {
        return cities;
    }

    public void setCities(CitiesBean cities) {
        this.cities = cities;
    }

    public List<ProvinciesBean> getProvincies() {
        return provincies;
    }

    public void setProvincies(List<ProvinciesBean> provincies) {
        this.provincies = provincies;
    }

    public List<DistrictsBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsBean> districts) {
        this.districts = districts;
    }





}
