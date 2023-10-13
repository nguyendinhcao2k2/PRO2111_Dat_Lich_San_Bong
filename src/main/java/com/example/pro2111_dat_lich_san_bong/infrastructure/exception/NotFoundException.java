package com.example.pro2111_dat_lich_san_bong.infrastructure.exception;

import lombok.AllArgsConstructor;

/**
 * @author caodinh
 */
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

}