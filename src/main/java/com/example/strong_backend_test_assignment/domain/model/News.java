package com.example.strong_backend_test_assignment.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(
            name = "topics_news",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "news_topic_id")
    )
    private List<NewsTopic> topics;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    private NewsSource source;

    @CreationTimestamp
    @Column(name = "published")
    private LocalDateTime published;

}
