package com.jqdi.smssender.core.baidu;

import java.util.Map;

import com.baidubce.BceClientException;
import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 百度云短信客户端
 * 
 * <pre>
 * 官方文档：https://cloud.baidu.com/doc/SMS/s/lkijy5wvf
 * </pre>
 */
@Slf4j
public class SmsClient {

	private com.baidubce.services.sms.SmsClient client = null;

	public SmsClient(String accessKeyId, String secretKey) {
		SmsClientConfiguration config = new SmsClientConfiguration();
		BceCredentials credentials = new DefaultBceCredentials(accessKeyId, secretKey);
		config.withCredentials(credentials);
		this.client = new com.baidubce.services.sms.SmsClient(config);
	}

	public SendResponse send(String mobile, String signatureId, String template, Map<String, String> contentVar) {
		SendMessageV3Request request = new SendMessageV3Request();
		request.setMobile(mobile);
		request.setSignatureId(signatureId);
		request.setTemplate(template);
		request.setContentVar(contentVar);

		SendResponse resp = new SendResponse();
		try {
			SendMessageV3Response response = client.sendMessage(request);
			log.info("response:{}", response);

			resp.setRequestId(response.getRequestId());
			if (response.isSuccess()) {
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setMessage(response.getMessage());
			}
		} catch (BceClientException e) {
			log.error("BceClientException error", e);
			resp.setSuccess(false);
			resp.setMessage(e.getMessage());
		}

		return resp;
	}

}