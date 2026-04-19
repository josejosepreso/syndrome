package com.proxies.client.service.impl;

import com.proxies.client.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProxyServiceTest {
    @Mock ProxyServiceClient proxyServiceClient;
    @Mock BackendApplicationClient backendApplicationClient;
    @Mock PdfGeneratorServiceClient pdfGeneratorServiceClient;
    @Mock BackendApplicationClientAsync backendApplicationClientAsync;
    @Mock PdfGeneratorServiceClientAsync pdfGeneratorServiceClientAsync;

    @InjectMocks ProxyService proxyService;

    @Test
    void shouldNotGetProxiesForEmptyProxyServiceResponse() {
        when(this.proxyServiceClient.scrapProxies())
                .thenReturn(emptyList());

        assertTrue(this.proxyService.getAllProxies().isEmpty());
    }
}