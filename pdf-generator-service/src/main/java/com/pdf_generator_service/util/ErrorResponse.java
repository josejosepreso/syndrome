package com.pdf_generator_service.util;

import lombok.Getter;
import lombok.Setter;

public class ErrorResponse {
	@Getter @Setter private boolean success;
	@Getter @Setter private String message;

	public ErrorResponse(String message) {
		this.success = false;
		this.message = message;
	}
}
