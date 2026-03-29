package com.pdf_generator_service.dto;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class PdfGeneratorResponseDto {
	private boolean success;
	private String message;
	private String document;
}