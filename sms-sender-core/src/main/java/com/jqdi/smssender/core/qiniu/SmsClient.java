package com.jqdi.smssender.core.qiniu;

import java.util.Map;
import java.util.Optional;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.qiniu.client.QiniuSmsManager;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;

import lombok.extern.slf4j.Slf4j;

/**
 * 七牛云短信客户端
 * 
 * <pre>
 * 官方文档：https://developer.qiniu.com/sms/5897/sms-api-send-message
 * </pre>
 */
@Slf4j
public class SmsClient {

	private QiniuSmsManager client = null;

	public SmsClient(String accessKey, String secretKey) {
		Auth auth = Auth.create(accessKey, secretKey);
		client = new QiniuSmsManager(auth);
	}

	public SendResponse send(String mobile, String signatureId, String templateId, Map<String, String> parameters) {
		SendResponse resp = new SendResponse();
		try {
			Response response = client.sendSingleMessage(signatureId, templateId, mobile, parameters);
			log.info("response:{}", response);

			resp.setRequestId(response.reqId);
			if (response.isOK()) {
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setMessage(response.error);
			}
		} catch (QiniuException e) {
			log.error("QiniuException error", e);
			resp.setSuccess(false);
			resp.setMessage(e.getMessage());
			resp.setRequestId(Optional.ofNullable(e.response).map(v -> v.reqId).orElse(null));
		}

		return resp;
	}

}