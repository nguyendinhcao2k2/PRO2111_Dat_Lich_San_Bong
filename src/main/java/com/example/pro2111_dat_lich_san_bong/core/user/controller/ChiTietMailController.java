package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonUserRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;

/**
 * @author thepvph20110
 */
@Controller
@RequestMapping("/chi-tiet")
public class ChiTietMailController {

    @Autowired
    private HoaDonSanCaUserRepository reponsitory;

    @GetMapping("/hoa-don")
    public String detailHoaDon(String idHoaDonSanCa){
        HoaDonSendMailResponse response = reponsitory.getDetialHoaDon(idHoaDonSanCa);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter formatterNgayDa = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "detail";
    }
}
