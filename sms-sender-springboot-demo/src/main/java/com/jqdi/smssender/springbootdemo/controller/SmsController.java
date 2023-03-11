package com.jqdi.smssender.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.smssender.core.SendResponse;
import com.jqdi.smssender.core.SmsSender;

@RestController
@RequestMapping("/sms")
public class SmsController {

	@Autowired
	private SmsSender smsSender;

	@GetMapping("/send")
	public String send(String mobile, String templateCode, String templateParamJson) {
		SendResponse sendResponse = smsSender.send(mobile, templateCode, templateParamJson);
		System.out.println("sendResponse:" + sendResponse);
		return "success";
	}
}
