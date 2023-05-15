package com.example.strong_backend_test_assignment.domain.dto.newsDTO;

import com.example.strong_backend_test_assignment.exception.CustomException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsDTO {

    private String title;
    private String content;
    private Long sourceId;
    private List<Long> topics;

    public void validate() {
        if(this.title == null || this.title.trim() == "") {
            throw new CustomException("title is not provided");
        }
        if(this.content == null || this.content.trim() == "") {
            throw new RuntimeException("content is not provided");
        }
        if(this.topics == null || topics.isEmpty()) {
            throw new RuntimeException("topics are not provided");
        }
        if(this.sourceId == null) {
            throw new RuntimeException("invalid news source");
        }
    }
}
