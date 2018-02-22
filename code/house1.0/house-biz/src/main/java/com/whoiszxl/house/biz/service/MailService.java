package com.whoiszxl.house.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	
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

}
