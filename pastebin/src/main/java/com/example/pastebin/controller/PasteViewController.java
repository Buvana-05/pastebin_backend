package com.example.pastebin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.HtmlUtils;

import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteService;

import java.util.Optional;
import java.util.UUID;

@Controller
public class PasteViewController {
	private final PasteService service;

	public PasteViewController(PasteService service) {
		this.service = service;
	}

	@GetMapping(value = "/p/{id}", produces = "text/plain")
	public ResponseEntity<String> viewPaste(@PathVariable String id) {
		Optional<Paste> paste = service.findById(id);
		if (paste.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		String safeContent = HtmlUtils.htmlEscape(paste.get().getContent());
		return ResponseEntity.ok(safeContent);
	}
}
