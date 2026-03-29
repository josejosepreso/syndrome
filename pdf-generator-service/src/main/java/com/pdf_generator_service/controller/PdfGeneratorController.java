package com.pdf_generator_service.controller;

import com.pdf_generator_service.dto.BulkProxyDto;
import com.pdf_generator_service.service.PdfGeneratorService;
import com.pdf_generator_service.util.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf-generator")
public class PdfGeneratorController {

	@Autowired
	private PdfGeneratorService pdfGeneratorService;

	@PostMapping("/generate-pdf-base64")
	public ResponseEntity<?> generate(@RequestBody BulkProxyDto bulkProxyDto) {
		try {
			return ResponseEntity.ok(this.pdfGeneratorService.generatePdf(bulkProxyDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(e.getMessage()));
		}
	}
}
