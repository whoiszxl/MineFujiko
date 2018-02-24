package com.whoiszxl.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置攔截器的執行規則
 * @author whoiszxl
 *
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter{

	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Autowired
	private AuthActionInterceptor authActionInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//設置攔截除了"/static"的任何請求
		registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
		//設置action攔截器攔截"/accounts/profile"這個路徑
		registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");
		//registry會按順序執行所添加的攔截器
		super.addInterceptors(registry);
	}
}
