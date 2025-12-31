package com.example.pastebin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pastebin.model.Paste;

public interface PasteRepository extends JpaRepository<Paste, String> {
}