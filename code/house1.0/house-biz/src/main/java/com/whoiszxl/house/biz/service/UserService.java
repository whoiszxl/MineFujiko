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
	
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FileService fileService;

	@Autowired
	private MailService mailService;
	
	@Value("${file.prefix}")
	private String ImgPrefix;
	
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
		List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
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
		mailService.registerNotify(account.getEmail());
		return true;
	}

	public boolean enable(String key) {
		return mailService.enable(key);
	}

	public User auth(String username, String password) {
		User user = new User();
		user.setEmail(username);
		user.setPasswd(HashUtils.encryPassword(password));
		user.setEnable(1);
		List<User> list = getUserByQuery(user);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<User> getUserByQuery(User user) {
		List<User> userList = userMapper.selectUsersByQuery(user);
		userList.forEach(u -> {
			u.setAvatar(ImgPrefix + u.getAvatar());
		});
		return userList;
	}

	public void updateUser(User updateUser, String email) {
		updateUser.setEmail(email);
		BeanHelper.onUpdate(updateUser);
		userMapper.update(updateUser);
	}

}
