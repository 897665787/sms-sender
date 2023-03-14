package com.jqdi.core;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.log.SmsClient;

public class LogClientTest {

	public static void main(String[] args) {
		SmsClient client = new SmsClient();

		String mobile = "15220163215";
		String signName = "测试";
		String templateCode = "SMS_213693660";
		String templateParam = "{\"code\":\"1234\"}";

		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParam);
		System.out.println("sendResponse:" + sendResponse);
	}
}