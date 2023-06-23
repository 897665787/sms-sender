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
import com.jqdi.smssender.core.baidu.BaiduSmsSender;
import com.jqdi.smssender.core.jingdong.JingdongSmsSender;
import com.jqdi.smssender.core.log.LogSmsSender;
import com.jqdi.smssender.core.qiniu.QiniuSmsSender;
import com.jqdi.smssender.core.tencent.TencentSmsSender;
import com.jqdi.smssender.spring.boot.starter.properties.AliSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.BaiduSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.JingdongSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.LogSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.QiniuSmsProperties;
import com.jqdi.smssender.spring.boot.starter.properties.TentcentSmsProperties;

@Configuration
@ConditionalOnProperty(prefix = "smssender", name = "active")
@ConditionalOnMissingBean(SmsSender.class)
@EnableConfigurationProperties({ LogSmsProperties.class, AliSmsProperties.class, TentcentSmsProperties.class,
		BaiduSmsProperties.class, JingdongSmsProperties.class, QiniuSmsProperties.class })
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

	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "baidu")
	SmsSender baiduSmsSender(BaiduSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String accessKey = properties.getAccessKey();
		String secretKey = properties.getSecretKey();
		String signName = properties.getSignName();

		SmsSender smsSender = new BaiduSmsSender(accessKey, secretKey, signName, sendPostProcessor);
		return smsSender;
	}

	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "jingdong")
	SmsSender jingdongSmsSender(JingdongSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String regionId = properties.getRegionId();
		String accessKey = properties.getAccessKey();
		String secretKey = properties.getSecretKey();
		String signName = properties.getSignName();

		SmsSender smsSender = new JingdongSmsSender(regionId, accessKey, secretKey, signName, sendPostProcessor);
		return smsSender;
	}

	@Bean
	@ConditionalOnProperty(prefix = "smssender", name = "active", havingValue = "qiniu")
	SmsSender qiniuSmsSender(QiniuSmsProperties properties, @Nullable SendPostProcessor sendPostProcessor) {
		String accessKey = properties.getAccessKey();
		String secretKey = properties.getSecretKey();
		String signName = properties.getSignName();

		SmsSender smsSender = new QiniuSmsSender(accessKey, secretKey, signName, sendPostProcessor);
		return smsSender;
	}
}
