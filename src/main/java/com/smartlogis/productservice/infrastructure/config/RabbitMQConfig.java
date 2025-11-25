package com.smartlogis.productservice.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	//주문 생성
	public static final String ORDER_CREATED_QUEUE = "smartlogis.order.created.queue";
	public static final String ORDER_CREATED_EXCHANGE = "smartlogis.order.exchange";
	public static final String ORDER_CREATED_ROUTING_KEY = "smartlogis.order.created";

	//주문 취소
	public static final String ORDER_CANCELED_QUEUE = "smartlogis.order.canceled.queue";
	public static final String ORDER_CANCELED_EXCHANGE = "smartlogis.order.exchange";
	public static final String ORDER_CANCELED_ROUTING_KEY = "smartlogis.order.canceled";

	//상품별 허브 이벤트
	public static final String HUB_ORDER_CREATED_QUEUE = "smartlogis.hub.order.created.queue";
	public static final String HUB_ORDER_CREATED_EXCHANGE = "smartlogis.hub.exchange";
	public static final String HUB_ORDER_CREATED_ROUTING_KEY = "smartlogis.hub.order.created";


	@Bean
	public Queue orderCreatedQueue() {
		return new Queue(ORDER_CREATED_QUEUE, true);
	}

	@Bean
	public Queue orderCanceledQueue() {
		return new Queue(ORDER_CANCELED_QUEUE, true);
	}

	@Bean
	public Queue hubOrderCreatedQueue() {
		return new Queue(HUB_ORDER_CREATED_QUEUE, true);
	}

	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(ORDER_CREATED_EXCHANGE, true,  false);
	}

	@Bean
	public TopicExchange hubExchange() {
		return new TopicExchange(HUB_ORDER_CREATED_EXCHANGE, true, false);
	}

	@Bean
	public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(orderCreatedQueue)
			.to(orderExchange)
			.with(ORDER_CREATED_ROUTING_KEY);
	}

	@Bean
	public Binding orderCanceledBinding(Queue orderCanceledQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(orderCanceledQueue)
			.to(orderExchange)
			.with(ORDER_CANCELED_ROUTING_KEY);
	}

	@Bean
	public Binding hubOrderCreatedBinding(Queue hubOrderCreatedQueue, TopicExchange hubExchange) {
		return BindingBuilder.bind(hubOrderCreatedQueue)
			.to(hubExchange)
			.with(HUB_ORDER_CREATED_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
