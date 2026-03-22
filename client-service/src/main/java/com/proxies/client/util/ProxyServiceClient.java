package com.proxies.client.util;

import java.util.Arrays;
import java.util.List;

import com.proxies.client.dto.ProxyDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProxyServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.proxies}")
    private String proxiesServiceUrl;

    public List<ProxyDto> getAllProxies() {
        ResponseEntity<ProxyDto[]> res = this.restTemplate.getForEntity(this.proxiesServiceUrl, ProxyDto[].class);
        return Arrays.stream(res.getBody()).toList();
    }
}
