package com.whoiszxl.house.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;
import com.whoiszxl.house.biz.mapper.UserMapper;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.utils.BeanHelper;
import com.whoiszxl.house.common.utils.HashUtils;

@Service
public class UserService {
	
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
	private FileService fileService;

	@Autowired
	private MailService mailService;
	
	@Value("${domain.name}")
	private String domainName;
	
	
	
	public List<User> getUsers() {
		return userMapper.selectUsers();
	}

	/**
	 * 1.将用户添加到数据库，状态为未激活 2.生成一个key，绑定email 3.将key以邮件发送给用户
	 * 
	 * @param account
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class) // 需要在其他类调用，事务才会生效
	public boolean addAcount(User account) {
		// 1.将密码加密
		account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
		// 2.上传头像并获取到头像的地址
		List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
		if (!imgList.isEmpty()) {
			account.setAvatar(imgList.get(0));
		}
		// 3.通过工具类设置对象的默认值和插入时间
		BeanHelper.setDefaultProp(account, User.class);
		BeanHelper.onInsert(account);
		// 4.设置账号为未激活
		account.setEnable(0);
		// 5.将user对象插入到数据库
		userMapper.insert(account);
		// 6.发送邮件
		registerNotify(account.getEmail());
		return false;
	}

	/**
	 * 发送邮件操作 1.缓存key-email的关系 2.借助spring mail 发送邮件 3.借助异步框架进行异步操作
	 * 
	 * @param email
	 */
	@Async//使用异步方式操作，在app也需要添加一个开启异步的注解
	private void registerNotify(String email) {
		//1.创建随机key并且存入cache中绑定好email
		String randomKey = RandomStringUtils.randomAlphabetic(10);
		registerCache.put(randomKey, email);
		
		//2.创建一个url发送给用户
		String url = "http://" + domainName + "/accounts/verify?key="+randomKey;
		mailService.sendMail("whoiszxl.com邮件账号激活",url,email);
	}
}
