package com.jqdi.smssender.core.qiniu;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.smssender.core.Constants;
import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

public class QiniuSmsSender implements SmsSender {
	private SmsClient client = null;
	private String signName = null;
	private SendPostProcessor sendPostProcessor = null;

	public QiniuSmsSender(String accessKey, String secretKey, String signName) {
		this(accessKey, secretKey, signName, null);
	}

	public QiniuSmsSender(String accessKey, String secretKey, String signName, SendPostProcessor sendPostProcessor) {
		this.client = new SmsClient(accessKey, secretKey);
		this.signName = signName;
		this.sendPostProcessor = sendPostProcessor;
	}

	@Override
	public String channel() {
		return Constants.Channel.QINIU;
	}

	@Override
	public SendResponse send(String mobile, String signName, String templateCode,
			LinkedHashMap<String, String> templateParamMap, String content) {
		if (StringUtils.isBlank(signName)) {
			signName = this.signName;
		}
		SendResponse sendResponse = client.send(mobile, signName, templateCode, templateParamMap);
		if (sendPostProcessor != null) {
			sendPostProcessor.afterSend(Constants.Channel.QINIU, mobile, signName, templateCode, templateParamMap,
					content, sendResponse);
		}
		return sendResponse;
	}
}