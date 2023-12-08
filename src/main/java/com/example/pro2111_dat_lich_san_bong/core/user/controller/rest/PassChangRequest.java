package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassChangRequest {

    private String passCurrency;
    private String passNew;
}
