package com.backend.application.controller;

import com.backend.application.dto.BulkProxyDto;
import com.backend.application.dto.ProxyDto;
import com.backend.application.service.ProxyHistoryService;
import com.backend.application.util.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class ProxyHistoryController {
	@Autowired
	private ProxyHistoryService proxyHistoryService;

	@PostMapping("/save")
	public ResponseEntity<?> saveProxy(@RequestBody ProxyDto proxyDto) {
		try {
			return ResponseEntity.ok(this.proxyHistoryService.saveProxy(proxyDto));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/save/all/")
	public ResponseEntity<?> saveAll(@RequestBody BulkProxyDto bulkProxyDto) {
		try {
			return ResponseEntity.ok(this.proxyHistoryService.saveAll(bulkProxyDto));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(String.format("An error ocurred: %s",e.getMessage())));
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getAll() {
		try {
			return ResponseEntity.ok(this.proxyHistoryService.getAll());
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(e.getMessage()));
		}
	}
}
