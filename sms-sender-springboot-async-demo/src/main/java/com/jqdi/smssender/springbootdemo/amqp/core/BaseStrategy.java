package com.jqdi.smssender.springbootdemo.amqp.core;

/**
 * MQ消费策略
 */
public interface BaseStrategy<E> {
	
	void doStrategy(E e);
}
