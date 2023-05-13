package com.jqdi.smssender.jdbc.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "smssender.ali")
public class AliSmsProperties {
	private String regionId;
	private String accessKey;
	private String secretKey;
	private String signName;
}
