package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.model.request.HoaDonSanCaViewRequest;
import com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry.HoaDonSanCaAdminRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.HoaDonSanCaStaffRepository;
import com.example.pro2111_dat_lich_san_bong.repository.DichVuRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import com.example.pro2111_dat_lich_san_bong.repository.SanCaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin("/*")
@Controller
@RequestMapping("/api/v1/staff/thanh-toan-hoa-don")
public class ThanhToanHoaDonStaffController {
    @Autowired
    private HoaDonSanCaStaffRepository hoaDonSanCaStaffRepository;
    @Autowired
    private SanCaRepository sanCaRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private DichVuRepository dichVuRepository;

    @GetMapping("/page-hoa-don")
    public String viewThanhToanHoaDon(Model model, @RequestParam(defaultValue = "1") int page) {
//        showHoaDonSanCa(model, page);
        return "staff/danh-sach-hoa-don";
    }

    private Page<HoaDonSanCaViewRequest> showHoaDonSanCa(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<HoaDonSanCaViewRequest> pageHoaDonSanCaViewRequest;
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, 10);
        pageHoaDonSanCaViewRequest = hoaDonSanCaStaffRepository.findHoaDonSanCa(pageable);
        model.addAttribute("pageHoaDonSanCaViewRequest", pageHoaDonSanCaViewRequest);
        return pageHoaDonSanCaViewRequest;
    }

//    @GetMapping("/{id}")
//    public String showThanhToanHoaDon(Model model, @PathVariable(name = "id") String id, @RequestParam(defaultValue = "1") int page) {
//        System.out.println(id);
//        showHoaDonSanCa(model, page);
//        HoaDonSanCaViewRequest hoaDonSanCaViewRequest = hoaDonSanCaAdminRepository.findHoaDonSanCaById(id);
//        System.out.println(hoaDonSanCaViewRequest);
//        model.addAttribute("hoaDonSanCaViewRequest", hoaDonSanCaViewRequest);
//        return "admin/ql-hoa-don-admin";
//    }

}
