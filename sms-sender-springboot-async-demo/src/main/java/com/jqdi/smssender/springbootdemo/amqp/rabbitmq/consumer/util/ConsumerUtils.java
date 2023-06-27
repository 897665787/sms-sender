package com.jqdi.smssender.springbootdemo.amqp.rabbitmq.consumer.util;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections4.MapUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import com.jqdi.smssender.springbootdemo.amqp.core.BaseStrategy;
import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.constants.HeaderConstants;
import com.jqdi.smssender.springbootdemo.context.SpringContextUtil;
import com.jqdi.smssender.springbootdemo.util.JsonUtil;
import com.jqdi.smssender.springbootdemo.util.MdcUtil;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerUtils {
	private ConsumerUtils() {
	}

	/**
	 * 使用Strategy处理逻辑
	 * 
	 * @param jsonStrMsg
	 * @param channel
	 * @param message
	 */
	public static <E> void handleByStrategy(String jsonStrMsg, Channel channel, Message message) {
		handleByStrategy(jsonStrMsg, channel, message, true);
	}

	/**
	 * 使用Strategy处理逻辑
	 * 
	 * @param jsonStrMsg
	 * @param channel
	 * @param message
	 * @param unAckIfException
	 *            如果抛出java.lang.Exception异常则不ack
	 */
	public static <E> void handleByStrategy(String jsonStrMsg, Channel channel, Message message,
			boolean unAckIfException) {
		@SuppressWarnings("unchecked")
		Consumer<Object> consumer = entity -> {
			MessageProperties messageProperties = message.getMessageProperties();
			Map<String, Object> headers = messageProperties.getHeaders();
			String strategyName = MapUtils.getString(headers, HeaderConstants.HEADER_STRATEGY_NAME);
			BaseStrategy<E> strategy = SpringContextUtil.getBean(strategyName, BaseStrategy.class);
			strategy.doStrategy((E) entity);
		};
		handle(jsonStrMsg, channel, message, consumer, unAckIfException);
	}

	/**
	 * 使用自定义Consumer函数处理逻辑
	 * 
	 * @param jsonStrMsg
	 * @param channel
	 * @param message
	 * @param consumer
	 */
	public static <E> void handleByConsumer(String jsonStrMsg, Channel channel, Message message, Consumer<E> consumer) {
		handleByConsumer(jsonStrMsg, channel, message, consumer, false);
	}

	/**
	 * 使用自定义Consumer函数处理逻辑
	 * 
	 * @param jsonStrMsg
	 * @param channel
	 * @param message
	 * @param consumer
	 * @param unAckIfException
	 *            如果抛出java.lang.Exception异常则不ack
	 */
	public static <E> void handleByConsumer(String jsonStrMsg, Channel channel, Message message, Consumer<E> consumer,
			boolean unAckIfException) {
		@SuppressWarnings("unchecked")
		Consumer<Object> consumer2 = entity -> {
			consumer.accept((E) entity);
		};
		handle(jsonStrMsg, channel, message, consumer2, unAckIfException);
	}

	private static void handle(String jsonStrMsg, Channel channel, Message message, Consumer<Object> consumer,
			boolean unAckIfException) {
		try {
			if (jsonStrMsg == null) {
				log.info("jsonStrMsg is null");
				basicAck(channel, message);
				return;
			}
			long start = System.currentTimeMillis();
			try {
				MessageProperties messageProperties = message.getMessageProperties();
				MdcUtil.put(messageProperties.getMessageId());
				log.info("jsonStrMsg:{}", jsonStrMsg);
				Map<String, Object> headers = messageProperties.getHeaders();
				String paramsClassName = MapUtils.getString(headers, HeaderConstants.HEADER_PARAMS_CLASS);
				Class<?> paramsClass = null;
				try {
					paramsClass = Class.forName(paramsClassName);
				} catch (ClassNotFoundException e) {
					log.error("ClassNotFoundException", e);
				}
				Object entity = JsonUtil.toEntity(jsonStrMsg, paramsClass);
				consumer.accept(entity);
			} catch (RuntimeException e) {
				// 业务异常一般是校验不通过，可以当做成功处理
				log.warn("RuntimeException message:{}", e.getMessage());
			} catch (Exception e) {
				log.error("accept error", e);
				if (unAckIfException) {
					return;
				}
			} finally {
				log.info("耗时:{}ms", System.currentTimeMillis() - start);
			}
			basicAck(channel, message);
		} finally {
			MdcUtil.remove();
		}
	}

	private static void basicAck(Channel channel, Message message) {
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			log.error("basicAck error", e);
		}
	}
}
