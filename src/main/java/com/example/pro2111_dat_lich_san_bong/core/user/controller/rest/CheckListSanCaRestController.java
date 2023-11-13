package com.example.pro2111_dat_lich_san_bong.core.user.controller.rest;

import com.example.pro2111_dat_lich_san_bong.core.user.model.request.CheckSanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
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
@RequestMapping("/api/v1/user/san-ca")
public class CheckListSanCaRestController {

//    @Autowired
//    private SanCaUserService sanCaUserService;
//
//    @GetMapping("/check-list-san-ca")
//    private List CheckListSanCa(@RequestBody List<CheckSanCaUserRequest> requests){
//
//        return  sanCaUserService.checkListSanCa(requests);
//    }

}
