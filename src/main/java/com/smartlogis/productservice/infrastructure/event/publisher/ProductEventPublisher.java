package com.smartlogis.productservice.infrastructure.event.publisher;

import static com.smartlogis.productservice.infrastructure.config.RabbitMQConfig.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.smartlogis.productservice.interfaces.dto.event.HubOrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publishHubOrderCreated(HubOrderCreatedEvent event) {
		rabbitTemplate.convertAndSend(
			HUB_ORDER_CREATED_EXCHANGE,
			HUB_ORDER_CREATED_ROUTING_KEY,
			event
		);
	}
}
