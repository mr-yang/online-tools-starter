package com.umpay.online.tools.util;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : gaozhiguo
 * @date : 2020-04-29 11:19
 * @version : V1.0
 * @description : 获取随机数
 **/
public class RandomUtil {

	/**
	 * 根据字符串数组制定长度，随机生成字符串
	 * @param keys
	 * @param length2
	 * @return
	 */
	public  static String getRandomKeys(String[] keys, int length2) {
		return RandomStringUtils.random(length2, StringUtils.join(keys, ""));
	}
	
	/**
	 * 获取指定数量的字符
	 * @param randomNum 获取几位字符
	 * @param randomString 全量字符
	 * @return
	 */
	public static String randomString(Integer randomNum,String randomString){
		String random = RandomStringUtils.random(randomNum,randomString);
		return random;
	}
	
	/**
	 * 随机制定长度字符串
	 * @param length
	 * @return
	 */
	public static String randomString(int length){
		return RandomStringUtils.randomNumeric(length);
	}

	/**
	 * @author : gaozhiguo
	 * @date : 2020-03-27 14:37
	 * @version : V1.0
	 * @description : 获取制定数字内的随机数
	 **/
	public static Integer getRandomValue(int max){
		Random random = new Random();
		return random.nextInt(max) % (max + 1);
	}

	/**
	 * @author : gaozhiguo
	 * @date : 2020-03-30 15:35
	 * @version : V1.0
	 * @description : 获取指定数字区间内
	 **/
	public static List<Integer> getRandomValueList(int max, int count){
		Random random = new Random();
		List<Integer>  indexList = new ArrayList<Integer>();
		for(int i=0; i< count; i++){
			indexList.add(random.nextInt(max) % (max + 1));
		}
		return indexList;
	}
}
