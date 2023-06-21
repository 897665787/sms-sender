package com.jqdi.smssender.core.ali;

import java.util.LinkedHashMap;

import com.google.gson.Gson;
import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class AliSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public AliSmsSender(String regionId, String accessKey, String secretKey, String signName) {
		this(regionId, accessKey, secretKey, signName, null);
	}

	public AliSmsSender(String regionId, String accessKey, String secretKey, String signName,
			SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(regionId, accessKey, secretKey);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.ALI;
	}

	@Override
	public SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap,
			String content) {
		// AliSms的依赖包中含有gson，所以利用gson来处理
		String templateParamJson = new Gson().toJson(templateParamMap);
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamJson);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend(Constants.Channel.ALI, mobile, signName, templateCode, templateParamMap,
					content, sendResponse);
		}
		return sendResponse;
	}
}