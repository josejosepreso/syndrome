package com.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProxyDto {
	private String ip;
	private Integer port;
	private String source;
}
