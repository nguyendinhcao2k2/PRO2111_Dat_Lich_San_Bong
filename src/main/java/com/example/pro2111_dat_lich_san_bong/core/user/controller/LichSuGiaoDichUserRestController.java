package com.example.pro2111_dat_lich_san_bong.core.user.controller;

import com.example.pro2111_dat_lich_san_bong.core.common.base.PageableObject;
import com.example.pro2111_dat_lich_san_bong.core.common.session.CommonSession;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuDichVuSuDungStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonSanCaStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.response.LichSuHoaDonStaffReponse;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.*;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiPhuPhiHoaDon;
import com.example.pro2111_dat_lich_san_bong.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class LichSuGiaoDichUserRestController {

    @Autowired
    private HoaDonStaffService hoaDonStaffService;

    @Autowired
    private IHoaDonSanCaStaffService hoaDonSanCaStaffService;

    @Autowired
    private IDichVuSanBongStaffService dichVuSanBongStaffService;

    @Autowired
    private IDoThueStaffService doThueStaffService;

    @Autowired
    private INuocUongStaffService nuocUongStaffService;

    @Autowired
    private IPhuPhiHoaDonStaffService phuPhiHoaDonStaffService;

    @Autowired
    private CommonSession commonSession;


    @GetMapping("/lich-su-giao-dich")
    public BaseResponse<?> findAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        try {
            Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
            Page<LichSuHoaDonStaffReponse> suHoaDonStaffReponseList = hoaDonStaffService.findAllDataHoaDonAndHoaDonSanCaUser(pageable, commonSession.getUserId());
            for (LichSuHoaDonStaffReponse items : suHoaDonStaffReponseList.getContent()) {
                List<LichSuHoaDonSanCaStaffReponse> lichSuHoaDonSanCaStaffReponses = hoaDonSanCaStaffService.findAllLichSuHoaDonSanCaTheoIdHD(items.getId());

                for (LichSuHoaDonSanCaStaffReponse ele : lichSuHoaDonSanCaStaffReponses) {
//                    dich vu su dung
                    List<DichVuSanBong> dichVuSuDungStaffReponses = dichVuSanBongStaffService.findAllByLichSuSuDungDichVu(ele.getId());

                    List<LichSuDichVuSuDungStaffReponse> lichSuDichVu = new ArrayList<>();

                    for (DichVuSanBong dichVuSanBong : dichVuSuDungStaffReponses) {
                        if (dichVuSanBong.getIdNuocUong() != null) {
                            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(dichVuSanBong.getIdNuocUong());
                            if (nuocUong != null) {
                                lichSuDichVu.add(new LichSuDichVuSuDungStaffReponse(nuocUong.getTenNuocUong(), dichVuSanBong.getSoLuongNuocUong()));
                            }
                        }
                        if (dichVuSanBong.getIdDoThue() != null) {
                            DoThue doThue = doThueStaffService.getOneDoThue(dichVuSanBong.getIdDoThue());
                            if (doThue != null) {
                                lichSuDichVu.add(new LichSuDichVuSuDungStaffReponse(doThue.getTenDoThue(), dichVuSanBong.getSoLuongDoThue()));
                            }
                        }
                    }
//                    dich vu su dung

                    List<PhuPhiHoaDonRequest> phuPhiHoaDonRequests = phuPhiHoaDonStaffService.getPhuPhiHoaDonByIdSanCa(ele.getId(), TrangThaiPhuPhiHoaDon.Da_Tra.ordinal());
                    ele.setPhuPhiHoaDonReponse(phuPhiHoaDonRequests);
                    ele.setDichVuSuDungStaffReponses(lichSuDichVu);
                }

                items.setHoaDonSanCaStaffList(lichSuHoaDonSanCaStaffReponses);
            }

            PageableObject pageableObject = new PageableObject(suHoaDonStaffReponseList);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        }

    }

    @GetMapping("/lich-su-giao-dich/search")
    public BaseResponse<?> searchLichSuGiaoDich(@RequestParam("page") Optional<Integer> page,
                                                @RequestParam("size") Optional<Integer> size,
                                                @RequestParam("ten") String ten,
                                                @RequestParam("sdt") String sdt) {
        try {
            Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(10));
            Page<LichSuHoaDonStaffReponse> suHoaDonStaffReponseList = hoaDonStaffService.searchLichSuHoaDonUser(pageable, commonSession.getUserId(), ten, sdt);
            for (LichSuHoaDonStaffReponse items : suHoaDonStaffReponseList.getContent()) {
                List<LichSuHoaDonSanCaStaffReponse> lichSuHoaDonSanCaStaffReponses = hoaDonSanCaStaffService.findAllLichSuHoaDonSanCaTheoIdHD(items.getId());

                for (LichSuHoaDonSanCaStaffReponse ele : lichSuHoaDonSanCaStaffReponses) {
//                    dich vu su dung
                    List<DichVuSanBong> dichVuSuDungStaffReponses = dichVuSanBongStaffService.findAllByLichSuSuDungDichVu(ele.getId());

                    List<LichSuDichVuSuDungStaffReponse> lichSuDichVu = new ArrayList<>();

                    for (DichVuSanBong dichVuSanBong : dichVuSuDungStaffReponses) {
                        if (dichVuSanBong.getIdNuocUong() != null) {
                            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(dichVuSanBong.getIdNuocUong());
                            if (nuocUong != null) {
                                lichSuDichVu.add(new LichSuDichVuSuDungStaffReponse(nuocUong.getTenNuocUong(), dichVuSanBong.getSoLuongNuocUong()));
                            }
                        }
                        if (dichVuSanBong.getIdDoThue() != null) {
                            DoThue doThue = doThueStaffService.getOneDoThue(dichVuSanBong.getIdDoThue());
                            if (doThue != null) {
                                lichSuDichVu.add(new LichSuDichVuSuDungStaffReponse(doThue.getTenDoThue(), dichVuSanBong.getSoLuongDoThue()));
                            }
                        }
                    }
//                    dich vu su dung

                    List<PhuPhiHoaDonRequest> phuPhiHoaDonRequests = phuPhiHoaDonStaffService.getPhuPhiHoaDonByIdSanCa(ele.getId(), TrangThaiPhuPhiHoaDon.Da_Tra.ordinal());
                    ele.setPhuPhiHoaDonReponse(phuPhiHoaDonRequests);
                    ele.setDichVuSuDungStaffReponses(lichSuDichVu);
                }

                items.setHoaDonSanCaStaffList(lichSuHoaDonSanCaStaffReponses);
            }

            PageableObject pageableObject = new PageableObject(suHoaDonStaffReponseList);
            return new BaseResponse<>(HttpStatus.OK, pageableObject);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Invalid");
        }

    }
}
