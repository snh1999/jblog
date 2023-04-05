package com.example.jblog.filter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyExceptionResponse implements ErrorResponse {

    private HttpStatusCode statusCode;
    private String details;


    @NonNull
    public ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(statusCode, details);
    }
}