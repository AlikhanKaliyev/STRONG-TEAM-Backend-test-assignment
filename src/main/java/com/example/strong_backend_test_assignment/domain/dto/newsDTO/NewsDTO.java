package com.example.strong_backend_test_assignment.domain.dto.newsDTO;

import com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO.NewsSourceDTO;
import com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO.NewsTopicDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsDTO{
    private long id;
    private String title;
    private String content;

    private NewsSourceDTO source;
    private List<NewsTopicDTO> topics;
}
