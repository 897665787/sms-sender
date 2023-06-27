package com.jqdi.smssender.core.ali;

import java.util.Optional;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云短信客户端
 * 
 * <pre>
 * 官方文档：https://help.aliyun.com/document_detail/419273.htm?spm=a2c4g.11186623.0.0.1f126cf0cAYBIr
 * </pre>
 */
@Slf4j
public class SmsClient {

	private IAcsClient client = null;

	public SmsClient(String regionId, String accessKey, String secretKey) {
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, secretKey);
		this.client = new DefaultAcsClient(profile);
	}

	public SendResponse send(String mobile, String signName, String templateCode, String templateParam) {
		SendSmsRequest request = new SendSmsRequest();
		request.setPhoneNumbers(mobile);
		request.setSignName(signName);
		// 您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
		request.setTemplateCode(templateCode);
		request.setTemplateParam(templateParam);

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		// request.setOutId("yourOutId");

		SendResponse resp = new SendResponse();
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			log.info("response:{}", response);

			resp.setRequestId(response.getRequestId());
			String code = response.getCode();
			if ("OK".equals(code)) {
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setMessage(response.getMessage());
			}
		} catch (ServerException e) {
			log.error("ServerException error", e);
			resp.setSuccess(false);
			resp.setMessage(Optional.ofNullable(e.getErrMsg()).orElse(e.getMessage()));
			resp.setRequestId(e.getRequestId());
		} catch (ClientException e) {
			log.error("ClientException error", e);
			resp.setSuccess(false);
			resp.setMessage(Optional.ofNullable(e.getErrMsg()).orElse(e.getMessage()));
			resp.setRequestId(e.getRequestId());
		}
		return resp;
	}
}