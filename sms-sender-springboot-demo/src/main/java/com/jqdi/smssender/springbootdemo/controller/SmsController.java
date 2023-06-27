package com.jqdi.smssender.springbootdemo.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	private SmsSender smsSender;

	@GetMapping("/send")
	public String send(String mobile, String code) {
		LinkedHashMap<String, String> templateParamMap = new LinkedHashMap<>();
		templateParamMap.put("code", code);
		String templateCode = "SMS_123456";
		String templateContent = "您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！";
		String content = templateContent.replace("${code}", code);
		smsSender.send(mobile, templateCode, templateParamMap, content);
		SendResponse sendResponse = smsSender.send(mobile, templateCode, templateParamMap, content);
		log.info("sendResponse:{}", sendResponse);
		return "success";
	}
}
