package com.bais1.util;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
	/**
	 * 测试
	 */
	public static void main(String[] args) {
		System.out.println(com.bais1.util.UuidUtil.getUuid());
		System.out.println(com.bais1.util.UuidUtil.getUuid());
		System.out.println(com.bais1.util.UuidUtil.getUuid());
		System.out.println(com.bais1.util.UuidUtil.getUuid());
	}
}
