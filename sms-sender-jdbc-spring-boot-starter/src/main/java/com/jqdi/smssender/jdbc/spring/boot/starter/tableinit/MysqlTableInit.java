package com.jqdi.smssender.jdbc.spring.boot.starter.tableinit;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * mysql表初始化
 */
@Slf4j
public class MysqlTableInit implements InitializingBean {

	private JdbcTemplate jdbcTemplate;

	public MysqlTableInit(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jdbcTemplate == null) {
			log.warn("缺少jdbcTemplate bean,未执行初始化脚本");
			return;
		}
		List<String> sqlFileList = new ArrayList<>();
		sqlFileList.add("sms_record.sql");

		for (String sqlFile : sqlFileList) {
			String sql = initSql(sqlFile);
			jdbcTemplate.execute(sql);
			log.info("执行初始化脚本:{}", sql);
		}
	}

	private static String initSql(String fileName) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:sql/mysql/" + fileName);

		try (InputStream inputStream = resource.getInputStream()) {
			byte[] bytes = new byte[0];
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			String str = new String(bytes, Charset.forName("UTF-8"));
			return str;
		} catch (IOException e) {
			log.error("IOException", e);
		}
		return null;
	}
}
