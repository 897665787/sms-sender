package com.jqdi.smssender.spring.boot.starter.processor;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;

/**
 * 这个是demo，实际应用中可以将发送记录保存到数据库
 */
public class SqlSendPostProcessor implements SendPostProcessor {
	String sql = "INSERT INTO `bu_user_info` (`nickname`, `avator`) VALUES (?, ?);";

	private JdbcTemplate jdbcTemplate;

	public SqlSendPostProcessor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterSend(String channel, String mobile, String signName, String templateCode, String templateParamJson,
			SendResponse sendResponse) {
		jdbcTemplate.update(sql, channel, mobile);
	}
}
