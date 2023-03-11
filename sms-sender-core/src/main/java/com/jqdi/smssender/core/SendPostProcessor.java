package com.jqdi.smssender.core;

/**
 * 短信发送后置处理器，例如用于记录发送记录等
 * 
 * @author JQ棣
 *
 */
public interface SendPostProcessor {

	/**
	 * 短信发送之后执行
	 * 
	 * @param channel
	 * @param mobile
	 * @param signName
	 * @param templateCode
	 * @param templateParamJson
	 * @param sendResponse
	 */
	void afterSend(String channel, String mobile, String signName, String templateCode, String templateParamJson,
			SendResponse sendResponse);
}