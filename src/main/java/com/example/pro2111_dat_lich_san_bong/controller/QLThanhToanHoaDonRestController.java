package com.example.pro2111_dat_lich_san_bong.controller;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.entity.PhieuDatLich;
import com.example.pro2111_dat_lich_san_bong.entity.ViTien;
import com.example.pro2111_dat_lich_san_bong.model.request.HoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.service.AccountService;
import com.example.pro2111_dat_lich_san_bong.service.HoaDonService;
import com.example.pro2111_dat_lich_san_bong.service.PhieuDatLichService;
import com.example.pro2111_dat_lich_san_bong.service.ViTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api-v1-admin/quan-ly-hoa-don-thanh-toan")
public class QLThanhToanHoaDonRestController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private PhieuDatLichService phieuDatLichService;
    @Autowired
    private AccountService accountService;
    private List<HoaDon> listHoaDons = new ArrayList<>();
    private List<HoaDonRequest> listHoaDonsRequest = new ArrayList<>();


//    @GetMapping("/all")
//    public ResponseEntity<List<HoaDonRequest>> getAllHoaDons() {
//        listHoaDons = hoaDonService.getAllHoaDons();
//        for (HoaDon hoaDon : listHoaDons) {
//            PhieuDatLich phieuDatLich = phieuDatLichService.getOnePhieuDatLich(hoaDon.getIdPhieuDatLich());
//            Account account = accountService.getOneAccount(phieuDatLich.get)
//            listHoaDonsRequest.add(new HoaDonRequest());
//        }
//        return ResponseEntity.ok(listHoaDonsRequest);
//    }
}
