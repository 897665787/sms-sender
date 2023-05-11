package com.jqdi.smssender.spring.boot.starter.processor;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * SQL处理器，将发送记录保存到数据库
 */
@Slf4j
public class SqlSendPostProcessor implements SendPostProcessor {
	private static final String INSERT_SQL = "INSERT INTO `bu_sms_record`(`channel`, `mobile`, `sign_name`, `template_code`, `template_param_json`, `content`, `result`, `message`, `request_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private JdbcTemplate jdbcTemplate;

	public SqlSendPostProcessor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterSend(String channel, String mobile, String signName, String templateCode, String templateParamJson,
			SendResponse sendResponse) {
		if (jdbcTemplate == null) {
			log.warn(
					"缺少jdbcTemplate bean,未保存数据,channel:{},mobile:{},signName:{},templateCode:{},templateParamJson:{},sendResponse:{}",
					channel, mobile, signName, templateCode, templateParamJson, sendResponse);
			return;
		}
		jdbcTemplate.update(INSERT_SQL, channel, mobile, signName, templateCode, templateParamJson, "1111", "22222",
				sendResponse.getMessage(), sendResponse.getRequestId());
	}
}
