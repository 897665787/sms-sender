package com.jqdi.smssender.core.leshua;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.leshua.dto.SendSmsResponse;
import com.jqdi.smssender.core.util.HttpUtil;
import com.jqdi.smssender.core.util.XmlUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 乐刷短信中心客户端
 * 
 * <pre>
 * 官方文档：
 * https://yapi.lepass.cn/project/1816/interface/api/336333
 * 或
 * https://images.lssqpay.com/image/2023/06-15/481a2e2c59e74568bfa77ae3c5cf7505.png
 * </pre>
 */
@Slf4j
public class SmsClient {
	private String url = null;
	private String key = null;
	private String source = null;

	public SmsClient(String url, String key, String source) {
		this.url = url;
		this.key = key;
		this.source = source;
	}

	public SendResponse send(String mobile, String title, String content) {
		SendResponse resp = new SendResponse();

		Map<String, String> paramMap = new TreeMap<>();// 参数字典排序
		paramMap.put("content", content);
		paramMap.put("mobile", mobile);
		paramMap.put("source", source);
		paramMap.put("title", title);

		String type = "normal";// normal:通知类、验证码,ad:营销
		if (content.contains("回T退订") || content.contains("退订回T")) {
			// 可退订的短信为营销类短信
			type = "ad";
		}
		paramMap.put("type", type);

		String urlParamStr = paramMap.entrySet().stream().filter(v -> v.getValue() != null)
				.map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));

		// 计算sign
		String signStr = urlParamStr + "&key=" + key;
		String sign = DigestUtils.sha1Hex(signStr);
		log.info("sha1Hex signStr:{},sign:{}", signStr, sign);

		// content字段可能包含特殊字符，经过
		String urlParamStr4Urlencode = paramMap.entrySet().stream().filter(v -> v.getValue() != null)
				.map(entry -> entry.getKey() + "=" + urlencode(entry.getValue())).collect(Collectors.joining("&"));
		
		String reqUrl = url + "?" + urlParamStr4Urlencode + "&sign=" + sign;
		try {
			long start = System.currentTimeMillis();
			String xmlResponse = HttpUtil.get(reqUrl);
			SendSmsResponse response = XmlUtil.toEntity(xmlResponse, SendSmsResponse.class);
			log.info("reqUrl:{},response:{},cost:{}", reqUrl, response, System.currentTimeMillis() - start);

			resp.setRequestId(response.getSmsId());
			Integer result = response.getResult();
			if (result == 0) {
				resp.setSuccess(true);
			} else {
				resp.setSuccess(false);
				resp.setMessage(response.getErrorMsg());
			}
		} catch (Exception e) {
			log.error("Exception error", e);
			resp.setSuccess(false);
			resp.setMessage(e.getMessage());
			resp.setRequestId("0");
		}
		return resp;
	}

	private static String urlencode(String url) {
		try {
			return java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException error", e);
		}
		return url;
	}
}