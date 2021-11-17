package com.umpay.online.tools.util;


import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 生成流水序列号
 * @author xiaoyang
 *
 */
public class SequenceGenerator {
	public static final String FIXED = "U";
	/**
	 * 获取流水号
	 */
	public static String getSequence(){
		//获取系统时间带毫秒
		String dateTime = DateTimeFormatter.ofPattern("MMddHHmmssSSS").format(LocalDateTime.now());
		//获取可变随机数8位
		String randomKey = RandomUtil.randomString(2);
		return StringUtils.join(FIXED,dateTime,randomKey);
	}

}
