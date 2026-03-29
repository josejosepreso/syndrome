package com.proxies.client.service;

import java.util.List;

import com.proxies.client.dto.BulkProxyResponseDto;
import com.proxies.client.dto.ProxyDto;
import com.proxies.client.util.ProxyServiceClient;
import com.proxies.client.util.BackendApplicationClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {
    @Autowired
    private ProxyServiceClient proxiesClient;

    @Autowired
    private BackendApplicationClient backendClient;

	public List<ProxyDto> getAllProxies() throws Exception {
		return this.proxiesClient.getAllProxies();
	}

	public BulkProxyResponseDto executeProxiesScraper() throws Exception {
		return this.backendClient.performSaveAllProxies(this.getAllProxies());
	}
}
