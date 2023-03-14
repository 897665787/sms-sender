package com.jqdi.smssender.core.log;

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
	public SendResponse send(String mobile, String templateCode, String templateParamJson) {
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamJson);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend("log", mobile, signName, templateCode, templateParamJson, sendResponse);
		}
		return sendResponse;
	}
}