package com.shop28.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class ErrorResponse {
    private Integer status;
    private String message;
    private String error;
    private String path;
    private Date timestamp;
}
