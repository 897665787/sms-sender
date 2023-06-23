package com.jqdi.smssender.core.leshua.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <pre>
 * 失败情况：
 * <root><error_msg>phone error</error_msg><result>56</result><sms_id>0</sms_id></root>
 * 
 * 成功情况：
 * <root><error_msg>send success</error_msg><result>0</result><sms_id>1686733036675629387</sms_id></root>
 * </pre>
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class SendSmsResponse {

	@XmlElement(name = "error_msg")
	String errorMsg;

	@XmlElement(name = "result")
	Integer result;

	@XmlElement(name = "sms_id")
	String smsId;
}