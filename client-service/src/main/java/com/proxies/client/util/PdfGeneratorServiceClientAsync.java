package com.proxies.client.util;

import java.util.concurrent.CompletableFuture;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.PdfGeneratorResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PdfGeneratorServiceClientAsync {

	@Autowired
	private PdfGeneratorServiceClient pdfGeneratorServiceClient;

	public CompletableFuture<PdfGeneratorResponseDto> generatePdf(BulkProxyDto dto) {
		return CompletableFuture.supplyAsync(() -> this.pdfGeneratorServiceClient.generatePdf(dto));
	}
}
