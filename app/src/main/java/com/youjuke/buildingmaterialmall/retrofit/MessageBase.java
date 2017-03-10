package com.youjuke.buildingmaterialmall.retrofit;

/**
 * 描述:
 * <p/>
 * 工程:
 * #0000    Tian Xiao    2016-09-13 19:14
 */
public class MessageBase <T> {
    String message;

    public MessageBase(T object)
    {

        this.message = (String) object;

    }
    public  MessageBase() {
        super();

    }

    @Override
    public String toString() {
        return "MessageBase{" +
                "message='" + message + '\'' +
                '}';
    }
}
