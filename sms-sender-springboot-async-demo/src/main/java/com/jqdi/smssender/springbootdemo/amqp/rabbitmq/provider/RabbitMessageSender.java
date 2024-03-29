package com.jqdi.smssender.springbootdemo.amqp.rabbitmq.provider;

import java.util.UUID;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.jqdi.smssender.springbootdemo.amqp.core.MessageSender;
import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.autoconfigure.RabbitAutoConfiguration.RabbitCondition;
import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.constants.HeaderConstants;
import com.jqdi.smssender.springbootdemo.util.JsonUtil;
import com.jqdi.smssender.springbootdemo.util.MdcUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component
@Conditional(RabbitCondition.class)
public class RabbitMessageSender implements MessageSender {

	@Autowired(required = false)
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendNormalMessage(String strategyName, Object toJson, String exchange, String routingKey) {
		sendMessage(strategyName, toJson, exchange, routingKey, null);
	}

	@Override
	public void sendFanoutMessage(Object toJson, String exchange) {
		sendMessage(null, toJson, exchange, null, null);
	}

	@Override
	public void sendDelayMessage(String strategyName, Object toJson, String exchange, String routingKey,
			Integer delaySeconds) {
		sendMessage(strategyName, toJson, exchange, routingKey, delaySeconds);
	}

	/**
	 * 发送消息
	 * 
	 * @param strategyName
	 * @param toJson
	 * @param exchange
	 * @param routingKey
	 * @param delaySeconds
	 */
	private void sendMessage(String strategyName, Object toJson, String exchange, String routingKey,
			Integer delaySeconds) {
		if (rabbitTemplate == null) {
			log.warn("rabbitTemplate not init");
			return;
		}

		String correlationId = UUID.randomUUID().toString();
		String paramsStr = JsonUtil.toJsonString(toJson);
		rabbitTemplate.convertAndSend(exchange, routingKey, paramsStr, messageToSend -> {
			MessageProperties messageProperties = messageToSend.getMessageProperties();
			if (strategyName != null) {
				messageProperties.setHeader(HeaderConstants.HEADER_STRATEGY_NAME, strategyName);
			}
			messageProperties.setHeader(HeaderConstants.HEADER_PARAMS_CLASS, toJson.getClass().getName());
			messageProperties.setMessageId(MdcUtil.get());
			if (delaySeconds != null) {
				Integer delayMillis = delaySeconds * 1000;// 设置延时毫秒值
				messageProperties.setDelay(delayMillis);// x-delayed延时
				messageProperties.setExpiration(String.valueOf(delayMillis));// x-dead-letter延时
			}
			return messageToSend;
		}, new CorrelationData(correlationId));
		log.info("convertAndSend,correlationId:{},strategyName:{},toJson:{},exchange:{},routingKey:{},delaySeconds:{}",
				correlationId, strategyName, paramsStr, exchange, routingKey, delaySeconds);
	}
}