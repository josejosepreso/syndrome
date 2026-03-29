package com.proxies.client.controller;

import com.proxies.client.service.ProxyService;
import com.proxies.client.util.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/proxies")
public class ProxyController {
	@Autowired
	private ProxyService service;

	@GetMapping
	public ResponseEntity<ApiResponse<?>> proxies() {
		ApiResponse<?> res;

		try {
			var item = this.service.executeProxiesScraper();

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
