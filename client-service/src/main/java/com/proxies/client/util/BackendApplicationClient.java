package com.proxies.client.util;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.BulkProxyResponseDto;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
public class BackendApplicationClient {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.backend}")
	private String backendServiceUrl;

	public BulkProxyResponseDto performSaveAllProxies(BulkProxyDto dto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

			HttpEntity<BulkProxyDto> httpEntity = new HttpEntity<>(dto, headers);

			String url = this.backendServiceUrl + "/api/history/save/all/";

			ResponseEntity<BulkProxyResponseDto> response = this.restTemplate.postForEntity(url, httpEntity, BulkProxyResponseDto.class);

			if (isNull(response.getBody()))
				throw new Exception("Empty response body.");

			return response.getBody();
		} catch (Exception e) {
			return BulkProxyResponseDto.builder()
					.status("Error")
					.message(e.getMessage())
					.build();
		}
	}
}
