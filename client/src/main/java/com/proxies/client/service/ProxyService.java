package com.proxies.client.service;

import java.util.List;

import com.proxies.client.dto.ProxyDto;
import com.proxies.client.util.ProxyServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyService {
    @Autowired
    private ProxyServiceClient client;

    public List<ProxyDto> getAllProxies() {
        return this.client.getAllProxies();
    }
}
