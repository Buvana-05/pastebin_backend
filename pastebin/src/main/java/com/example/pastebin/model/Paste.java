package com.example.pastebin.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "pastes")
public class Paste {
	@Id
	private String id;

	@Column(nullable = false, columnDefinition = "text")
	private String content;

	@Column(nullable = false)
	private Integer remainingViews = 0;

	@Column(nullable = false)
	private Integer maxViews = 10;

	@Column(nullable = false)
	private int views = 0;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	private Instant expiresAt;

	public Integer getRemainingViews() {
		return remainingViews;
	}

	public void setRemainingViews(Integer remainingViews) {
		this.remainingViews = remainingViews;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
	void onCreate() {
		this.createdAt = Instant.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMaxViews() {
		return maxViews;
	}

	public void setMaxViews(Integer maxViews) {
		this.maxViews = maxViews;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
}