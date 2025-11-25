package com.smartlogis.productservice.domain.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.smartlogis.productservice.application.service.ProductService;
import com.smartlogis.productservice.infrastructure.config.RabbitMQConfig;
import com.smartlogis.productservice.interfaces.dto.event.CompanyOrderCreatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.OrderCanceledEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

	private final ProductService productService;

	@RabbitListener(queues = RabbitMQConfig.COMPANY_ORDER_CREATED_QUEUE)
	public void handleOrderCreated(CompanyOrderCreatedEvent event){
		log.info("[주문 생성] 이벤트 받음 {}", event);

		//재고 관리
		productService.applyOrderStock(event);

		//출발 허브 id 추가 후 이벤트 발행
		productService.handleOrderCreatedEvent(event);
	}

	@RabbitListener(queues = RabbitMQConfig.ORDER_CANCELED_QUEUE)
	public void handleOrderCanceled(OrderCanceledEvent event){
		log.info("[주문 취소] 이벤트 받음 {}", event);
		productService.revertOrderStock(event);
	}
}
