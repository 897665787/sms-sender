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
	public String send(String mobile, String templateCode, String code) {
		LinkedHashMap<String, String> templateParamMap = new LinkedHashMap<>();
		templateParamMap.put("code", code);
		SendResponse sendResponse = smsSender.send(mobile, templateCode, templateParamMap);
		log.info("sendResponse:{}", sendResponse);
		return "success";
	}
}
