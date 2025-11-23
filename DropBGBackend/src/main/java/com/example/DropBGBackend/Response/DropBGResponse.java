package com.example.DropBGBackend.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DropBGResponse {

    private boolean success;
    private HttpStatus statusCode;
    private Object data;
}
