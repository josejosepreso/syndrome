package com.proxies.client.util;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.PdfGeneratorResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
public class PdfGeneratorServiceClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.pdf-generator}")
	private String pdfGeneratorServiceUrl;

	public PdfGeneratorResponseDto generatePdf(BulkProxyDto dto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

			HttpEntity<BulkProxyDto> httpEntity = new HttpEntity<>(dto, headers);

			String url = this.pdfGeneratorServiceUrl + "/api/pdf-generator/generate-pdf-base64";

			ResponseEntity<PdfGeneratorResponseDto> res = this.restTemplate.postForEntity(url, httpEntity,
					PdfGeneratorResponseDto.class);

			if (isNull(res.getBody()))
				throw new Exception("Empty response body.");

			return res.getBody();
		} catch (Exception e) {
			return PdfGeneratorResponseDto.builder()
					.success(false)
					.message(e.getMessage())
					.document(null)
					.build();
		}
	}
}
