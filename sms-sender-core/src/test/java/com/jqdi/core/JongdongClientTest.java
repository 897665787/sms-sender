package com.jqdi.core;

import java.util.ArrayList;
import java.util.List;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.jingdong.SmsClient;

public class JongdongClientTest {

	public static void main(String[] args) {
		String regionId = "cn-north-1";
		String accessKeyId = "LTAI5tRK8vFDnEijSRxNyDEs";
		String secretKey = "ZIIW2Gt71FU8l48JyCIe4gzXmMYESe";

		SmsClient client = new SmsClient(regionId, accessKeyId, secretKey);

		String mobile = "15220163215";
		String signId = "快递驿站中心";
		String templateId = "SMS_213693660";

		List<String> params = new ArrayList<>();
		params.add("1234");
		SendResponse sendResponse = client.send(mobile, signId, templateId, params);
		System.out.println("sendResponse:" + sendResponse);
	}
}