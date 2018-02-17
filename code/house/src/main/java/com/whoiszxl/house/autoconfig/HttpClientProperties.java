package com.whoiszxl.house.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月17日 下午10:21:45
* @description 
*/
@ConfigurationProperties(prefix="spring.httpclient")
public class HttpClientProperties {

	private Integer connectTimeOut = 1000;
	private Integer socketTimeOut = 10000;
	
	private String agent = "agent";
	private Integer maxConnPerRoute = 10;
	private Integer maxConnTotal = 50;
	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(Integer connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}
	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public Integer getMaxConnPerRoute() {
		return maxConnPerRoute;
	}
	public void setMaxConnPerRoute(Integer maxConnPerRoute) {
		this.maxConnPerRoute = maxConnPerRoute;
	}
	public Integer getMaxConnTotal() {
		return maxConnTotal;
	}
	public void setMaxConnTotal(Integer maxConnTotal) {
		this.maxConnTotal = maxConnTotal;
	}
}
