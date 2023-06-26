package com.jqdi.smssender.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jqdi.smssender.springbootdemo.context.SpringContextUtil;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		// SpringApplication.run(Application.class, args);
		
		SpringApplication springApplication = new SpringApplication(Application.class);
		// 初始化ApplicationContext，保证在所有bean实例化前面
		springApplication.addInitializers(SpringContextUtil.newInstance());
		springApplication.run(args);
	}
}