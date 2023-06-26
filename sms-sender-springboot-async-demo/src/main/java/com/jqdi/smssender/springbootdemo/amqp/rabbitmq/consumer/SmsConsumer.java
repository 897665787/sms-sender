package com.jqdi.smssender.springbootdemo.amqp.rabbitmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.Constants;
import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.autoconfigure.RabbitAutoConfiguration.RabbitCondition;
import com.jqdi.smssender.springbootdemo.amqp.rabbitmq.consumer.util.ConsumerUtils;
import com.rabbitmq.client.Channel;

@Component
@Conditional(RabbitCondition.class)
public class SmsConsumer {

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = Constants.QUEUE.SEND_SMS.NAME), exchange = @Exchange(value = Constants.EXCHANGE.DIRECT), key = Constants.QUEUE.SEND_SMS.ROUTING_KEY))
	public void handle(String jsonStrMsg, Channel channel, Message message) {
		ConsumerUtils.handleByStrategy(jsonStrMsg, channel, message);
	}
}