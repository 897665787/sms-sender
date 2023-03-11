package com.jqdi.core;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.tencent.SmsClient;

public class TencentClientTest {

	public static void main(String[] args) {
		String appId = "1400006666";
		String regionId = "ap-guangzhou";
		String accessKey = "LTAIkcl1bVhsEpGf";
		String secretKey = "D9hwgRigRI1Q6rIE4PBU1lmKsqTpUm";

		SmsClient client = new SmsClient(appId, regionId, accessKey, secretKey);

		String mobile = "+8618511122266";
		String signName = "腾讯云";
		String templateCode = "1234";
		String[] templateParamArray = new String[] { "12345" };

		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamArray);
		System.out.println("sendResponse:" + sendResponse);
	}
}