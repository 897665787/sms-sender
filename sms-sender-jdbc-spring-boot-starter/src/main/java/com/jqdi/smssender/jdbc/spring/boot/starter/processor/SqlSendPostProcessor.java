package com.jqdi.smssender.jdbc.spring.boot.starter.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

import com.jqdi.smssender.core.SendPostProcessor;
import com.jqdi.smssender.core.SendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * SQL处理器，将发送记录保存到数据库
 */
@Slf4j
public class SqlSendPostProcessor implements SendPostProcessor {
	private static final String SELECT_SQL = "SELECT template_content FROM `sms_template` WHERE channel = ? and template_code = ?;";
	private static final String INSERT_SQL = "INSERT INTO `sms_record`(`channel`, `mobile`, `sign_name`, `template_code`, `template_param_json`, `content`, `result`, `message`, `request_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private JdbcTemplate jdbcTemplate;

	public SqlSendPostProcessor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterSend(String channel, String mobile, String signName, String templateCode,
			LinkedHashMap<String, String> templateParamMap, SendResponse sendResponse) {
		if (jdbcTemplate == null) {
			log.warn(
					"缺少jdbcTemplate bean,未保存数据,channel:{},mobile:{},signName:{},templateCode:{},templateParamJson:{},sendResponse:{}",
					channel, mobile, signName, templateCode, templateParamMap, sendResponse);
			return;
		}
		List<String> resultList = jdbcTemplate.queryForList(SELECT_SQL, String.class,
				new Object[] { channel, templateCode });
		String templateContent = resultList.isEmpty() ? null : resultList.iterator().next();

		String templateParamJson = mapToJsonStr(templateParamMap);
		String content = fillTemplateContent(templateContent, templateParamMap);
		boolean success = sendResponse.isSuccess();
		String message = sendResponse.getMessage();
		String requestId = sendResponse.getRequestId();
		jdbcTemplate.update(INSERT_SQL, channel, mobile, signName, templateCode, templateParamJson, content,
				String.valueOf(success), message, requestId);
	}

	/**
	 * 填充模板内容
	 * 
	 * @param templateContent
	 * @param map
	 * @return
	 */
	private String fillTemplateContent(String templateContent, LinkedHashMap<String, String> map) {
		if (templateContent == null) {
			return null;
		}
		Collection<String> values = map.values();
		String content = String.format(templateContent, values.toArray());
		return content;
	}

	/**
	 * map转JSON字符串（这里不想增加依赖的引入，所以以字符串拼接的方式实现）
	 * 
	 * @param map
	 * @return
	 */
	private String mapToJsonStr(LinkedHashMap<String, String> map) {
		String templateParamJson = "{";
		List<String> itemList = new ArrayList<>();
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (value != null && !value.equals("")) {
				String item = String.format("\"%s\":\"%s\"", key, value);
				itemList.add(item);
			}
		}
		templateParamJson += itemList.stream().collect(Collectors.joining(","));
		templateParamJson += "}";
		return templateParamJson;
	}
}
