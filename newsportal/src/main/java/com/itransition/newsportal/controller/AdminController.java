package com.itransition.newsportal.controller;

import com.itransition.newsportal.dto.NewsDTO;
import com.itransition.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final NewsService newsService;

    /**
     * Administrative endpoint to generate a random news article
     * POST /api/admin/generate-news
     */
    @PostMapping("/generate-news")
    public ResponseEntity<Map<String, Object>> generateNews() {
        NewsDTO generatedNews = newsService.generateRandomNews();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "News article generated successfully");
        response.put("news", generatedNews);

        return ResponseEntity.ok(response);
    }

    /**
     * Generate multiple news articles at once
     * POST /api/admin/generate-bulk?count=5
     */
    @PostMapping("/generate-bulk")
    public ResponseEntity<Map<String, Object>> generateBulkNews(
            @RequestParam(defaultValue = "5") int count) {

        if (count < 1 || count > 20) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Count must be between 1 and 20");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        for (int i = 0; i < count; i++) {
            newsService.generateRandomNews();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", count + " news articles generated successfully");
        response.put("count", count);

        return ResponseEntity.ok(response);
    }
}
