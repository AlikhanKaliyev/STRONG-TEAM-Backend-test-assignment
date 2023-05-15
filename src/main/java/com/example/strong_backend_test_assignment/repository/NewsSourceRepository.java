package com.example.strong_backend_test_assignment.repository;

import com.example.strong_backend_test_assignment.domain.model.NewsSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSourceRepository extends JpaRepository<NewsSource,Long> {
}
