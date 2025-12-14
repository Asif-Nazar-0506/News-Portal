package com.itransition.newsportal.repository;

import com.itransition.newsportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByPublishedAtDesc();

    List<News> findByPublishedAtAfterOrderByPublishedAtDesc(LocalDateTime timestamp);

    @Query("SELECT n FROM News n WHERE n.publishedAt > :timestamp ORDER BY n.publishedAt DESC")
    List<News> findNewsSince(LocalDateTime timestamp);
}