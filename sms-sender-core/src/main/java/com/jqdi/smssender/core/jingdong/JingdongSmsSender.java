package com.jqdi.smssender.core.jingdong;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class JingdongSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public JingdongSmsSender(String regionId, String accessKey, String secretKey, String signName) {
		this(regionId, accessKey, secretKey, signName, null);
	}

	public JingdongSmsSender(String regionId, String accessKey, String secretKey, String signName,
			SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(regionId, accessKey, secretKey);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.JINGDONG;
	}

	@Override
	public SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap,
			String content) {
		List<String> templateParamList = templateParamMap.values().stream().collect(Collectors.toList());
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamList);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend(Constants.Channel.JINGDONG, mobile, signName, templateCode, templateParamMap,
					content, sendResponse);
		}
		return sendResponse;
	}
}