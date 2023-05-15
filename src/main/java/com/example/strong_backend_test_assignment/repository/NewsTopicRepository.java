package com.example.strong_backend_test_assignment.repository;

import com.example.strong_backend_test_assignment.domain.model.NewsTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTopicRepository extends JpaRepository<NewsTopic, Long> {
}
