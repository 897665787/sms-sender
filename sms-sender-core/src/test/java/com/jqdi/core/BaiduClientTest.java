package com.jqdi.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.baidu.SmsClient;

public class BaiduClientTest {

	public static void main(String[] args) {
		String accessKeyId = "LTAI5tRK8vFDnEijSRxNyDEs";
		String secretKey = "ZIIW2Gt71FU8l48JyCIe4gzXmMYESe";

		SmsClient client = new SmsClient(accessKeyId, secretKey);

		String mobile = "15220163215";
		String signatureId = "快递驿站中心";
		String template = "SMS_213693660";
		Map<String, String> contentVar = new LinkedHashMap<>();
		contentVar.put("code", "1234");

		SendResponse sendResponse = client.send(mobile, signatureId, template, contentVar);
		System.out.println("sendResponse:" + sendResponse);
	}
}