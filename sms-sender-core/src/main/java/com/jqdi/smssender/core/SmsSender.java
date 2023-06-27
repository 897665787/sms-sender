package com.jqdi.smssender.core;

import java.util.LinkedHashMap;

/**
 * 短信发送器
 * 
 * @author JQ棣
 */
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
	 *            手机号
	 * @param signName
	 *            签名（为空则使用配置的signName）
	 * @param templateCode
	 *            模板编码（以content作为发送方式的话可以不填）
	 * @param templateParamMap
	 *            模板编码参数（以content作为发送方式的话可以不填）
	 * @param content
	 *            短信内容（以templateCode作为发送方式的话可以不填）
	 * @return
	 */
	SendResponse send(String mobile, String signName, String templateCode,
			LinkedHashMap<String, String> templateParamMap, String content);

	/**
	 * 发送短信（固定使用配置的signName）
	 * 
	 * @param mobile
	 *            手机号
	 * @param templateCode
	 *            模板编码（以content作为发送方式的话可以不填）
	 * @param templateParamMap
	 *            模板编码参数（以content作为发送方式的话可以不填）
	 * @param content
	 *            短信内容（以templateCode作为发送方式的话可以不填）
	 * @return
	 */
	default SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap,
			String content) {
		return send(mobile, null, templateCode, templateParamMap, content);
	}
}