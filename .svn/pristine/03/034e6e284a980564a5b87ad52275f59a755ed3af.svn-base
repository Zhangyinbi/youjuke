package com.youjuke.swissgearlibrary.utils;

import java.math.BigDecimal;

/**
 * 描述: 计算类 解决double float 计算 精度缺失
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-07-15 15:26
 */
public class ArithUtil {
	private static final int DEF_DIV_SCALE=10;

	private ArithUtil(){}

	public static double add(double d1,double d2){
		BigDecimal b1=new BigDecimal(Double.toString(d1));
		BigDecimal b2=new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();

	}

	public static double sub(double d1,double d2){
		BigDecimal b1=new BigDecimal(Double.toString(d1));
		BigDecimal b2=new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();

	}

	public static double mul(double d1,double d2){
		BigDecimal b1=new BigDecimal(Double.toString(d1));
		BigDecimal b2=new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();

	}

	public static double div(double d1,double d2){

		return div(d1,d2,DEF_DIV_SCALE);

	}

	public static double div(double d1,double d2,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1=new BigDecimal(Double.toString(d1));
		BigDecimal b2=new BigDecimal(Double.toString(d2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();

	}
}
