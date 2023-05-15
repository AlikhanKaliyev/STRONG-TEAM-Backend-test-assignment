package com.example.strong_backend_test_assignment.domain.dto.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenResponseDTO {
    private String token;
}
