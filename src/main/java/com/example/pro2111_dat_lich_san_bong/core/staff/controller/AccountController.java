package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.AccountResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.GiaoCaResponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IAccountStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IGiaoCaStaffService;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/staff/account/display")
public class AccountController {
    @Autowired
    private IAccountStaffService accountStaffService;

    @Autowired
    private CommonSession commonSession;
    @Autowired
    private IGiaoCaStaffService giaoCaStaffService;

    @GetMapping("")
    public String signInAll(Model model) {
        try {
            List<GiaoCaResponse> giaoCaResponseAll = giaoCaStaffService.giaoCaList();
            if (giaoCaResponseAll == null) {
                return "staff/nhan-ca";
            } else {
                AccountResponse accountResponse = accountStaffService.findAccountById(commonSession.getUserId());
                GiaoCaResponse giaoCaResponseByStatus = giaoCaStaffService.findGiaoCaByTrangThai(TrangThaiGiaoCa.NHAN_CA);
                GiaoCaResponse giaoCaResponseOrderByKetCa = giaoCaStaffService.findFirstByOrderByThoiGianNhanCaDesc();
                if (giaoCaResponseByStatus != null) {
                    if (!giaoCaResponseByStatus.getIdNhanVienTrongCa().equals(accountResponse.getId())) {
                        model.addAttribute("thongBao", "Bạn không phải là nhân viên của ca làm việc này!");
                        return "authen/login-staff";
                    }
                    return "redirect:/api/v1/staff/view-dat-lich";
                } else {
                    if (!giaoCaResponseOrderByKetCa.getIdNhanVienCaTiepTheo().equals(accountResponse.getId())) {
                        model.addAttribute("thongBao", "Bạn không phải là nhân viên ca tiếp theo!");
                        return "authen/login-staff";
                    }
                    return "staff/nhan-ca";
                }
            }

        } catch (Exception e) {
            return "authen/login-staff";
        }

    }
}
