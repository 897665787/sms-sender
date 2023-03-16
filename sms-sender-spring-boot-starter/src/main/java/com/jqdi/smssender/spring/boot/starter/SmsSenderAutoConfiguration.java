package com.jqdi.smssender.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SmsSender;
import com.jqdi.smssender.core.ali.AliSmsSender;
import com.jqdi.smssender.core.log.LogSmsSender;
import com.jqdi.smssender.core.tencent.TencentSmsSender;
import com.jqdi.smssender.spring.boot.starter.properties.AliSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.LogSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.TentcentSmsProperties;

@Configuration
@ConditionalOnProperty(prefix = "smssender", name = "active")
@ConditionalOnMissingBean(SmsSender.class)
@EnableConfigurationProperties({ LogSmsProperties.class, AliSmsProperties.class, TentcentSmsProperties.class })
public class SmsSenderAutoConfiguration {
	// for test env
	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "log")
	SmsSender logSmsSender(LogSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String signName = properties.getSignName();

		SmsSender smsSender = new LogSmsSender(signName, sendPostProcessor);
		return smsSender;
	}
	// for test env
	
	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "ali")
	SmsSender aliSmsSender(AliSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String regionId = properties.getRegionId();
		String accessKey = properties.getAccessKey();
		String secretKey = properties.getSecretKey();
		String signName = properties.getSignName();

		SmsSender smsSender = new AliSmsSender(regionId, accessKey, secretKey, signName, sendPostProcessor);
		return smsSender;
	}

	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "tencent")
	SmsSender tencentSmsSender(TentcentSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String appId = properties.getAppId();
		String regionId = properties.getRegionId();
		String accessKey = properties.getAccessKey();
		String secretKey = properties.getSecretKey();
		String signName = properties.getSignName();

		SmsSender smsSender = new TencentSmsSender(appId, regionId, accessKey, secretKey, signName, sendPostProcessor);
		return smsSender;
	}
}
