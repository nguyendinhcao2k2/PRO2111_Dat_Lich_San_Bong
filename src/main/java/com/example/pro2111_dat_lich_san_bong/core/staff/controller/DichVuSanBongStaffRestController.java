package com.example.pro2111_dat_lich_san_bong.core.staff.controller;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DichVuSanBongRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.DoThueNuocUongDichVuRequest;
import com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory.DichVuSanBongStaffRepository;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDichVuSanBongStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.IDoThueStaffService;
import com.example.pro2111_dat_lich_san_bong.core.staff.service.INuocUongStaffService;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.entity.DoThue;
import com.example.pro2111_dat_lich_san_bong.entity.NuocUong;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/staff/dich-vu-san-bong")
public class DichVuSanBongStaffRestController {
    @Autowired
    private IDichVuSanBongStaffService dichVuSanBongStaffService;
    @Autowired
    private IDoThueStaffService doThueStaffService;
    @Autowired
    private INuocUongStaffService nuocUongStaffService;

    @Autowired
    private DichVuSanBongStaffRepository dichVuSanBongStaffRepository;
    private List<DichVuSanBong> listDichVuSanBongs = new ArrayList<>();
    private List<DichVuSanBongRequest> listDichVuSanBongRequests = new ArrayList<>();
    private List<DoThueNuocUongDichVuRequest> listDoThueNuocUongDichVuRequest = new ArrayList<>();


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<List<DichVuSanBong>> getAllDichVuSanBongs(@PathVariable(name = "id") String idHoaDonSanCa) {
        listDichVuSanBongs.clear();
        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    @GetMapping("/dich-vu-thanh-toan/{id}")
    public ResponseEntity<List<DichVuSanBongRequest>> getAllDichVuSanBongRequests(@PathVariable("id") String id) {
        listDichVuSanBongRequests.clear();
        listDichVuSanBongRequests = dichVuSanBongStaffService.dichVuSanBongSuDungByHoaDonSanCas(id, 0);
        return ResponseEntity.ok(listDichVuSanBongRequests);
    }

    @GetMapping("/danh-sach-dich-vu")
    public ResponseEntity<List<DoThueNuocUongDichVuRequest>> getAllDoThueNuocUongDichVuRequest() {
        listDoThueNuocUongDichVuRequest.clear();
        listDoThueNuocUongDichVuRequest = dichVuSanBongStaffService.getAllDoThueNuocUongDichVuRequest(0);
        return ResponseEntity.ok(listDoThueNuocUongDichVuRequest);
    }

    @PostMapping("/them-dich-vu-hoa-don/{id}")
    public ResponseEntity<List<DichVuSanBong>> themDichVuVaoHoaDon(@PathVariable(name = "id") String idHoaDonSanCa, @RequestBody DoThueNuocUongDichVuRequest doThueNuocUongDichVuRequest) {
        int soLuongDoThueNuocUong = doThueNuocUongDichVuRequest.getSoLuong();
        String idDoThueNuocUong = doThueNuocUongDichVuRequest.getId();
        int trangThaiDoThueNuocUong = doThueNuocUongDichVuRequest.getTrangThai();
        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);
        System.out.println(listDichVuSanBongs.size());
        if (listDichVuSanBongs == null || listDichVuSanBongs.size() == 0) {
            DichVuSanBong dichVuSanBong = new DichVuSanBong();
            if (trangThaiDoThueNuocUong == 1) {
                NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setIdNuocUong(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setSoLuongNuocUong(doThueNuocUongDichVuRequest.getSoLuong());
                dichVuSanBong.setDonGia(nuocUong.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
            } else if (trangThaiDoThueNuocUong == 2) {
                DoThue doThue = doThueStaffService.getOneDoThue(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setIdDoThue(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setSoLuongDoThue(doThueNuocUongDichVuRequest.getSoLuong());
                dichVuSanBong.setDonGia(doThue.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
            }
            dichVuSanBong.setId(null);
            dichVuSanBong.setIdHoaDonSanCa(idHoaDonSanCa);
            dichVuSanBong.setTrangThai(0);
            dichVuSanBongStaffRepository.save(dichVuSanBong);
        } else {
            listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);
            for (int i = 0; i < listDichVuSanBongs.size(); i++) {
                int count = 0;
                DichVuSanBong dichVuSanBongIndex = listDichVuSanBongs.get(i);
                if (trangThaiDoThueNuocUong == 1) {
                    if (dichVuSanBongIndex.getIdNuocUong() == null) {
                        count++;
                        DichVuSanBong dichVuSanBongNull = dichVuSanBongStaffRepository.getById(dichVuSanBongIndex.getId());
                        dichVuSanBongNull.setIdNuocUong(idDoThueNuocUong);
                        dichVuSanBongNull.setSoLuongNuocUong(soLuongDoThueNuocUong);
                        dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                        dichVuSanBongNull.setTrangThai(0);
                        dichVuSanBongStaffRepository.save(dichVuSanBongNull);
                        break;
                    } else if (dichVuSanBongIndex.getIdNuocUong().equals(idDoThueNuocUong)) {
                        count++;
                        DichVuSanBong dichVuSanBongFind = dichVuSanBongStaffRepository.getById(dichVuSanBongIndex.getId());
                        int soLuongCu = dichVuSanBongIndex.getSoLuongNuocUong();
                        dichVuSanBongFind.setSoLuongNuocUong(soLuongCu + soLuongDoThueNuocUong);
                        dichVuSanBongStaffRepository.save(dichVuSanBongFind);
                        break;
                    } else {
                        if (count == 0 && i == listDichVuSanBongs.size() - 1) {
                            DichVuSanBong dichVuSanBongNull = new DichVuSanBong();
                            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setIdNuocUong(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setSoLuongNuocUong(doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setDonGia(nuocUong.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setId(null);
                            dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                            dichVuSanBongNull.setTrangThai(0);
                            dichVuSanBongStaffRepository.save(dichVuSanBongNull);
                            break;
                        }
                    }
                } else {
                    if (dichVuSanBongIndex.getIdDoThue() == null) {
                        count++;
                        DichVuSanBong dichVuSanBongUpdated = dichVuSanBongStaffRepository.getById(dichVuSanBongIndex.getId());
                        dichVuSanBongUpdated.setIdDoThue(idDoThueNuocUong);
                        dichVuSanBongUpdated.setSoLuongDoThue(soLuongDoThueNuocUong);
                        dichVuSanBongUpdated.setIdHoaDonSanCa(idHoaDonSanCa);
                        dichVuSanBongUpdated.setTrangThai(0);
                        dichVuSanBongStaffRepository.save(dichVuSanBongUpdated);
                        break;
                    } else if (dichVuSanBongIndex.getIdDoThue().equals(idDoThueNuocUong)) {
                        count++;
                        DichVuSanBong dichVuSanBongUpdated = dichVuSanBongStaffRepository.getById(dichVuSanBongIndex.getId());
                        int soLuongCu = dichVuSanBongIndex.getSoLuongDoThue();
                        dichVuSanBongUpdated.setSoLuongDoThue(soLuongCu + soLuongDoThueNuocUong);
                        dichVuSanBongStaffRepository.save(dichVuSanBongUpdated);
                        break;
                    } else {
                        if (count == 0 && i == listDichVuSanBongs.size() - 1) {
                            DichVuSanBong dichVuSanBongNull = new DichVuSanBong();
                            DoThue doThue = doThueStaffService.getOneDoThue(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setIdDoThue(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setSoLuongDoThue(doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setDonGia(doThue.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setId(null);
                            dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                            dichVuSanBongNull.setTrangThai(0);
                            dichVuSanBongStaffRepository.save(dichVuSanBongNull);
                        }
                    }
                }

            }
        }
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    @DeleteMapping("/xoa-dich-vu/{id}")
    public void deleteDichVu(@PathVariable(name = "id") String idDichVuSuDung) {
        // Your logic to delete the service based on the provided ID
        System.out.println("Deleting service with ID: " + idDichVuSuDung);
        // Perform deletion logic here
    }
}
