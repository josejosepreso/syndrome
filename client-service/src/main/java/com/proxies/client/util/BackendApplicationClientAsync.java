package com.proxies.client.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.proxies.client.dto.BulkProxyDto;
import com.proxies.client.dto.BulkProxyResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BackendApplicationClientAsync {

	@Autowired
	private BackendApplicationClient backendApplicationClient;

	public CompletableFuture<BulkProxyResponseDto> performSaveAllProxies(BulkProxyDto dto) {
		return CompletableFuture.supplyAsync(() -> this.backendApplicationClient.performSaveAllProxies(dto));
	}
}
