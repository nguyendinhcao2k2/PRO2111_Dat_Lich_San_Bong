package com.example.pro2111_dat_lich_san_bong.core.admin.controller;

import com.example.pro2111_dat_lich_san_bong.core.admin.serviver.AccountAdminService;
import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author thepvph20110
 */
@RestController
@RequestMapping("/account")
public class AccountAdminRestController extends BaseController {
    @Autowired
    private AccountAdminService accountAdminService ;

    @GetMapping("/list-all")
    public List getAll(){
        String userId= session.getUserId();
        return accountAdminService.getList();
    }
}
