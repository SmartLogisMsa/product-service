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

	//업체 이벤트 받기
	public static final String COMPANY_ORDER_CREATED_QUEUE = "smartlogis.company.order.created.queue";
	public static final String COMPANY_ORDER_CREATED_EXCHANGE = "smartlogis.company.exchange";
	public static final String COMPANY_ORDER_CREATED_ROUTING_KEY = "smartlogis.company.order.created";

	//주문 취소
	public static final String ORDER_CANCELED_QUEUE = "smartlogis.order.canceled.queue";
	public static final String ORDER_CANCELED_EXCHANGE = "smartlogis.order.exchange";
	public static final String ORDER_CANCELED_ROUTING_KEY = "smartlogis.order.canceled";

	//허브로 가는 이벤트
	public static final String PRODUCT_ORDER_CREATED_QUEUE = "smartlogis.product.order.created.queue";
	public static final String PRODUCT_ORDER_CREATED_EXCHANGE = "smartlogis.product.exchange";
	public static final String PRODUCT_ORDER_CREATED_ROUTING_KEY = "smartlogis.product.order.created";

	//업체 비활성화 이벤트
	public static final String COMPANY_INACTIVED_QUEUE = "smartlogis.company.inactive.queue";
	public static final String COMPANY_INACTIVATED_EXCHANGE = "smartlogis.company.inactivated.exchange";
	public static final String COMPANY_INACTIVATED_ROUTING_KEY = "smartlogis.company.inactivated";

	//업체 상태 변경 이벤트
	public static final String COMPANY_STATUS_QUEUE = "smartlogis.company.status.queue";
	public static final String COMPANY_STATUS_CHANGED_EXCHANGE = "smartlogis.company.status.changed.exchange";
	public static final String COMPANY_STATUS_CHANGED_ROUTING_KEY = "smartlogis.company.status.changed";

	//업체 허브 변경 이벤트
	public static final String COMPANY_HUB_CHANGED_QUEUE = "smartlogis.company.hubId.changed.queue";
	public static final String COMPANY_HUB_CHANGED_EXCHANGE = "smartlogis.company.hubId.changed.exchange";
	public static final String COMPANY_HUB_CHANGED_ROUTING_KEY = "smartlogis.company.hubId.changed";


	@Bean
	public Queue orderCanceledQueue() {
		return new Queue(ORDER_CANCELED_QUEUE, true);
	}

	@Bean
	public Queue companyOrderCreatedQueue() {
		return new Queue(COMPANY_ORDER_CREATED_QUEUE, true);
	}

	@Bean
	public Queue productOrderCreatedQueue() {
		return new Queue(PRODUCT_ORDER_CREATED_QUEUE, true);
	}

	@Bean
	public Queue companyInactiveQueue() {return new Queue(COMPANY_INACTIVED_QUEUE, true);}

	@Bean
	public Queue companyStatusChangedQueue() {return new Queue(COMPANY_STATUS_QUEUE, true);}

	@Bean
	public Queue companyHubChangedQueue() {
		return new Queue(COMPANY_HUB_CHANGED_QUEUE, true);
	}

	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(ORDER_CANCELED_EXCHANGE, true,  false);
	}

	@Bean
	public TopicExchange companyExchange() {
		return new TopicExchange(COMPANY_ORDER_CREATED_EXCHANGE, true, false);
	}

	@Bean
	public TopicExchange productExchange() {
		return new TopicExchange(PRODUCT_ORDER_CREATED_EXCHANGE, true, false);
	}

	@Bean
	public TopicExchange companyInactivatedExchange() {
		return new TopicExchange(COMPANY_INACTIVATED_EXCHANGE, true, false);
	}

	@Bean
	public TopicExchange companyStatusChangedExchange() {
		return new TopicExchange(COMPANY_STATUS_CHANGED_EXCHANGE, true, false);
	}

	@Bean
	public TopicExchange companyHubChangedExchange() {
		return new TopicExchange(COMPANY_HUB_CHANGED_EXCHANGE, true, false);
	}

	@Bean
	public Binding orderCanceledBinding(Queue orderCanceledQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(orderCanceledQueue)
			.to(orderExchange)
			.with(ORDER_CANCELED_ROUTING_KEY);
	}

	@Bean
	public Binding companyOrderCreatedBinding(Queue companyOrderCreatedQueue, TopicExchange companyExchange) {
		return BindingBuilder.bind(companyOrderCreatedQueue)
			.to(companyExchange)
			.with(COMPANY_ORDER_CREATED_ROUTING_KEY);
	}

	@Bean
	public Binding productOrderCreatedBinding(Queue productOrderCreatedQueue, TopicExchange productExchange) {
		return BindingBuilder.bind(productOrderCreatedQueue)
			.to(productExchange)
			.with(PRODUCT_ORDER_CREATED_ROUTING_KEY);
	}

	@Bean
	public Binding companyInactiveBinding(Queue companyInactiveQueue, TopicExchange companyInactivatedExchange) {
		return BindingBuilder.bind(companyInactiveQueue)
			.to(companyInactivatedExchange)
			.with(COMPANY_INACTIVATED_ROUTING_KEY);
	}

	@Bean
	public Binding companyStatusChangedBinding(Queue companyStatusChangedQueue, TopicExchange companyExchange) {
		return BindingBuilder.bind(companyStatusChangedQueue)
			.to(companyExchange)
			.with(COMPANY_STATUS_CHANGED_ROUTING_KEY);
	}

	@Bean
	public Binding companyHubChangedBinding(Queue companyHubChangedQueue, TopicExchange companyHubChangedExchange) {
		return BindingBuilder.bind(companyHubChangedExchange)
			.to(companyHubChangedExchange)
			.with(COMPANY_HUB_CHANGED_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
