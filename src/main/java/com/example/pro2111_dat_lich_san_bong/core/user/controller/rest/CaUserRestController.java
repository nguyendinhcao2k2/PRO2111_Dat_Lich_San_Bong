package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thepvph20110
 */
@RequestMapping("/api/v1/user/ca")
@RestController
public class CaUserRestController {

    @Autowired
    private CaUserService caUserService;

    @GetMapping("/get-all-ca")
    public List getAllCaByIdLoaiSan(EventRequest request){
        String idLoaiSan = request.getIdLoaiSan();

        return caUserService.getAllCaByIdLoaiSan(idLoaiSan);
    }
}