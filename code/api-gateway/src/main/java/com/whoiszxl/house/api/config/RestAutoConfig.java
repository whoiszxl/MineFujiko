package com.whoiszxl.house.api.config;

import java.nio.charset.Charset;

import org.apache.http.client.HttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

@Configuration
public class RestAutoConfig {

	public static class RestTemplateConfig {
		
		@Bean
		@LoadBalanced //spring对restTemplate bean进行定制,加入loadbalance拦截器进行ip:port的替换
		RestTemplate lbRestTemplate(HttpClient httpClient) {
			RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
			template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
			template.getMessageConverters().add(1, new FastJsonHttpMessageConverter4());
			
			return template;
		}
		
		
		@Bean //直连
		RestTemplate directRestTemplate(HttpClient httpClient) {
			RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
			template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("utf-8")));
			template.getMessageConverters().add(1, new FastJsonHttpMessageConverter4());
			
			return template;
		}
	}
}
