package com.pdf_generator_service.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.pdf_generator_service.dto.BulkProxyDto;
import com.pdf_generator_service.dto.PdfGeneratorResponseDto;
import com.pdf_generator_service.dto.ProxyDto;

import org.springframework.stereotype.Service;

import static com.pdf_generator_service.util.Configuration.*;

@Service
public class PdfGeneratorService {
	public PdfGeneratorResponseDto generatePdf(BulkProxyDto bulkProxyDto) throws IOException {
		String html = REPORT_BASE_HTML.replace(PARAM_PROXIES_RECORDS, this.getProxiesRowsHTML(bulkProxyDto));

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
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String getProxiesRowsHTML(BulkProxyDto bulkProxyDto) {
		StringBuilder rows = new StringBuilder();
		int i = 1;

		for (ProxyDto proxyDto : bulkProxyDto.getProxies())
			rows.append(this.getProxyRecordHTML(i++, proxyDto));

		return rows.toString();
	}

	private String getProxyRecordHTML(int id, ProxyDto dto) {
		return String.format(PROXY_RECORD_TR_HTML, id, dto.getIp(), dto.getPort(), dto.getSource())
			.replace("&", "&amp;");
	}
}
