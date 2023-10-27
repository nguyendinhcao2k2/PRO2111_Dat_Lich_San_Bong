package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.EventRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/vi/user")
public class TrangChuUserRestController extends BaseController {

    @Autowired
    private SanCaUserService sanCaUserService;

    @GetMapping("/all-event")
    public List<SanCaUserResponse> getAllEvent(EventRequest request){

        String userId= session.getUserId();
        request.setIdAcount(userId);
        return sanCaUserService.getAllSanCa(request);
    }
}
