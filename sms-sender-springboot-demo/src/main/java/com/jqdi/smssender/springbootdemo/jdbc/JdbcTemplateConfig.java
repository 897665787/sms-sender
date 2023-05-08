package com.jqdi.smssender.springbootdemo.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcTemplateConfig {

	@Bean
	public DataSource dataSource() {
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/template?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&tinyInt1isBit=false";
		String username = "root";
		String password = "12345678";

		DataSource dataSource = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username)
				.password(password).build();
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
	}
}
