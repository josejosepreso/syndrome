package com.pdf_generator_service.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.pdf_generator_service.dto.BulkProxyDto;
import com.pdf_generator_service.dto.PdfGeneratorResponseDto;
import com.pdf_generator_service.dto.ProxyDto;

import com.pdf_generator_service.dto.ProxyEntityDto;
import com.pdf_generator_service.service.IPdfGeneratorService;
import com.pdf_generator_service.util.BackendApplicationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pdf_generator_service.util.Configuration.*;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService implements IPdfGeneratorService {

    @Autowired
    private final BackendApplicationClient backendApplicationClient;

    public PdfGeneratorResponseDto generateScrappedPdf(BulkProxyDto bulkProxyDto) throws IOException {
        String html = REPORT_BASE_HTML.replace(PARAM_PROXIES_RECORDS, this.getProxiesRowsHTML(bulkProxyDto.getProxies()))
                .replace("&", "&amp;");

        return this.generatePdf(html);
    }

    private String getProxiesRowsHTML(List<ProxyDto> proxies) {
        return this.transformAndCollectString(proxies, this::getProxyRecordHTML);
    }

    private String getProxyRecordHTML(ProxyDto dto) {
        return String.format(PROXY_RECORD_TR_HTML, dto.getIp(), dto.getPort(), dto.getSource());
    }

    public PdfGeneratorResponseDto generateHistoryPdf() throws IOException {
        List<ProxyEntityDto> proxies = this.backendApplicationClient.getProxiesHistory();
        String html = REPORT_HISTORY_BASE_HTML.replace(PARAM_PROXIES_RECORDS, this.getProxiesEntitiesRowsHTML(proxies))
                .replace("&", "&amp;");

        return this.generatePdf(html);
    }

    private String getProxiesEntitiesRowsHTML(List<ProxyEntityDto> proxies) {
        return this.transformAndCollectString(proxies, this::getProxyEntityRecordHTML);
    }

    private String getProxyEntityRecordHTML(ProxyEntityDto proxy) {
        return String.format(PROXY_ENTITY_RECORD_TR_HTML,
                        proxy.getId(),
                        proxy.getIp(),
                        proxy.getPort(),
                        proxy.getSource(),
                        proxy.getRegisteredBy(),
                        proxy.getCreatedAt()
        );
    }

    private PdfGeneratorResponseDto generatePdf(String html) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();

            byte[] pdfBytes = os.toByteArray();

            String documentBase64 = Base64.getEncoder().encodeToString(pdfBytes);

            return PdfGeneratorResponseDto.builder()
                    .success(true)
                    .message("Ok")
                    .document(documentBase64)
                    .build();
        }
    }

    private <T> String transformAndCollectString(List<T> items, Function<T, String> convert) {
        return items.stream()
                .map(convert)
                .collect(Collectors.joining());
    }
}
