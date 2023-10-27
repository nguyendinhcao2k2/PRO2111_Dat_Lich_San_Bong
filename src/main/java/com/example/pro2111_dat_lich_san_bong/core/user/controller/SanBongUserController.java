package com.example.pro2111_dat_lich_san_bong.core.user.controller;

/**
 * @author thepvph20110
 */

import com.example.pro2111_dat_lich_san_bong.core.common.base.BaseController;
import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.user.model.request.SanCaUserRequest;
import com.example.pro2111_dat_lich_san_bong.core.user.model.response.SanBongUserResponse;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanBongUserService;
import com.example.pro2111_dat_lich_san_bong.core.user.service.SanCaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/vi/user/san-bong")
public class SanBongUserController extends BaseController {

    @Autowired
    private SanBongUserService sanBongUserService;

    @Autowired
    private SanCaUserService sanCaUserService;

    private SimpleDateFormat format= new SimpleDateFormat("dd/MM/YYYY");

    @GetMapping("/page-san-bong")
    public String getAllSanBong(Model model, Pageable pageable){
       try {
           PageableObject<SanBongUserResponse> listPage = sanBongUserService.getAllSanBong(pageable);
           SanCaUserRequest request = new SanCaUserRequest(format.format(new Date()));
           model.addAttribute("pageSanBong",listPage);
       }catch (Exception e){
           e.printStackTrace();
       }
        return "user/book-soccer-field";
    }

}
