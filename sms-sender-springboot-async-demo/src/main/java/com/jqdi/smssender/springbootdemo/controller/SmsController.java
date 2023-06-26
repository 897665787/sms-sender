package com.jqdi.smssender.springbootdemo.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.smssender.springbootdemo.enums.SmsEnum;
import com.jqdi.smssender.springbootdemo.sms.AsyncSmsSender;

@RestController
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	// private SmsSender smsSender;
	private AsyncSmsSender smsSender;

	@GetMapping("/send")
	public String send(String mobile, String code) {
		LinkedHashMap<String, String> templateParamMap = new LinkedHashMap<>();
		templateParamMap.put("code", code);

		LocalDateTime planSendTime = LocalDateTime.now();
		LocalDateTime overTime = planSendTime.plusMinutes(5);
		smsSender.send(mobile, templateParamMap, SmsEnum.Type.VERIFYCODE, planSendTime, overTime);
		return "success";
	}
	
	@GetMapping("/senddelay")
	public String senddelay(String mobile, String code) {
		LinkedHashMap<String, String> templateParamMap = new LinkedHashMap<>();
		templateParamMap.put("code", code);

		LocalDateTime now = LocalDateTime.now();
		
		LocalDateTime planSendTime = now.plusSeconds(10);
		LocalDateTime overTime = planSendTime.plusMinutes(5);
		smsSender.send(mobile, templateParamMap, SmsEnum.Type.VERIFYCODE, planSendTime, overTime);
		return "success";
	}
}
