package com.itransition.newsportal.controller;

import com.itransition.newsportal.dto.NewsDTO;
import com.itransition.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NewsController {

    private final NewsService newsService;

    /**
     * Get all news articles ordered by published date (newest first)
     */
    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<NewsDTO> news = newsService.getAllNews();
        return ResponseEntity.ok(news);
    }

    /**
     * Get news articles published after a specific timestamp
     * This endpoint is used for polling to get only new articles
     *
     * Example: /api/news/updates?since=2024-01-15T10:30:00
     */
    @GetMapping("/updates")
    public ResponseEntity<List<NewsDTO>> getNewsUpdates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime since) {
        List<NewsDTO> newArticles = newsService.getNewsSince(since);
        return ResponseEntity.ok(newArticles);
    }
}