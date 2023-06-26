package com.jqdi.smssender.springbootdemo.util;

import org.apache.commons.lang3.StringUtils;

public class Utils {
	
	/**
	 * 以/拼接remark(取左侧200字符)
	 * 
	 * @param oldRemark
	 * @param newRemark
	 * @return
	 */
	public static String appendRemark(String oldRemark, String newRemark) {
		if (StringUtils.isBlank(newRemark)) {
			return oldRemark;
		}
		String remark = newRemark;
		if (StringUtils.isNotBlank(oldRemark)) {
			remark = StringUtils.join(new String[] { oldRemark, remark }, "/");
		}
		remark = StringUtils.substring(remark, 0, 200);
		return remark;
	}
	
	/**
	 * 以/拼接remark(取右侧200字符)
	 * 
	 * @param oldRemark
	 * @param newRemark
	 * @return
	 */
	public static String rightRemark(String oldRemark, String newRemark) {
		if (StringUtils.isBlank(newRemark)) {
			return oldRemark;
		}
		String remark = newRemark;
		if (StringUtils.isNotBlank(oldRemark)) {
			remark = StringUtils.join(new String[] { oldRemark, remark }, "/");
		}
		remark = StringUtils.right(remark, 200);
		return remark;
	}
}
