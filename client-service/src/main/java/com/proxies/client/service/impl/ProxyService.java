package com.proxies.client.service.impl;

import com.proxies.client.dto.*;
import com.proxies.client.service.IProxyService;
import com.proxies.client.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProxyService implements IProxyService {

	@Autowired
	private ProxyServiceClient proxiesClient;

	@Autowired
	private BackendApplicationClient backendClient;

	@Autowired
	private PdfGeneratorServiceClient pdfGeneratorServiceClient;

	@Autowired
	private BackendApplicationClientAsync backendClientAsync;

	@Autowired
	private PdfGeneratorServiceClientAsync pdfGeneratorServiceClientAsync;

	public List<ProxyDto> getAllProxies() throws RuntimeException {
		return this.proxiesClient.scrapProxies();
	}

	public ProxiesScraperResponseDto executeProxiesScraper() {
		BulkProxyDto dto = this.getBulkProxyDto();

		BulkProxyResponseDto backendResponse = this.backendClient.performSaveAllProxies(dto);

		PdfGeneratorResponseDto pdfGeneratorResponse = this.pdfGeneratorServiceClient.generatePdf(dto);

		return ProxiesScraperResponseDto.builder()
				.status(backendResponse.getStatus())
				.message(backendResponse.getMessage())
				.documentResponse(pdfGeneratorResponse)
				.build();
	}

	public ProxiesScraperResponseDto executeProxiesScraperAsync() throws RuntimeException {
		BulkProxyDto dto = this.getBulkProxyDto();

		CompletableFuture<BulkProxyResponseDto> backendFuture = this.backendClientAsync.performSaveAllProxies(dto);

		CompletableFuture<PdfGeneratorResponseDto> pdfGeneratorFuture = this.pdfGeneratorServiceClientAsync.generatePdf(dto);

		CompletableFuture<ProxiesScraperResponseDto> future = backendFuture.thenCombine(pdfGeneratorFuture,
				(backendResponse, pdfGeneratorResponse) -> ProxiesScraperResponseDto.builder()
						.status(backendFuture.join().getStatus())
						.message(backendFuture.join().getMessage())
						.documentResponse(pdfGeneratorFuture.join())
						.build());

		return future.join();
	}

	private BulkProxyDto getBulkProxyDto() {
		return BulkProxyDto.builder()
				.proxies(this.getAllProxies())
				.registeredBy(null)
				.build();
	}
}
