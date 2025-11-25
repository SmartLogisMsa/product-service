package com.smartlogis.productservice.domain.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.smartlogis.productservice.application.service.ProductService;
import com.smartlogis.productservice.infrastructure.config.RabbitMQConfig;
import com.smartlogis.productservice.interfaces.dto.event.CompanyHubChangeEvent;
import com.smartlogis.productservice.interfaces.dto.event.CompanyInactivatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.CompanyOrderCreatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.CompanyStatusChangedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyEventListener {

	private final ProductService productService;

	@RabbitListener(queues = RabbitMQConfig.COMPANY_ORDER_CREATED_QUEUE)
	public void handleOrderCreated(CompanyOrderCreatedEvent event){
		log.info("[주문 생성] 이벤트 받음 {}", event);

		//재고 관리
		productService.applyOrderStock(event);

		//출발 허브 id 추가 후 이벤트 발행
		productService.handleOrderCreatedEvent(event);

		log.info("허브로 이벤트 발행 성공");
	}

	@RabbitListener(queues = RabbitMQConfig.COMPANY_INACTIVED_QUEUE)
	public void handleInactiveCompany(CompanyInactivatedEvent event){
		log.info("업체 비활성화 이벤트 받음");

		productService.handleCompanyInactivated(event.companyId());

		log.info("비활성화된 업체의 상품 비활성화 성공");
	}

	@RabbitListener(queues = RabbitMQConfig.COMPANY_STATUS_QUEUE)
	public void handleCompanyStatus(CompanyStatusChangedEvent event){
		log.info("업체 상태 변경 이벤트 받음");

		if (event.status().equals("INACTIVE")) {
			productService.handleCompanyInactivated(event.companyId());
		} else if (event.status().equals("ACTIVE")) {
			productService.activateProductsByCompany(event.companyId());
		}
	}

	@RabbitListener(queues = RabbitMQConfig.COMPANY_HUB_CHANGED_QUEUE)
	public void handleHubChanged(CompanyHubChangeEvent event){
		log.info("업체의 소속 허브 변경 이벤트 받음");

		productService.updateProductsHubId(event.companyId(), event.newHubId());
	}
}
