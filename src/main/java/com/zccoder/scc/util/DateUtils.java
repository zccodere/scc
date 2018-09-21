package com.zccoder.scc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* 日期转换工具类
* @create ZhangCheng by 2017-06-20
*
*/
public class DateUtils {
	
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	* 功能：按照 "yyyy-MM-dd HH:mm:ss" 格式将当前日期时间转换成字符串
	*/
	public static String dateToStr() {
	   return dateToStr(new Date(),DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	* 功能：按照 "yyyy-MM-dd HH:mm:ss" 格式将日期转换成字符串
	*/
	public static String dateToStr(Date date) {
	   return dateToStr(date,DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	* 功能：按照指定格式将日期转换成字符串
	*/
	public static String dateToStr(Date date,String format) {
	   return new SimpleDateFormat(format).format(date);
	}

	/**
	* 功能：字符串转换成日期
	*/
	public static Date strToDate(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
}
