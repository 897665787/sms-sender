package com.jqdi.core;

import java.util.LinkedHashMap;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.log.SmsClient;

public class LogClientTest {

	public static void main(String[] args) {
		SmsClient client = new SmsClient();

		String mobile = "15220163215";
		String signName = "测试";
		String templateCode = "SMS_213693660";
		LinkedHashMap<String, String> templateParamMap = new LinkedHashMap<>();
		templateParamMap.put("code", "123456");

		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamMap);
		System.out.println("sendResponse:" + sendResponse);
	}
}