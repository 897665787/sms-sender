package com.jqdi.smssender.springbootdemo.sms.dto;

import java.util.Map;

import lombok.Data;

@Data
public class MobileTemplateParam {
	private String mobile;
	private Map<String, String> templateParamMap;
}
