package com.proxies.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkProxyResponseDto {
	@Getter @Setter private String status;
	@Getter @Setter private String message;
}
