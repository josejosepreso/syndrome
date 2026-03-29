package com.proxies.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProxiesScraperResponseDto {
	private String status;
	private String message;
	private PdfGeneratorResponseDto documentResponse;
}
