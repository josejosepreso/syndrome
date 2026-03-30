package com.backend.application.service;

import com.backend.application.domain.entity.Proxy;
import com.backend.application.dto.BulkProxyDto;
import com.backend.application.dto.BulkProxyResponseDto;
import com.backend.application.dto.ProxyDto;

import java.util.List;

public interface IProxyHistoryService {

	public List<Proxy> getAll();

	public Proxy saveProxy(ProxyDto proxyDto);

	public BulkProxyResponseDto saveAll(BulkProxyDto bulkProxyDto) throws Exception;
}
