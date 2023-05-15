package com.example.strong_backend_test_assignment.domain.dto.newsSourceDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsSourceDTO {
    private String name;

    public void validate() {
        if(this.name == null || this.name.trim() == ""){
            throw new RuntimeException("name is not provided");
        }
    }
}
