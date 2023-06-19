package com.jqdi.smssender.core.huawei;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.sms.v3.model.CreateTaskRequest;
import com.huaweicloud.sdk.sms.v3.model.CreateTaskResponse;
import com.huaweicloud.sdk.sms.v3.model.PostTask;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 华为云短信客户端
 * 
 * <pre>
 * 官方文档：https://support.huaweicloud.com/api-msgsms/sms_05_0001.html
 * </pre>
 */
@Slf4j
public class SmsClient {

	private com.huaweicloud.sdk.sms.v3.SmsClient client = null;

	public SmsClient(String appId, String regionId, String accessKeyId, String accessKeySecret) {
		ICredential credential = new BasicCredentials();
		this.client = com.huaweicloud.sdk.sms.v3.SmsClient.newBuilder()
				.withCredential(credential ).build();
	}

	public SendResponse send(String mobile, String signName, String templateCode, String[] templateParamArray) {
		CreateTaskRequest request = new CreateTaskRequest();
		PostTask body = new PostTask();
		body.setRegionId(regionId);
		request.setBody(body);

		SendResponse resp = new SendResponse();
		CreateTaskResponse response = client.createTask(request);
		log.info("response:{}", response);
		resp.setRequestId(response.getId());
		int httpStatusCode = response.getHttpStatusCode();
		if (httpStatusCode == 0) {
			resp.setSuccess(true);
		} else {
			resp.setSuccess(false);
			resp.setMessage("发送失败");
		}
		return resp;
	}

}