package com.youjuke.buildingmaterialmall.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 生成订单的Bean
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-23 16:14
 */

public class OrderInfo implements Parcelable {

    /**
     * order_id : 5257
     * order_no : 160925162543335314
     * order_type : 0
     * user_address : {"receive_name":"张文fang","mobile":"15294893103","province":"上海","city":"上海","district":"虹口区","address":"ggggggggggggggggggggggggggggg"}
     * good_items : [{"good_id":"13","good_name":"浴室柜组合现代简约马桶座便器花洒卫浴套餐组合","classification_id":"21","classification_name":"300坑距马桶E00308","classification_num":"3","classification_original_cost":"56.25","classification_share_offer":"0.00","available_red_packet":0,"good_image":"/origin/20160627/small14669964989836.jpg","sale_price":"0.02"}]
     * total_fee : 0.06
     * available_red_packet : 0
     * voucher : 0
     * actual_fee : 0.06
     */

    private String order_id;
    private String order_no;

    public int getGood_is_virtual_product() {
        return good_is_virtual_product;
    }

    public void setGood_is_virtual_product(int good_is_virtual_product) {
        this.good_is_virtual_product = good_is_virtual_product;
    }
    private int good_is_virtual_product;
    private int order_type;
    /**
     * receive_name : 张文fang
     * mobile : 15294893103
     * province : 上海
     * city : 上海
     * district : 虹口区
     * address : ggggggggggggggggggggggggggggg
     */

    private UserAddressBean user_address;
    private double total_fee;
    private double available_red_packet;
    private double voucher;
    private double actual_fee;

    /**
     * good_id : 13
     * good_name : 浴室柜组合现代简约马桶座便器花洒卫浴套餐组合
     * classification_id : 21
     * classification_name : 300坑距马桶E00308
     * classification_num : 3
     * classification_original_cost : 56.25
     * classification_share_offer : 0.00
     * available_red_packet : 0
     * good_image : /origin/20160627/small14669964989836.jpg
     * sale_price : 0.02
     */

    private List<GoodItemsBean> good_items;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public UserAddressBean getUser_address() {
        return user_address;
    }

    public void setUser_address(UserAddressBean user_address) {
        this.user_address = user_address;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public double getAvailable_red_packet() {
        return available_red_packet;
    }

    public void setAvailable_red_packet(double available_red_packet) {
        this.available_red_packet = available_red_packet;
    }

    public double getVoucher() {
        return voucher;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
    }

    public double getActual_fee() {
        return actual_fee;
    }

    public void setActual_fee(double actual_fee) {
        this.actual_fee = actual_fee;
    }

    public List<GoodItemsBean> getGood_items() {
        return good_items;
    }

    public void setGood_items(List<GoodItemsBean> good_items) {
        this.good_items = good_items;
    }

    public static class UserAddressBean implements Parcelable {
        private String receive_name;
        private String mobile;
        private String province;
        private String city;
        private String district;
        private String address;

        @Override
        public String toString() {
            return "UserAddressBean{" +
                    "receive_name='" + receive_name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

        public String getReceive_name() {
            return receive_name;
        }

        public void setReceive_name(String receive_name) {
            this.receive_name = receive_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.receive_name);
            dest.writeString(this.mobile);
            dest.writeString(this.province);
            dest.writeString(this.city);
            dest.writeString(this.district);
            dest.writeString(this.address);
        }

        public UserAddressBean() {
        }

        protected UserAddressBean(Parcel in) {
            this.receive_name = in.readString();
            this.mobile = in.readString();
            this.province = in.readString();
            this.city = in.readString();
            this.district = in.readString();
            this.address = in.readString();
        }

        public static final Creator<UserAddressBean> CREATOR = new Creator<UserAddressBean>() {
            @Override
            public UserAddressBean createFromParcel(Parcel source) {
                return new UserAddressBean(source);
            }

            @Override
            public UserAddressBean[] newArray(int size) {
                return new UserAddressBean[size];
            }
        };
    }

    public static class GoodItemsBean implements Parcelable {
        private String good_id;
        private String good_name;
        private String classification_id;
        private String classification_name;
        private String classification_num;
        private String classification_original_cost;
        private String classification_share_offer;
        private float available_red_packet;
        private String good_image;
        private String sale_price;

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getGood_name() {
            return good_name;
        }

        public void setGood_name(String good_name) {
            this.good_name = good_name;
        }

        public String getClassification_id() {
            return classification_id;
        }

        public void setClassification_id(String classification_id) {
            this.classification_id = classification_id;
        }

        public String getClassification_name() {
            return classification_name;
        }

        public void setClassification_name(String classification_name) {
            this.classification_name = classification_name;
        }

        public String getClassification_num() {
            return classification_num;
        }

        public void setClassification_num(String classification_num) {
            this.classification_num = classification_num;
        }

        public String getClassification_original_cost() {
            return classification_original_cost;
        }

        public void setClassification_original_cost(String classification_original_cost) {
            this.classification_original_cost = classification_original_cost;
        }

        public String getClassification_share_offer() {
            return classification_share_offer;
        }

        public void setClassification_share_offer(String classification_share_offer) {
            this.classification_share_offer = classification_share_offer;
        }

        public float getAvailable_red_packet() {
            return available_red_packet;
        }

        public void setAvailable_red_packet(float available_red_packet) {
            this.available_red_packet = available_red_packet;
        }

        public String getGood_image() {
            return good_image;
        }

        public void setGood_image(String good_image) {
            this.good_image = good_image;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.good_id);
            dest.writeString(this.good_name);
            dest.writeString(this.classification_id);
            dest.writeString(this.classification_name);
            dest.writeString(this.classification_num);
            dest.writeString(this.classification_original_cost);
            dest.writeString(this.classification_share_offer);
            dest.writeFloat(this.available_red_packet);
            dest.writeString(this.good_image);
            dest.writeString(this.sale_price);
        }

        public GoodItemsBean() {
        }

        protected GoodItemsBean(Parcel in) {
            this.good_id = in.readString();
            this.good_name = in.readString();
            this.classification_id = in.readString();
            this.classification_name = in.readString();
            this.classification_num = in.readString();
            this.classification_original_cost = in.readString();
            this.classification_share_offer = in.readString();
            this.available_red_packet = in.readFloat();
            this.good_image = in.readString();
            this.sale_price = in.readString();
        }

        public static final Creator<GoodItemsBean> CREATOR = new Creator<GoodItemsBean>() {
            @Override
            public GoodItemsBean createFromParcel(Parcel source) {
                return new GoodItemsBean(source);
            }

            @Override
            public GoodItemsBean[] newArray(int size) {
                return new GoodItemsBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.order_no);
        dest.writeInt(this.order_type);
        dest.writeParcelable(this.user_address, flags);
        dest.writeDouble(this.total_fee);
        dest.writeDouble(this.available_red_packet);
        dest.writeDouble(this.voucher);
        dest.writeDouble(this.actual_fee);
        dest.writeList(this.good_items);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.order_id = in.readString();
        this.order_no = in.readString();
        this.order_type = in.readInt();
        this.user_address = in.readParcelable(UserAddressBean.class.getClassLoader());
        this.total_fee = in.readDouble();
        this.available_red_packet = in.readDouble();
        this.voucher = in.readDouble();
        this.actual_fee = in.readDouble();
        this.good_items = new ArrayList<GoodItemsBean>();
        in.readList(this.good_items, GoodItemsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderInfo> CREATOR = new Parcelable.Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
