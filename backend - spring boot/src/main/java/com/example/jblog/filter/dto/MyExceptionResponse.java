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
public class MyExceptionResponse {
    private String details;

}