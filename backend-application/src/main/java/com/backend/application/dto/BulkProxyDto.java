package com.backend.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BulkProxyDto {
	@Getter @Setter private String registeredBy;
	@Getter @Setter List<ProxyDto> proxies;
}
