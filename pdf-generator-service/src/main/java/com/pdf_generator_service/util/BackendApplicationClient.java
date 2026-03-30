package com.pdf_generator_service.util;

import com.pdf_generator_service.dto.ProxyEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class BackendApplicationClient {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.backend}")
	private String backendServiceUrl;

	public List<ProxyEntityDto> getProxiesHistory() {
		try {
			String url = this.backendServiceUrl + "/api/history/";

			ResponseEntity<ProxyEntityDto[]> response = this.restTemplate.getForEntity(url, ProxyEntityDto[].class);

			if (isNull(response.getBody()))
				throw new Exception("Empty response body.");

			return Arrays.stream(response.getBody()).toList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
