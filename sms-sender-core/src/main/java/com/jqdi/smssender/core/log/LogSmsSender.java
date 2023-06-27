package com.jqdi.smssender.core.log;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

/**
 * 打印日志（用于测试环境不想发送真实短信）
 */
public class LogSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public LogSmsSender(String signName) {
		this(signName, null);
	}

	public LogSmsSender(String signName, SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient();
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.LOG;
	}

	@Override
	public SendResponse send(String mobile, String signName, String templateCode,
			LinkedHashMap<String, String> templateParamMap, String content) {
		if (StringUtils.isBlank(signName)) {
			signName = this.signName;
		}
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamMap);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend(Constants.Channel.LOG, mobile, signName, templateCode, templateParamMap,
					content, sendResponse);
		}
		return sendResponse;
	}
}