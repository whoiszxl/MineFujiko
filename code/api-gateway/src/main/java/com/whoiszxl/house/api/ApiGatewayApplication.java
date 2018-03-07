package com.whoiszxl.house.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.house.api.config.NewRuleConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
//@RibbonClient(name="user", configuration=NewRuleConfig.class)
public class ApiGatewayApplication {
	
	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@RequestMapping("index1")
	@ResponseBody
	public List<ServiceInstance> getRegister() {
		return discoveryClient.getInstances("user");
	}
	
	
}
