package com.whoiszxl.house;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseApplicationTests {

	@Autowired
	private HttpClient httpClient;
	
	@Test
	public void contextLoads() {
	}
	
	
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException {
		HttpEntity a = httpClient.execute(new HttpGet("http://whoiszxl.com")).getEntity();
		
		System.out.println(EntityUtils.toString(a));
	}

}
