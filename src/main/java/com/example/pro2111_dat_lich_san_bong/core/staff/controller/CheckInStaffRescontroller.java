package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.service.HoaDonStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caodinh
 */
@RestController
@RequestMapping("/api/v1/staff")
public class CheckInStaffRescontroller {

    @Autowired
    private HoaDonStaffService hoaDonStaffService;


    @GetMapping("/list-check-in")
    public ResponseEntity<?> listHoaDon(@RequestParam("param") String param) {
        String newParam = "%" + param + "%";
        return new ResponseEntity<>(hoaDonStaffService.listCheckIn(newParam), HttpStatus.OK);
    }

    @GetMapping("/check-in/{param}")
    public ResponseEntity<?> checkIn(@PathVariable("param") String param) {
        return hoaDonStaffService.checkIn(param);
    }
}
