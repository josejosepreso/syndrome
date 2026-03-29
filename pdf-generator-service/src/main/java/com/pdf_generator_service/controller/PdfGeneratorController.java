package com.pdf_generator_service.controller;

import com.pdf_generator_service.dto.BulkProxyDto;
import com.pdf_generator_service.service.IPdfGeneratorService;
import com.pdf_generator_service.util.ErrorResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf-generator")
@RequiredArgsConstructor
public class PdfGeneratorController {

	@Autowired
	private final IPdfGeneratorService pdfGeneratorService;

	@PostMapping("/generate-pdf-base64")
	public ResponseEntity<?> generate(@RequestBody BulkProxyDto bulkProxyDto) {
		try {
			return ResponseEntity.ok(this.pdfGeneratorService.generateScrappedPdf(bulkProxyDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(e.getMessage()));
		}
	}

    @GetMapping("/generate-report")
    public ResponseEntity<?> report() {
        try {
            return ResponseEntity.ok(this.pdfGeneratorService.generateHistoryPdf());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
}
