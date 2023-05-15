package com.example.strong_backend_test_assignment.domain.dto.newsTopicDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNewsTopicDTO {
    private String name;

    public void validate() {
        if (this.name.trim() == ""){
            throw new RuntimeException("name is not provided");
        }
    }
}
