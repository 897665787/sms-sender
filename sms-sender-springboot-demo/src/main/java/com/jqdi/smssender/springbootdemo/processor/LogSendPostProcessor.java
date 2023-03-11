package com.jqdi.smssender.springbootdemo.processor;

import org.springframework.stereotype.Component;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;

/**
 * 这个是demo，实际应用中可以将发送记录保存到数据库
 */
@Component
public class LogSendPostProcessor implements SendPostProcessor {

	@Override
	public void afterSend(String channel, String mobile, String signName, String templateCode, String templateParamJson,
			SendResponse sendResponse) {
		System.out.println("LogSendPostProcessor.afterSend():" + channel + ",mobile:" + mobile + ",signName:" + signName
				+ ",templateCode:" + templateCode + ",templateParamJson:" + templateParamJson + ",sendResponse:"
				+ sendResponse);
	}
}
