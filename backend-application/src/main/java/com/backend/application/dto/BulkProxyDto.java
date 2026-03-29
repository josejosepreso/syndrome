package com.backend.application.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BulkProxyDto {
	@Getter @Setter private String registeredBy;
	@Getter @Setter List<ProxyDto> proxies;
}
