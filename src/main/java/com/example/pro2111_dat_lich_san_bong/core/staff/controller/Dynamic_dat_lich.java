package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.LoaiSanStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/api/v1/staff")
public class Dynamic_dat_lich {

    @Autowired
    private LoaiSanStaffService loaiSanStaffService;

    @GetMapping("/dat-lich-dynamic")
    public String datLich(Model model){

        List<LoaiSan> loaiSanList = loaiSanStaffService.getAllLoaiSan();
        model.addAttribute("listLoaiSan",loaiSanList);
        return "staff/dynamic_content";
    }
}
