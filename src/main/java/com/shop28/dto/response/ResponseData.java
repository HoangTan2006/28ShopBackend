package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
@Builder
public class ResponseData<T> {
    private Integer status;
    private String message;
    private T data;
}
