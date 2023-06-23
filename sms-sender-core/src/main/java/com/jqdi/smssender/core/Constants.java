package com.jqdi.smssender.core;

public interface Constants {
	public interface Channel {
		// 测试渠道
		String LOG = "log";
		// 阿里云短信
		String ALI = "ali";
		// 腾讯云短信
		String TENCENT = "tencent";
		// 百度云短信
		String BAIDU = "baidu";
		// 京东云短信
		String JINGDONG = "jingdong";
		// 七牛云短信
		String QINIU = "qiniu";
	}
}