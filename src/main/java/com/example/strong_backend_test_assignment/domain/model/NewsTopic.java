package com.example.strong_backend_test_assignment.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "news_topics")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "topics",cascade = CascadeType.REMOVE)
    private List<News> news;

}
