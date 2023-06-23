package com.jqdi.smssender.core;

import java.util.LinkedHashMap;

public interface SmsSender {
	/**
	 * 获取调用渠道
	 * 
	 * @return 调用渠道 Constants.Channel
	 */
	String channel();

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param templateCode
	 * @param templateParamMap
	 * @return
	 */
	SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap);
}