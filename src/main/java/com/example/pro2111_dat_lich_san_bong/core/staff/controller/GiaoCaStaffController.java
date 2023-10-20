package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiHoaDonSanCa;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.currency.CurrencyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("api/v1/staff/giao-ca/display")
public class GiaoCaStaffController {

    @Autowired
    private CommonSession commonSession;
    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;
    @Autowired
    private IAccountStaffService accountStaffService;
    @Autowired
    private CurrencyConfig currencyConfig;

    @GetMapping()
    public String giaoCa(Model model) {
        return "staff/giao-ca";
    }

    @GetMapping("/nhan-ca")
    public String viewKhoiTaoCa() {
        return "staff/nhan-ca";
    }
}
