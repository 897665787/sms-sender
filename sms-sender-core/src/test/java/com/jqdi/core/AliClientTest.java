package com.jqdi.core;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.ali.SmsClient;

public class AliClientTest {

	public static void main(String[] args) {
		String regionId = "cn-hangzhou";
		String accessKey = "LTAI5tRK8vFDnEijSRxNyDEs";
		String secretKey = "ZIIW2Gt71FU8l48JyCIe4gzXmMYESe";

		SmsClient client = new SmsClient(regionId, accessKey, secretKey);

		String mobile = "15220163215";
		String signName = "快递驿站中心";
		String templateCode = "SMS_213693660";
		String templateParam = "{\"code\":\"1234\"}";

		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParam);
		System.out.println("sendResponse:" + sendResponse);
	}
}