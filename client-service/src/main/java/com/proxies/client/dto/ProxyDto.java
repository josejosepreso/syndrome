package com.proxies.client.dto;

import lombok.Getter;
import lombok.Setter;

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ProxyDto {
    @Getter @Setter private String ip;
    @Getter @Setter private Integer port;
}
