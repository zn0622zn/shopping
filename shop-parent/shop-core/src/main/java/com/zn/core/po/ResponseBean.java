package com.zn.core.po;

import java.io.Serializable;

/**
 * 响应
 */
public class ResponseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标记
	 */
	private boolean success = true;
	/**
	 * 提示信息
	 */
	private String message = "操作成功";
	/**
	 * 实体类
	 */
	private Object object;
	/**
	 * 响应状态码(成功)
	 */
	private int code = 200;
	/**
	 * JWT
	 */
	private String token;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
