package com.whoiszxl.house.common.utils;

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * hash加密工具类
 * @author Administrator
 *
 */
public class HashUtils {

private static final HashFunction FUNCTION = Hashing.md5();
	
	private static final String SALT = "549whoiszxl669731";
	
	public static String encryPassword(String password){
	   HashCode hashCode =	FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
	   return hashCode.toString();
	}
	
}
