package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.CaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.CaUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thepvph20110
 */

@RestController
@RequestMapping("/api/v1/user/san-ca")
public class SanCaUserRestController {

    @Autowired
    private SanCaUserService sanCaUserService;

    @Autowired
    private CaUserService caUserService;

    @GetMapping("/check-san-ca")
    public BaseResponse<CaUserResponse> checkSanCa(String idLoaiSan, String idCa, String ngayDat){
        int trangThaiSanCa =sanCaUserService.checkSanCa(idLoaiSan,idCa,ngayDat);
        CaUserResponse response = caUserService.getCaByIdCa(idCa,idLoaiSan);
        if(trangThaiSanCa == 0){
            return new BaseResponse<>(HttpStatus.OK,response);
        }else {
            return new BaseResponse<>(HttpStatus.NOT_EXTENDED,response);
        }
    }

    @PostMapping("/check-list-san-ca")
    private List CheckListSanCa(@RequestBody List<CheckSanCaUserRequest> requests){
        return  sanCaUserService.checkListSanCa(requests);
    }

}
