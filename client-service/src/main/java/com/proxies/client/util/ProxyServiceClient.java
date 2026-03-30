package com.proxies.client.util;

import java.util.Arrays;
import java.util.List;

import com.proxies.client.dto.ProxyDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
public class ProxyServiceClient {
	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.proxies}")
	private String proxiesServiceUrl;

	public List<ProxyDto> scrapProxies() throws RuntimeException {
		String url = this.proxiesServiceUrl + "/api/proxies";
		ResponseEntity<ProxyDto[]> res = this.restTemplate.getForEntity(url, ProxyDto[].class);

		if (isNull(res.getBody()))
			throw new RuntimeException("Empty response body.");

		if (!res.getStatusCode().is2xxSuccessful())
			throw new RuntimeException("An error occured when getting proxies: " + Arrays.toString(res.getBody()));

		return Arrays.stream(res.getBody()).toList();
	}
}
