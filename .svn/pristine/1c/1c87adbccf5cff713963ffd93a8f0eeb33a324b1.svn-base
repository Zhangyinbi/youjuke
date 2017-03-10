package com.youjuke.buildingmaterialmall.retrofit;

import com.youjuke.swissgearlibrary.utils.L;

import java.util.Map;

/**
 * 描述: 请求封装类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-09-12 14:42
 */
public class RequestBean {

	public RequestBean(JsonMsg json_msg) {
		this.json_msg = json_msg;
	}

	private JsonMsg json_msg;

	public static class JsonMsg {
		private String function_name;
		private Map<String,Object> params;

		public JsonMsg(String function_name, Map<String, Object> params) {
			this.function_name = function_name;
			this.params = params;
		}

		public JsonMsg() {
		}

		public String getFunction_name() {
			return function_name;
		}

		public void setFunction_name(String function_name) {
			this.function_name = function_name;
		}

		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}

		public String toJson(){
			if (this != null){
				String format = RequestFormat.getInstance().format(this);
				L.i("请求参数",format);
				return format;
			}
			else
				return null;
		}
	}

	public JsonMsg getJson_msg() {
		return json_msg;
	}

	public void setJson_msg(JsonMsg json_msg) {
		this.json_msg = json_msg;
	}
}
