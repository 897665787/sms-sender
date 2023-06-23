package com.jqdi.smssender.core.jingdong;

import java.util.ArrayList;
import java.util.List;

import com.jdcloud.sdk.auth.CredentialsProvider;
import com.jdcloud.sdk.auth.StaticCredentialsProvider;
import com.jdcloud.sdk.service.sms.model.BatchSendRequest;
import com.jdcloud.sdk.service.sms.model.BatchSendResp;
import com.jdcloud.sdk.service.sms.model.BatchSendResult;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 京东云短信客户端
 * 
 * <pre>
 * 官方文档：https://docs.jdcloud.com/cn/text-message/java
 * </pre>
 */
@Slf4j
public class SmsClient {
	
	private com.jdcloud.sdk.service.sms.client.SmsClient client = null;
	private String regionId = null;

	public SmsClient(String regionId, String accessKeyId, String secretKey) {
		CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId, secretKey);
		this.client = com.jdcloud.sdk.service.sms.client.SmsClient.builder().credentialsProvider(provider).build();
		this.regionId = regionId;
	}

	public SendResponse send(String mobile, String signId, String templateId, List<String> params) {
		BatchSendRequest request = new BatchSendRequest();
		request.setRegionId(regionId);
		request.setTemplateId(templateId);
		request.setSignId(signId);
		List<String> phoneList = new ArrayList<>();
		phoneList.add(mobile);
		request.setPhoneList(phoneList);
		request.setParams(params);
		
		SendResponse resp = new SendResponse();
		BatchSendResult response = client.batchSend(request).getResult();
		log.info("response:{}", response);
		
		if (response == null) {
			resp.setSuccess(false);
			resp.setMessage("无响应结果");
			return resp;
		}
		
		BatchSendResp batchSendResp = response.getData();
		resp.setRequestId(batchSendResp.getSequenceNumber());
		Boolean status = response.getStatus();
		if (status) {
			resp.setSuccess(true);
		} else {
			resp.setSuccess(false);
			resp.setMessage(response.getMessage());
		}
		return resp;
	}

}