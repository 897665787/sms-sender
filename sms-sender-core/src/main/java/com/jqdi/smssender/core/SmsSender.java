package com.jqdi.smssender.core;

public interface SmsSender {
	SendResponse send(String mobile, String templateCode, String templateParamJson);
}