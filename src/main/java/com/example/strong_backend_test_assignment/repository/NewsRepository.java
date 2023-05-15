package com.example.strong_backend_test_assignment.repository;

import com.example.strong_backend_test_assignment.domain.model.News;
import com.example.strong_backend_test_assignment.domain.model.NewsSource;
import com.example.strong_backend_test_assignment.domain.model.NewsTopic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findBySource(NewsSource source, PageRequest pageRequest);
    List<News> findBySource(NewsSource source);
    List<News> findByTopics(NewsTopic topic);
}
