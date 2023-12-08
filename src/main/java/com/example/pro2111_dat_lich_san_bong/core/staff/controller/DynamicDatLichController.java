package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.LoaiSanStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
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
public class DynamicDatLichController {

    @Autowired
    private LoaiSanStaffService loaiSanStaffService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @GetMapping("/dat-lich-dynamic")
    public String datLich(Model model){

        List<LoaiSan> loaiSanList = loaiSanStaffService.getAllLoaiSan();
        String thamSoTienCoc = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC).getValue();
        String thoiGianDuocDatLich= sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_CHO_PHEP_DAT_LICH).getValue();
        model.addAttribute("listLoaiSan",loaiSanList);
        model.addAttribute("thamSoTienCoc",thamSoTienCoc);
        model.addAttribute("thoiGianDuocDatLich",thoiGianDuocDatLich);
        return "staff/dynamic_content";
    }
}
