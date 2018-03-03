package com.whoiszxl.house.api.config;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

public class FastJsonHttpMessageConvert5 extends FastJsonHttpMessageConverter4{

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public FastJsonHttpMessageConvert5() {
		setDefaultCharset(DEFAULT_CHARSET);
		setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+")));
	}
	
}
