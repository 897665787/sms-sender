package com.jqdi.smssender.core.tencent;

import com.jqdi.smssender.core.SendResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 腾讯云短信客户端
 * 
 * <pre>
 * 官方文档：https://cloud.tencent.com/document/product/382/55981
 * </pre>
 */
@Slf4j
public class SmsClient {
	private static final String SUCCESS_CODE = "OK";
	
	private com.tencentcloudapi.sms.v20210111.SmsClient client = null;
	private String appId = null;

	public SmsClient(String appId, String regionId, String accessKey, String secretKey) {
		Credential cred = new Credential(accessKey, secretKey);

		this.client = new com.tencentcloudapi.sms.v20210111.SmsClient(cred, regionId);
		this.appId = appId;
	}

	public SendResponse send(String mobile, String signName, String templateCode, String[] templateParamArray) {
		SendSmsRequest request = new SendSmsRequest();
		request.setSmsSdkAppId(appId);

		request.setPhoneNumberSet(new String[] { mobile });
		request.setSignName(signName);

		request.setTemplateId(templateCode);
		request.setTemplateParamSet(templateParamArray);

		// 可选:用户的 session 内容，可以携带用户侧 ID 等上下文信息，server 会原样返回
		// request.setSessionContext(SessionContext);

		SendResponse resp = new SendResponse();
		try {
			SendSmsResponse response = client.SendSms(request);
			log.info("response:{}", response);

			resp.setRequestId(response.getRequestId());
			SendStatus[] sendStatusSet = response.getSendStatusSet();
			if (sendStatusSet.length < 1) {
				resp.setSuccess(false);
				resp.setMessage("状态不匹配");
			}
			SendStatus sendStatus = sendStatusSet[0];
			String code = sendStatus.getCode();
			if (SUCCESS_CODE.equals(code)) {
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setMessage(sendStatus.getMessage());
			}
		} catch (TencentCloudSDKException e) {
			log.error("TencentCloudSDKException error", e);
			resp.setSuccess(false);
			resp.setMessage(e.getMessage());
			resp.setRequestId(e.getRequestId());
		}
		return resp;
	}

}