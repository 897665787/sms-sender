package com.jqdi.core;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.leshua.SmsClient;
import com.jqdi.smssender.core.leshua.dto.SendSmsResponse;
import com.jqdi.smssender.core.util.XmlUtil;

public class LeshuaClientTest {

	public static void main(String[] args) {
		String url = "";
		String key = "";
		String source = "";
		SmsClient client = new SmsClient(url, key, source);

		String mobile = "";
		String title = "";
		String content = "";
		SendResponse sendResponse = client.send(mobile, title, content);
		System.out.println("sendResponse:" + sendResponse);
		
		String xmlString = "<root><error_msg>phone error</error_msg><result>56</result><sms_id>0</sms_id></root>";
//		String xmlString = "<root><error_msg>send success</error_msg><result>0</result><sms_id>1686733036675629387</sms_id></root>";
		SendSmsResponse sendSmsResponse = XmlUtil.toEntity(xmlString, SendSmsResponse.class);
		System.out.println(sendSmsResponse);
	}
}