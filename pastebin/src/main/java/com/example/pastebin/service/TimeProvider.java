package com.example.pastebin.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimeProvider {
	private final HttpServletRequest request;

	public TimeProvider(HttpServletRequest request) {
		this.request = request;
	}

	public Instant now() {
		String testMode = System.getenv("TEST_MODE");
		if ("1".equals(testMode)) {
			String header = request.getHeader("x-test-now-ms");
			if (header != null) {
				try {
					long millis = Long.parseLong(header);
					return Instant.ofEpochMilli(millis);
				} catch (NumberFormatException e) {
					// fallback to real time if header is invalid
				}
			}
		}
		return Instant.now();
	}
}
