package com.jqdi.smssender.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "smssender.tencent")
public class TentcentSmsProperties {
	private String appId;
	private String regionId;
	private String accessKey;
	private String secretKey;
	private String signName;
}
