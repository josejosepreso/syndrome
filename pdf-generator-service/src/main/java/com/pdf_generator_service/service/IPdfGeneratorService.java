package com.pdf_generator_service.service;

import com.pdf_generator_service.dto.BulkProxyDto;
import com.pdf_generator_service.dto.PdfGeneratorResponseDto;

import java.io.IOException;

public interface IPdfGeneratorService {
	public PdfGeneratorResponseDto generateScrappedPdf(BulkProxyDto bulkProxyDto) throws IOException;

    public PdfGeneratorResponseDto generateHistoryPdf() throws IOException;
}
