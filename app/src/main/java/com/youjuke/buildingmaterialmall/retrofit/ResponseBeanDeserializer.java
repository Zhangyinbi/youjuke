package com.youjuke.buildingmaterialmall.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * 描述: 此类 就是为了让 GSON 不解析data字段里的json数据 直接返回里面的字符串
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-13 19:57
 */
public class ResponseBeanDeserializer implements JsonDeserializer<ResponseBean> {
	@Override
	public ResponseBean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		JSONObject jsono = null;
		final ResponseBean responseBean = new ResponseBean();
		try {
			jsono = new JSONObject(jsonObject.toString());
			responseBean.setError(jsono.getString("error"));
			responseBean.setMessage(jsono.getString("message"));
			responseBean.setStatus(jsono.getString("status"));
			responseBean.setData(jsono.getString("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseBean;
	}
}
