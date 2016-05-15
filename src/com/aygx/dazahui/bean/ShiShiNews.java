package com.aygx.dazahui.bean;
/**
 * 从网络中获取实时新闻的类，用于封装Json数据
 * @author Administrator
 *
 */
public class ShiShiNews {
	
	private String reason;//查询的结果
	private String[] result;//查询的信息
	private int error_code;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String[] getResult() {
		return result;
	}
	public void setResult(String[] result) {
		this.result = result;
	}
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	
	
}
