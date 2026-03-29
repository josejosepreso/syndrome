package com.backend.application.util;

import java.util.Date;
import java.util.function.Function;

import com.backend.application.domain.entity.Proxy;
import com.backend.application.dto.ProxyDto;

import org.springframework.stereotype.Component;

@Component
public class ProxyMapper {
	public Proxy toEntity(ProxyDto dto, String registeredBy) {
		return Proxy.builder()
				.ip(dto.getIp())
				.port(dto.getPort())
				.source(dto.getSource())
				.createdAt(new Date())
				.registeredBy(registeredBy)
				.build();
	}

	public Function<ProxyDto, Proxy> toEntityFunction(String registeredBy) {
		return dto -> this.toEntity(dto, registeredBy);
	}
}
