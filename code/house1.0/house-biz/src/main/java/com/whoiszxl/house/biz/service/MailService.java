package com.whoiszxl.house.biz.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.whoiszxl.house.biz.mapper.UserMapper;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class MailService {

	private final Cache<String, String> registerCache = CacheBuilder
			.newBuilder()
			.maximumSize(100)//设置缓存数量最大100
			.expireAfterAccess(15, TimeUnit.MINUTES)//设置失效时间为15分钟
			.removalListener(new RemovalListener<String, String>() {
				public void onRemoval(RemovalNotification<String, String> notification) {
					//通过用户邮箱删除用户
					userMapper.delete(notification.getValue());
				};
			}).build();
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${domain.name}")
	private String domainName;
	
	@Value("${spring.mail.username}")
	private String from;
	
	
	/**
	 * 发送邮件
	 * @param title 邮件标题
	 * @param url 邮件验证url
	 * @param email 邮件接收地址
	 */
	public void sendMail(String title, String url, String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(email);
		message.setText(url);
		mailSender.send(message);
	}
	
	/**
	 * 发送邮件操作 1.缓存key-email的关系 2.借助spring mail 发送邮件 3.借助异步框架进行异步操作
	 * 
	 * @param email
	 */
	@Async//使用异步方式操作，在app也需要添加一个开启异步的注解
	public void registerNotify(String email) {
		//1.创建随机key并且存入cache中绑定好email
		String randomKey = RandomStringUtils.randomAlphabetic(10);
		registerCache.put(randomKey, email);
		
		//2.创建一个url发送给用户
		String url = "http://" + domainName + "/accounts/verify?key="+randomKey;
		sendMail("whoiszxl.com邮件账号激活",url,email);
	}

}
