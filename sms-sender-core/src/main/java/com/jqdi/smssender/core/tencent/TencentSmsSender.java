package com.jqdi.smssender.core.tencent;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class TencentSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public TencentSmsSender(String appId, String regionId, String accessKey, String secretKey, String signName) {
		this(appId, regionId, accessKey, secretKey, signName, null);
	}

	public TencentSmsSender(String appId, String regionId, String accessKey, String secretKey, String signName,
			SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(appId, regionId, accessKey, secretKey);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap) {
		Collection<String> templateParamList = templateParamMap.values();
		String[] templateParamArray = templateParamList.toArray(new String[] {});
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamArray);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend("tencent", mobile, signName, templateCode, templateParamMap, sendResponse);
		}
		return sendResponse;
	}
}