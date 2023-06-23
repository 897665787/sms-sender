package com.jqdi.smssender.core.log;

import java.util.LinkedHashMap;
import java.util.UUID;

import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 打印日志短信客户端（用于测试环境不想发送真实短信）
 */
@Slf4j
public class SmsClient {

	public SmsClient() {
	}

	public SendResponse send(String mobile, String signName, String templateCode, LinkedHashMap<String, String> templateParamMap) {
		log.info("not send sms,mobile:{},signName:{},templateCode:{},templateParam:{}", mobile, signName, templateCode,
				templateParamMap);

		SendResponse resp = new SendResponse();
		resp.setSuccess(true);
		resp.setRequestId(UUID.randomUUID().toString().replace("-", ""));
		return resp;
	}
}