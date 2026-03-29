package com.proxies.client.service;

import java.util.List;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.BulkProxyResponseDto;
import com.proxies.client.dto.PdfGeneratorResponseDto;
import com.proxies.client.dto.ProxiesScraperResponseDto;
import com.proxies.client.dto.ProxyDto;
import com.proxies.client.util.ProxyServiceClient;
import com.proxies.client.util.BackendApplicationClient;
import com.proxies.client.util.PdfGeneratorServiceClient;

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

	public List<ProxyDto> getAllProxies() throws Exception {
		return this.proxiesClient.getAllProxies();
	}

	public ProxiesScraperResponseDto executeProxiesScraper() throws Exception {
		List<ProxyDto> proxies = this.getAllProxies();

		BulkProxyDto dto = BulkProxyDto.builder()
				.proxies(proxies)
				.registeredBy(null)
				.build();

		BulkProxyResponseDto backendResponse = this.backendClient.performSaveAllProxies(dto);

		PdfGeneratorResponseDto pdfGeneratorResponse = this.pdfGeneratorServiceClient.generatePdf(dto);

		return ProxiesScraperResponseDto.builder()
				.status(backendResponse.getStatus())
				.message(backendResponse.getMessage())
				.documentResponse(pdfGeneratorResponse)
				.build();
	}
}
