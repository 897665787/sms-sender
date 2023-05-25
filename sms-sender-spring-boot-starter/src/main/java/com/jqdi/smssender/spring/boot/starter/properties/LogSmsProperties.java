package com.jqdi.smssender.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "smssender.log")
public class LogSmsProperties {
	private String signName;
}
