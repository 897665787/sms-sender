package com.jqdi.smssender.core.leshua;

import java.util.LinkedHashMap;

import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class LeshuaSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public LeshuaSmsSender(String url, String key, String source, String signName) {
		this(url, key, source, signName, null);
	}

	public LeshuaSmsSender(String url, String key, String source, String signName,
			SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(url, key, source);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.LESHUA;
	}

	@Override
	public SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap,
			String content) {
		SendResponse sendResponse = client.send(mobile, signName, content);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend("leshua", mobile, signName, templateCode, templateParamMap, content,
					sendResponse);
		}
		return sendResponse;
	}
}