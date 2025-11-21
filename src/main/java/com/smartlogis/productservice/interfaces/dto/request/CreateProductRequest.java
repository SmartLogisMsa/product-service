package com.smartlogis.productservice.interfaces.dto.request;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 생성 요청")
public class CreateProductRequest {

	@Schema(description = "상품명")
	private String name;

	@Schema(description = "생산 업체 id")
	private UUID companyId;

	@Schema(description = "허브 id")
	private UUID hubId;

	@Schema(description = "재고")
	private Integer stock;

	@Schema(description = "담당 매니저 id")
	private UUID managerId;
}
