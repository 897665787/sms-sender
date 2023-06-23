package com.jqdi.smssender.core.baidu;

import java.util.LinkedHashMap;

import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class BaiduSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public BaiduSmsSender(String accessKey, String secretKey, String signName) {
		this(accessKey, secretKey, signName, null);
	}

	public BaiduSmsSender(String accessKey, String secretKey, String signName, SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(accessKey, secretKey);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.BAIDU;
	}

	@Override
	public SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap,
			String content) {
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamMap);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend(Constants.Channel.BAIDU, mobile, signName, templateCode, templateParamMap,
					content, sendResponse);
		}
		return sendResponse;
	}
}