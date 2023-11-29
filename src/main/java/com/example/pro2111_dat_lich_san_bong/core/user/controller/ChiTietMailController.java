package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.schedule.model.response.HoaDonSendMailResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonSanCaUserRepository;
import com.example.pro2111_dat_lich_san_bong.core.user.repository.HoaDonUserRepository;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonSanCaReponsitory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
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
    public String detailHoaDon(String idHoaDonSanCa, Model model){
        HoaDonSendMailResponse response = reponsitory.getDetailHoaDon(idHoaDonSanCa);
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        model.addAttribute("item",response);
        return "utill/chi-tiet-phieu-dat";
    }

}
