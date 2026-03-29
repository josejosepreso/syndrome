package com.backend.application.dto;

import lombok.Data;

@Data
public class ProxyDto {
	private String ip;
	private Integer port;
	private String source;
}
