package com.backend.application.service;

import java.util.List;
import java.util.function.Function;
import java.util.Date;

import com.backend.application.domain.entity.Proxy;
import com.backend.application.domain.repository.ProxyRepository;
import com.backend.application.dto.BulkProxyDto;
import com.backend.application.dto.BulkProxyResponseDto;
import com.backend.application.dto.ProxyDto;
import com.backend.application.util.ProxyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNullElse;

@Service
public class ProxyHistoryService {
	@Autowired
	private ProxyRepository proxyRepository;

	@Autowired
	private ProxyMapper proxyMapper;

	@Value("${default.username}")
	private String username;

	public List<Proxy> getAll() {
		return this.proxyRepository.findAll();
	}

	public Proxy saveProxy(ProxyDto proxyDto) {
		Proxy proxy = Proxy.builder()
				.ip(proxyDto.getIp())
				.port(proxyDto.getPort())
				.source(proxyDto.getSource())
				.createdAt(new Date())
				.registeredBy(this.username)
				.build();

		return this.proxyRepository.save(proxy);
	}

	public BulkProxyResponseDto saveAll(BulkProxyDto bulkProxyDto) throws Exception {
		String registeredBy = requireNonNullElse(bulkProxyDto.getRegisteredBy(), this.username);

		Function<ProxyDto, Proxy> toEntity = this.proxyMapper.toEntityFunction(registeredBy);

		bulkProxyDto.getProxies()
				.stream()
				.map(toEntity)
				.forEach(this.proxyRepository::save);

		return BulkProxyResponseDto.builder()
				.status(HttpStatus.OK.name())
				.message(String.format("Success. %s proxies were saved", bulkProxyDto.getProxies().size()))
				.build();
	}
}
