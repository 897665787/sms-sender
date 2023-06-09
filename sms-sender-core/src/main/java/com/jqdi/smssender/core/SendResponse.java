package com.jqdi.smssender.core;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendResponse {
	boolean success;
	String message;
	String requestId;
}