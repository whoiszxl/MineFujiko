package com.whoiszxl.house.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月14日 下午10:37:16
* @description 
*/
@Configuration
public class DruidConfig {

	@ConfigurationProperties(prefix="spring.druid")
	@Bean(initMethod="init", destroyMethod="close")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		return dataSource;
	}
	
	/**
	 * 慢日志记录
	 * @return
	 */
	@Bean
	public Filter statFilter() {
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(1);
		filter.setLogSlowSql(true);
		filter.setMergeSql(true);
		return filter;
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
	}
}
