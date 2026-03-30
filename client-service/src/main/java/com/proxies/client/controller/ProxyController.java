package com.proxies.client.controller;

import com.proxies.client.dto.ProxiesScraperResponseDto;
import com.proxies.client.service.IProxyService;
import com.proxies.client.util.ApiResponse;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import tools.jackson.databind.ObjectMapper;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/proxies")
public class ProxyController {

	@Autowired
	private IProxyService service;

	@GetMapping
	@SneakyThrows
	public ResponseEntity<ApiResponse<?>> getProxiesAsync() {
		return this.proxies(this.service::executeProxiesScraperAsync);
	}

	@GetMapping("/sync")
	@SneakyThrows
	public ResponseEntity<ApiResponse<?>> getProxiesSync() {
		return this.proxies(this.service::executeProxiesScraper);
	}

	private ResponseEntity<ApiResponse<?>> proxies(Supplier<ProxiesScraperResponseDto> method) {
		ApiResponse<?> res;

		try {
			ProxiesScraperResponseDto item = method.get();

			res = ApiResponse.builder()
					.ok(true)
					.message(null)
					.item(item)
					.build();

			return ResponseEntity.ok(res);

		} catch (HttpServerErrorException errorException) {

			res = ApiResponse.builder()
					.item(new ObjectMapper().readTree(errorException.getResponseBodyAsString()))
					.ok(false)
					.message("Error")
					.build();

		} catch (Exception e) {

			res = ApiResponse.builder()
					.item(null)
					.ok(false)
					.message(e.getMessage())
					.build();
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(res);
	}
}
