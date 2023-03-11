package com.jqdi.smssender.core.ali;

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
	public SendResponse send(String mobile, String templateCode, String templateParamJson) {
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamJson);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend("ali", mobile, signName, templateCode, templateParamJson, sendResponse);
		}
		return sendResponse;
	}
}