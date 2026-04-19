package com.backend.application.util;

import com.backend.application.domain.entity.Proxy;
import com.backend.application.dto.ProxyDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.backend.application.util.TestConstants.*;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@ExtendWith(MockitoExtension.class)
class ProxyMapperTest {
    ProxyMapper proxyMapper;

    @BeforeEach
    void setUp() {
        this.proxyMapper = new ProxyMapper();
    }

    @Test
    void shouldMapProxyDtoToEntity() {
        ProxyDto dto = new ProxyDto(TEST_IP_ADDRESS, TEST_PORT_NUMBER, SOURCE_TEST_URL);

        Proxy proxy = this.proxyMapper.toEntity(dto, BAUTISTA);

        assumingThat(
                nonNull(proxy),
                () -> assertAll(
                        () -> assertEquals(dto.getIp(), proxy.getIp()),
                        () -> assertEquals(dto.getPort(), proxy.getPort()),
                        () -> assertEquals(dto.getSource(), proxy.getSource()),
                        () -> assertEquals(BAUTISTA, proxy.getRegisteredBy()),
                        () -> assertNotNull(proxy.getCreatedAt())
                )
        );
    }
}