package br.com.upper.product.api.config;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SuccessResponse {
    private Integer status;
    private String message;

    public static SuccessResponse create(String message) {
        return SuccessResponse.builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}
