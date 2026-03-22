package com.proxies.client.util;

import lombok.Getter;
import lombok.Setter;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class ApiResponse<T> {
    @Getter @Setter private boolean ok;
    @Getter @Setter private String message;
    @Getter @Setter private T item;
}
