package com.youjuke.buildingmaterialmall.retrofit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 描述: 把RequestBean 格式成json格式
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-12 14:54
 */
public class RequestFormat{

	private static RequestFormat instance;
	private Gson gson;

	private RequestFormat (){
		gson = new Gson();
	}

	public static RequestFormat getInstance(){
		if (instance ==null){
			synchronized (RequestFormat.class){
				instance = new RequestFormat();
			}
		}
		return instance;
	}

	public String format(RequestBean.JsonMsg bean){
		if (bean!=null){
			return gson.toJson(bean,new TypeToken<RequestBean.JsonMsg>(){}.getType());
		}
		return null;
	}




}
