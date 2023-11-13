package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.impl.ThanhToanSanCaStaffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/staff/thanh-toan")
public class ThanhToanHoaDonStaffController {
    @Autowired
    private ThanhToanSanCaStaffServiceImpl thanhToanStaffService;

    @GetMapping("/view-hoa-don")
    public String viewThanhToanHoaDon(Model model) {
        return "staff/danh-sach-hoa-don";
    }

    @GetMapping("/thanh-toan-hoa-don/{id}")
    public String getOneThanhToanHoaDon() {
        return "staff/thanh-toan-staff";
    }
}
