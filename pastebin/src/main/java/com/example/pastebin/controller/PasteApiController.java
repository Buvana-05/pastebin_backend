package com.example.pastebin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pastebin.model.CreatePasteRequest;
import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteService;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/pastes")
@CrossOrigin(origins = "http://pastebin-frontend-lwn3.vercel.app")
public class PasteApiController {

	private final PasteService service;

	public PasteApiController(PasteService service) {
		this.service = service;
	}

	@GetMapping("/healthz")
	public Map<String, Boolean> health() {
		return Map.of("ok", true);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CreatePasteRequest req) {

		if (req.getContent() == null || req.getContent().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Content is required");
		}

		Paste paste = service.create(req.getContent(), req.getTtl(), req.getMaxViews());

		String url = "https://pastebin-backend-kwjl.onrender.com/p/" + paste.getId();

		return ResponseEntity.ok(Map.of("id", paste.getId().toString(), "url", url));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPaste(@PathVariable String id) {
		Optional<Paste> opt = service.findById(id);

		if (opt.isEmpty())
			return ResponseEntity.notFound().build();

		Paste paste = opt.get();

		if ((paste.getExpiresAt() != null && paste.getExpiresAt().isBefore(Instant.now()))
				|| (paste.getRemainingViews() != null && paste.getRemainingViews() <= 0)) {
			return ResponseEntity.notFound().build();
		}

		service.decrementViews(paste);

		return ResponseEntity.ok(Map.of("content", paste.getContent(), "remaining_views", paste.getRemainingViews(),
				"expires_at", paste.getExpiresAt()));
	}
}