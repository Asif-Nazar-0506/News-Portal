package com.itransition.newsportal.service;

import com.itransition.newsportal.dto.NewsDTO;
import com.itransition.newsportal.model.News;
import com.itransition.newsportal.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final Random random = new Random();

    private static final String[] CATEGORIES = {
            "Technology", "Sports", "Politics", "Entertainment", "Business", "Health", "Science"
    };

    private static final String[] AUTHORS = {
            "John Smith", "Emma Wilson", "Michael Brown", "Sarah Johnson",
            "David Lee", "Lisa Anderson", "James Taylor"
    };

    private static final String[] TITLES = {
            "Breaking: Major Development in %s Industry",
            "Experts Predict Changes in %s Sector",
            "New Study Reveals Insights About %s",
            "Latest Updates on %s Trends",
            "Analysis: The Future of %s",
            "Investigation: What's Happening in %s",
            "Report: Significant Progress in %s Field"
    };

    @Transactional(readOnly = true)
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAllByOrderByPublishedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NewsDTO> getNewsSince(LocalDateTime timestamp) {
        return newsRepository.findNewsSince(timestamp)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public NewsDTO generateRandomNews() {
        String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
        String author = AUTHORS[random.nextInt(AUTHORS.length)];
        String titleTemplate = TITLES[random.nextInt(TITLES.length)];
        String title = String.format(titleTemplate, category);

        String content = generateContent(category);

        News news = new News();
        news.setTitle(title);
        news.setContent(content);
        news.setAuthor(author);
        news.setCategory(category);
        news.setPublishedAt(LocalDateTime.now());

        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
    }

    private String generateContent(String category) {
        return String.format(
                "This is an automatically generated news article about %s. " +
                        "The content provides important updates and information for our readers. " +
                        "Recent developments have shown significant progress in this area, " +
                        "with experts weighing in on the implications for the future. " +
                        "Stay tuned for more updates as this story develops.",
                category.toLowerCase()
        );
    }

    private NewsDTO convertToDTO(News news) {
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getAuthor(),
                news.getCategory(),
                news.getPublishedAt()
        );
    }
}