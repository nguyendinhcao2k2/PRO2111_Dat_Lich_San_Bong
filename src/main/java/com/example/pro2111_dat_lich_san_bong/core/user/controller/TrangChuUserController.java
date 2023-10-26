package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.LoaiSanUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
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

    @GetMapping("/danh-sach-lich-dat")
    public String getAllListLichDat(Model model) {
       Integer countNumber=  sanBongUserService.countSanBong();
       List<LoaiSan> loaiSanList = loaiSanUserService.getAllLoaiSan();
       model.addAttribute("countNumber",countNumber);
       model.addAttribute("listLoaiSan",loaiSanList);
        return "/user/dat-lich";
    }
}
