package com.proxies.client.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.BulkProxyResponseDto;
import com.proxies.client.dto.PdfGeneratorResponseDto;
import com.proxies.client.dto.ProxiesScraperResponseDto;
import com.proxies.client.dto.ProxyDto;
import com.proxies.client.util.ProxyServiceClient;
import com.proxies.client.util.BackendApplicationClient;
import com.proxies.client.util.PdfGeneratorServiceClient;
import com.proxies.client.util.BackendApplicationClientAsync;
import com.proxies.client.util.PdfGeneratorServiceClientAsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {

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

        CompletableFuture<ProxiesScraperResponseDto> future = backendFuture.thenCombine(pdfGeneratorFuture, (backendResponse, pdfGeneratorResponse) ->
                        ProxiesScraperResponseDto.builder()
                                .status(backendFuture.join().getStatus())
                                .message(backendFuture.join().getMessage())
                                .documentResponse(pdfGeneratorFuture.join())
                                .build()
                );

        return future.join();
	}

    private BulkProxyDto getBulkProxyDto() {
        return BulkProxyDto.builder()
                .proxies(this.getAllProxies())
                .registeredBy(null)
                .build();
    }
}