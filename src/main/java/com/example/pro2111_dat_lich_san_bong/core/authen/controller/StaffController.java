package com.example.pro2111_dat_lich_san_bong.core.authen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    @GetMapping("/all")
    List<Integer> get() {
        return Arrays.asList(1, 2, 31, 2);
    }
}
