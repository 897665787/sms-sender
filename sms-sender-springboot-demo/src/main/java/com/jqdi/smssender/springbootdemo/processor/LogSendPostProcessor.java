package com.jqdi.smssender.springbootdemo.processor;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Component;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;

/**
 * <pre>
 * 这个是demo，实际应用中可以将发送记录保存到数据库
 * 如果是引用了sms-sender-jdbc-spring-boot-starter，会覆盖SqlSendPostProcessor的逻辑
 * </pre>
 */
// @Component
public class LogSendPostProcessor implements SendPostProcessor {

	@Override
	public void afterSend(String channel, String mobile, String signName, String templateCode,
			LinkedHashMap<String, String> templateParamMap, String content, SendResponse sendResponse) {
		System.out.println("LogSendPostProcessor.afterSend():" + channel + ",mobile:" + mobile + ",signName:" + signName
				+ ",templateCode:" + templateCode + ",templateParamMap:" + templateParamMap + ",content:" + content
				+ ",sendResponse:" + sendResponse);
	}
}
