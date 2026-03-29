package com.proxies.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdfGeneratorResponseDto {
	private boolean success;
	private String message;
	private String document;
}
