package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LoaiSanUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SYSParamUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SYSParamCodeConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author thepvph20110
 */
@RequestMapping("/api/v1/user/trang-chu")
@Controller
public class TrangChuUserController extends BaseController {

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private CaUserService caUserService;

    @Autowired
    private LoaiSanUserService loaiSanUserService;

    @Autowired
    private SanBongUserService sanBongUserService;

    @Autowired
    private SYSParamUserService sysParamUserService;

    @GetMapping("/danh-sach-lich-dat")
    public String getAllListLichDat(Model model) {
       List<LoaiSan> loaiSanList = loaiSanUserService.getAllLoaiSan();
       String thamSoTienCoc = sysParamUserService.findSysParamByCode(SYSParamCodeConstant.PHAN_TRAM_GIA_TIEN_COC).getValue();
       String thoiGianDuocDatLich= sysParamUserService.findSysParamByCode(SYSParamCodeConstant.THOI_GIAN_CHO_PHEP_DAT_LICH).getValue();
       model.addAttribute("listLoaiSan",loaiSanList);
       model.addAttribute("thamSoTienCoc",thamSoTienCoc);
        model.addAttribute("thoiGianDuocDatLich",thoiGianDuocDatLich);
        return "/user/dat-lich";
    }
}
