package com.jqdi.smssender.jdbc.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.jdbc.spring.boot.starter.processor.SqlSendPostProcessor;
import com.jqdi.smssender.jdbc.spring.boot.starter.tableinit.MysqlTableInit;

@Configuration
@ConditionalOnClass(JdbcTemplate.class)
@ConditionalOnProperty(prefix = "smssender", name = "active")
public class SmsSenderJdbcAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	MysqlTableInit mysqlTableInit(@Nullable JdbcTemplate jdbcTemplate) {
		MysqlTableInit mysqlTableInit = new MysqlTableInit(jdbcTemplate);
		return mysqlTableInit;
	}

	@Bean
	@ConditionalOnMissingBean
	SendPostProcessor sqlSendPostProcessor(@Nullable JdbcTemplate jdbcTemplate) {
		SendPostProcessor sendPostProcessor = new SqlSendPostProcessor(jdbcTemplate);
		return sendPostProcessor;
	}
}
