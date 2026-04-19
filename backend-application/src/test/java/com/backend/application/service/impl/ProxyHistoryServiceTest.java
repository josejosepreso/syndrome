package com.backend.application.service.impl;

import com.backend.application.domain.entity.Proxy;
import com.backend.application.domain.repository.ProxyRepository;
import com.backend.application.dto.BulkProxyDto;
import com.backend.application.dto.BulkProxyResponseDto;
import com.backend.application.dto.ProxyDto;
import com.backend.application.util.ProxyMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static com.backend.application.util.TestConstants.*;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProxyHistoryServiceTest {

    @Mock ProxyRepository proxyRepository;
    @Mock ProxyMapper proxyMapper;

    @InjectMocks ProxyHistoryService proxyHistoryService;

    @Test
    void shouldGetProxyHistoryRecords() {
        when(this.proxyRepository.findAll())
                .thenReturn(List.of(mock(Proxy.class)));

        assertFalse(this.proxyHistoryService.getAll().isEmpty());
    }

    @Test
    void shouldNotGetProxyHistoryRecordsForEmptyTable() {
        when(this.proxyRepository.findAll())
                .thenReturn(emptyList());

        assertTrue(this.proxyHistoryService.getAll().isEmpty());
    }

    @Test
    void shouldSaveOneProxyRecord() {
        Proxy proxy = new Proxy(0L, TEST_IP_ADDRESS, TEST_PORT_NUMBER, SOURCE_TEST_URL, REGISTERED_BY_TEST_NAME, new Date());

        when(this.proxyRepository.save(any()))
                .thenReturn(proxy);

        Proxy result = this.proxyHistoryService.saveProxy(mock(ProxyDto.class));

        assertEquals(REGISTERED_BY_TEST_NAME, result.getRegisteredBy());
        verify(this.proxyRepository).save(any());
    }

    @Test
    void shouldNotThrowExceptionForEmptyProxyList() {
        lenient().when(this.proxyRepository.save(any()))
                .thenThrow(new RuntimeException());

        when(this.proxyMapper.toEntityFunction(any()))
                .thenReturn(proxyDto -> mock(Proxy.class));

        BulkProxyDto bulkProxyDto = new BulkProxyDto(BAUTISTA, emptyList());

        BulkProxyResponseDto responseDto = this.proxyHistoryService.saveAll(bulkProxyDto);

        assertEquals("Success. 0 proxies were saved", responseDto.getMessage());
        verify(this.proxyRepository, never()).save(any());
    }

    @Test
    void shouldSaveProxiesRecords() {
        when(this.proxyMapper.toEntityFunction(anyString()))
                .thenReturn(proxyDto -> mock(Proxy.class));

        var proxies = IntStream.range(0, N_MOCK_PROXIES)
                .mapToObj(i -> mock(ProxyDto.class))
                .toList();

        System.out.println(proxies);

        BulkProxyDto bulkProxyDto = new BulkProxyDto(BAUTISTA, proxies);

        BulkProxyResponseDto responseDto = this.proxyHistoryService.saveAll(bulkProxyDto);

        assertEquals(String.format("Success. %s proxies were saved", N_MOCK_PROXIES), responseDto.getMessage());
        verify(this.proxyMapper, times(1)).toEntityFunction(anyString());
        verify(this.proxyRepository, times(N_MOCK_PROXIES)).save(any());
    }
}
