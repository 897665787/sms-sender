package com.jqdi.smssender.springbootdemo.database.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MapperScan 配置
 */
@Configuration
@MapperScan(basePackages = "com.jqdi.smssender.springbootdemo.mapper")
public class MapperScanAutoConfiguration {
}