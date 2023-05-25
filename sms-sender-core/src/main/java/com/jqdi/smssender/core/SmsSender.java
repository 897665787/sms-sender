package com.jqdi.smssender.core;

import java.util.LinkedHashMap;

public interface SmsSender {
	SendResponse send(String mobile, String templateCode, LinkedHashMap<String, String> templateParamMap);
}