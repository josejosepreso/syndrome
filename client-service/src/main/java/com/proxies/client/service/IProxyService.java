package com.proxies.client.service;

import java.util.List;

import com.proxies.client.dto.ProxiesScraperResponseDto;
import com.proxies.client.dto.ProxyDto;

public interface IProxyService {

	public List<ProxyDto> getAllProxies() throws RuntimeException;

	public ProxiesScraperResponseDto executeProxiesScraper();

	public ProxiesScraperResponseDto executeProxiesScraperAsync() throws RuntimeException;
}