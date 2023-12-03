package com.example.pro2111_dat_lich_san_bong.core.staff.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.EventStaffRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.ISanCaStaffService;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanCaUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/api/vi/staff")
public class DatLichStaffRestController extends BaseController {

    @Autowired
    private ISanCaStaffService sanCaStaffService;

    @GetMapping("/all-event")
    public List<SanCaUserResponse> getAllEvent(EventStaffRequest request){
        return sanCaStaffService.getAllSanCa(request);
    }
}
