package com.sanxin.cloud.common;

import com.sanxin.cloud.common.pwd.Encode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.enums.RandNumType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


public class FunctionUtils {
	
	/*
	 * 获取唯一编号
	 * 
	 * @param a  数据来源平台 表示业务类型前缀
	 * @param b  操作对象   1.生成账号资金明细流水号
	 * @return
	 */
	public static final String getOrderCode(String a) {
		String time;
		Calendar cal = new java.util.GregorianCalendar();
		int random = (int) (Math.random() * 900) + 100;
		time = "" + a + cal.get(Calendar.HOUR) + String.valueOf(random).charAt(0) + cal.get(Calendar.MINUTE) + String.valueOf(random).charAt(1) + cal.get(Calendar.SECOND)
				+ String.valueOf(random).charAt(2) + cal.get(Calendar.MILLISECOND);
		return time;
	}
	/**
	 * BigDecimal 加减乘除
	 */
	/**
	 * 加
	 * 
	 * @param a1
	 *            加数
	 * @param a2
	 *            被加数
	 * @param index
	 *            保留位数
	 * @return 四舍五入 保留两位小数
	 */
	public static BigDecimal add(BigDecimal a1, BigDecimal a2, int index) {
		return a1.add(a2).setScale(index, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 减
	 * 
	 * @param a1
	 *            减数
	 * @param a2
	 *            被减数
	 * @param index
	 *            保留位数
	 * @return 四舍五入 保留两位小数
	 */
	public static BigDecimal sub(BigDecimal a1, BigDecimal a2, int index) {
		return a1.subtract(a2).setScale(index, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 乘
	 * 
	 * @param a1  乘数
	 * @param a2  被乘数
	 * @param index
	 *            保留位数
	 * @return 四舍五入 保留两位小数
	 */
	public static BigDecimal mul(BigDecimal a1, BigDecimal a2, int index) {
		return a1.multiply(a2).setScale(index, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 除
	 * 
	 * @param d1 除数
	 * @param d2
	 *            被除数
	 * @param index
	 *            保留位数
	 * @return 四舍五入 保留两位小数
	 */
	public static BigDecimal div(BigDecimal d1, BigDecimal d2, int index) {
		if(d2.compareTo(BigDecimal.ZERO)==0) {
			return BigDecimal.ZERO;
		}
		return d1.divide(d2, index, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 判断两个int类型的是否相等
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEquals(Integer a, Integer b) {
		if (a == null) {
			if (b.equals(a) || b == a) {
				return true;
			}
		} else {
			if (a.equals(b) || b == a) {
				return true;
			}
		}
		return false;
	}
	
	public static String getUUid() {
		String uuid=UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		uuid= Encode.encode(uuid);
		return uuid;
	}
	
	
	/**
	 * 字符串数组转list int对象
	 * 
	 * @param arr
	 * @return
	 */
	public static List<Integer> getIntegerList(String[] arr) {
		List<Integer> list = new ArrayList<Integer>();
		for (String s : arr) {
			if (!StringUtils.isEmpty(s)) {
				Integer a = Integer.parseInt(s);
				list.add(a);
			}
		}
		return list;
	}
	
	/**
	 * 字符串数组转list int对象 倒叙
	 * 
	 * @param arr
	 * @return
	 */
	public static List<Integer> getListDesc(String[] arr) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = arr.length - 1; i > -1; i--) {
			String s = arr[i];
			if (!StringUtils.isEmpty(s)) {
				Integer a = Integer.parseInt(s);
				list.add(a);
			}
		}
		return list;
	}
	
	/**
	 * 字符串数组转list int对象 倒叙
	 * 
	 * @param arr
	 * @return
	 */
	public static List<Integer> getListDesc(String[] arr,int max) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i =arr.length- 1; i > -1; i--) {
			String s = arr[i];
			if (!StringUtils.isEmpty(s)) {
				Integer a = Integer.parseInt(s);
				list.add(a);
			}
			if(list.size()==max) {
				break;
			}
		}
		return list;
	}

	/**
	 * 获取两个数随机
	 * @param minscale 最小
	 * @param maxmoney 最大
	 * @return
	 */
	public static BigDecimal getScale(BigDecimal minscale, BigDecimal maxmoney) {
		  BigDecimal db = new BigDecimal(Math.random() * (maxmoney.doubleValue() - minscale.doubleValue()) + minscale.doubleValue());  
	      db=db.setScale(2, BigDecimal.ROUND_HALF_UP);
		return db;
	}
	
	public static String getCodes(String paycode,Integer num) {
		String str="";
		for(int i=0;i<num;i++) {
			String code=paycode+ RandNumUtils.get(RandNumType.NUMBER, 6);
			str+=","+code;
		}
		str=str.substring(1, str.length()-1);
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(getCodes("190806", 10));
	}
}
