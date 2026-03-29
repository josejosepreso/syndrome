package com.pdf_generator_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkProxyDto {
	@Getter @Setter private List<ProxyDto> proxies;
	@Getter @Setter private String registeredBy;
}
