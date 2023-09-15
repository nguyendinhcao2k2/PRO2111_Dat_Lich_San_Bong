package com.example.pro2111_dat_lich_san_bong.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author caodinh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestApiException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

}
