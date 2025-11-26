package com.smartlogis.productservice.infrastructure.event.publisher;

import static com.smartlogis.productservice.infrastructure.config.RabbitMQConfig.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.smartlogis.productservice.interfaces.dto.event.LowStockEvent;
import com.smartlogis.productservice.interfaces.dto.event.ProductOrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publishToHub(ProductOrderCreatedEvent event) {
		rabbitTemplate.convertAndSend(
			PRODUCT_ORDER_CREATED_EXCHANGE,
			PRODUCT_ORDER_CREATED_ROUTING_KEY,
			event
		);
	}

	public void publishLowStock(LowStockEvent event) {
		rabbitTemplate.convertAndSend(
			PRODUCT_LOW_STOCK_EXCHANGE,
			PRODUCT_LOW_STOCK_ROUTING_KEY,
			event
		);
	}
}
