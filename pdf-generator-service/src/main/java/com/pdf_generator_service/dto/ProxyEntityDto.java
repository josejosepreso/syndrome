package com.pdf_generator_service.dto;

@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ProxyEntityDto {
    private String id;
    private String ip;
    private String port;
    private String source;
    private String registeredBy;
    String createdAt;
}
