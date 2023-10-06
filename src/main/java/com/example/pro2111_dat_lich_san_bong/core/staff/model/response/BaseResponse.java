package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class BaseResponse<Entity> {
    private HttpStatus httpStatus;
    private String message;
    private Entity content;

    public BaseResponse(HttpStatus httpStatus, String message, Entity content) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.content = content;
    }
}
