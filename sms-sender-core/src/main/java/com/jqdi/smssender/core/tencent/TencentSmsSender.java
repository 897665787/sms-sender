package com.jqdi.smssender.core.tencent;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
	public SendResponse send(String mobile, String templateCode, String templateParamJson) {
		// TencentSms的依赖包中含有gson，所以利用gson来处理
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> templateParamList = new Gson().fromJson(templateParamJson, type);
		
		String[] templateParamArray = templateParamList.toArray(new String[] {});
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamArray);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend("tencent", mobile, signName, templateCode, templateParamJson, sendResponse);
		}
		return sendResponse;
	}
}