package com.youjuke.swissgearlibrary.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by mwy on 2016/3/30.
 * 金钱格式化工具
 */
public class MoneySimpleFormat {

    //返回格式:￥200,000.03
    public static String getMoneyType(String money) {
        if(money == null) return null;
        // 把string类型的货币转换为double类型。
        Double numDouble = Double.parseDouble(money);
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // 把转换后的货币String类型返回
        String numString = format.format(numDouble);
        return numString;
    }


    public static String getMoneyType(long money) {
        return getMoneyType(money+"");
    }

    public static String getMoneyType(double money) {
        return getMoneyType(money+"");
    }

    //返回格式:200,000.03
    public static String getSimpleType(String money){
        NumberFormat nf = new DecimalFormat(",###.##");
        Double numDouble = Double.parseDouble(money);
        String testStr = nf.format(numDouble);
        if (testStr.contains(".")){
            return testStr;
        }else {
            return  testStr+".00";
        }
    }

    public static String getSimpleType(long money){
        return getSimpleType(money + "");
    }

    public static String getSimpleType(double money) {
        return getSimpleType(money + "");
    }

    /**
     *
     * @param custom 自定义金钱符号
     * @param money
     * @return
     */
    public static String getCustomType(String custom,String money){
        NumberFormat nf = new DecimalFormat(custom+",###.##");
        Double numDouble = Double.parseDouble(money);
        String testStr = nf.format(numDouble);
        return  testStr;
    }

    public static String getCustomType(String custom,long money){
        return getCustomType(custom, money + "");
    }

    public static String getCustomType(String custom,double money) {
        return getCustomType(custom,money + "");
    }

    public static String getYuanType(String money){
        NumberFormat nf = new DecimalFormat(",###.##元");
        Double numDouble = Double.parseDouble(money);
        String testStr = nf.format(numDouble);
        return  testStr;
    }

    public static String getYuanType(long money){
        return getYuanType(money + "");
    }

    public static String getYuanType(double money) {
        return getYuanType(money + "");
    }

/*    public static void main(String[] args)
    {
        System.out.println(MoneySimpleFormat.getYuanType("1545.135"));
    }*/

    /**
     * 返回 没有逗号的 ¥200000.03
     * @return
     */
    public static String getNoCommaType(String money){
        NumberFormat nf = new DecimalFormat("¥###.##");
        Double numDouble = Double.parseDouble(money);
        String testStr = nf.format(numDouble);
        return  testStr;
    }

    public static String getNoCommaType(long money){
        return getNoCommaType(money + "");
    }

    public static String getNoCommaType(double money) {
        return getNoCommaType(money + "");
    }
}
