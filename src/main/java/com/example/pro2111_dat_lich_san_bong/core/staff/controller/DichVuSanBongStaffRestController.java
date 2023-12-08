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
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDichVu;
import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiDoThue;
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

import java.lang.reflect.InvocationTargetException;
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

    private DoThue getOneDoThue(String idDoThue) {
        return doThueStaffService.getOneDoThue(idDoThue);
    }

    private NuocUong getOneNuocUong(String idNuocUong) {
        return nuocUongStaffService.getOneNuocUong(idNuocUong);
    }

    private DichVuSanBong getOneDichVuSanBong(String idDichVuSanBong) {
        return dichVuSanBongStaffRepository.getById(idDichVuSanBong);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<List<DichVuSanBong>> getAllDichVuSanBongs(@PathVariable(name = "id") String idHoaDonSanCa) {
        listDichVuSanBongs.clear();
        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, TrangThaiDichVu.Dang_Su_Dung.ordinal());
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    @GetMapping("/dich-vu-thanh-toan/{id}")
    public ResponseEntity<List<DichVuSanBongRequest>> getAllDichVuSanBongRequests(@PathVariable("id") String id) {
        listDichVuSanBongRequests.clear();
        listDichVuSanBongRequests = dichVuSanBongStaffService.dichVuSanBongSuDungByHoaDonSanCas(id, TrangThaiDichVu.Dang_Su_Dung.ordinal());
        return ResponseEntity.ok(listDichVuSanBongRequests);
    }

    @GetMapping("/danh-sach-dich-vu")
    public ResponseEntity<List<DoThueNuocUongDichVuRequest>> getAllDoThueNuocUongDichVuRequest() {
        listDoThueNuocUongDichVuRequest.clear();
        listDoThueNuocUongDichVuRequest = dichVuSanBongStaffService.getAllDoThueNuocUongDichVuRequest(TrangThaiDoThue.Con_Hang.ordinal());
        return ResponseEntity.ok(listDoThueNuocUongDichVuRequest);
    }

    @PostMapping("/them-dich-vu-hoa-don/{id}")
    public ResponseEntity<List<DichVuSanBong>> themDichVuVaoHoaDon(@PathVariable(name = "id") String idHoaDonSanCa, @RequestBody DoThueNuocUongDichVuRequest doThueNuocUongDichVuRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        int soLuongDoThueNuocUong = doThueNuocUongDichVuRequest.getSoLuong();
        String idDoThueNuocUong = doThueNuocUongDichVuRequest.getId();
        int trangThaiDoThueNuocUong = doThueNuocUongDichVuRequest.getTrangThai();
        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, TrangThaiDichVu.Dang_Su_Dung.ordinal());
        System.out.println(listDichVuSanBongs.size());
        if (listDichVuSanBongs == null || listDichVuSanBongs.size() == 0) {
            DichVuSanBong dichVuSanBong = new DichVuSanBong();
            if (trangThaiDoThueNuocUong == 1) {
                NuocUong nuocUong = getOneNuocUong(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setIdNuocUong(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setSoLuongNuocUong(doThueNuocUongDichVuRequest.getSoLuong());
                updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 1);
                dichVuSanBong.setDonGia(nuocUong.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
            } else if (trangThaiDoThueNuocUong == 2) {
                DoThue doThue = getOneDoThue(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setIdDoThue(doThueNuocUongDichVuRequest.getId());
                dichVuSanBong.setSoLuongDoThue(doThueNuocUongDichVuRequest.getSoLuong());
                updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 2);
                dichVuSanBong.setDonGia(doThue.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
            }
            dichVuSanBong.setId(null);
            dichVuSanBong.setIdHoaDonSanCa(idHoaDonSanCa);
            dichVuSanBong.setTrangThai(0);
            dichVuSanBongStaffRepository.save(dichVuSanBong);
        } else {
            listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, TrangThaiDichVu.Dang_Su_Dung.ordinal());
            for (int i = 0; i < listDichVuSanBongs.size(); i++) {
                int count = 0;
                DichVuSanBong dichVuSanBongIndex = listDichVuSanBongs.get(i);
                if (trangThaiDoThueNuocUong == 1) {
                    if (dichVuSanBongIndex.getIdNuocUong() == null) {
                        count++;
                        NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(idDoThueNuocUong);
                        DichVuSanBong dichVuSanBongNull = getOneDichVuSanBong(dichVuSanBongIndex.getId());
                        dichVuSanBongNull.setIdNuocUong(idDoThueNuocUong);
                        dichVuSanBongNull.setSoLuongNuocUong(soLuongDoThueNuocUong);
                        dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                        dichVuSanBongNull.setTrangThai(0);
                        dichVuSanBongNull.setDonGia(soLuongDoThueNuocUong * nuocUong.getDonGia());
                        updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 1);
                        updateDonGiaDichVuTuDong(dichVuSanBongNull);
                        dichVuSanBongStaffRepository.save(dichVuSanBongNull);

                        break;
                    } else if (dichVuSanBongIndex.getIdNuocUong().equals(idDoThueNuocUong)) {
                        count++;
                        double donGia = 0;
                        DichVuSanBong dichVuSanBongFind = getOneDichVuSanBong(dichVuSanBongIndex.getId());
                        int soLuongCu = dichVuSanBongIndex.getSoLuongNuocUong();
                        dichVuSanBongFind.setSoLuongNuocUong(soLuongCu + soLuongDoThueNuocUong);
                        if (dichVuSanBongIndex.getIdDoThue() != null) {
                            DoThue doThue = doThueStaffService.getOneDoThue(dichVuSanBongFind.getIdDoThue());
                            int soLuongDoThue = dichVuSanBongFind.getSoLuongDoThue();
                            donGia = (soLuongDoThue * doThue.getDonGia());
                        } else {
                            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(idDoThueNuocUong);
                            dichVuSanBongFind.setDonGia((soLuongCu + soLuongDoThueNuocUong) * nuocUong.getDonGia());
                        }
                        updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 1);
                        updateDonGiaDichVuTuDong(dichVuSanBongFind);
                        dichVuSanBongStaffRepository.save(dichVuSanBongFind);
                        break;
                    } else {
                        if (count == 0 && i == listDichVuSanBongs.size() - 1) {
                            DichVuSanBong dichVuSanBongNull = new DichVuSanBong();
                            NuocUong nuocUong = getOneNuocUong(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setIdNuocUong(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setSoLuongNuocUong(doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setDonGia(nuocUong.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setId(null);
                            dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                            dichVuSanBongNull.setTrangThai(0);
                            updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 1);
                            updateDonGiaDichVuTuDong(dichVuSanBongNull);
                            dichVuSanBongStaffRepository.save(dichVuSanBongNull);
                            break;
                        }
                    }
                } else {
                    listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);
                    if (dichVuSanBongIndex.getIdDoThue() == null) {
                        count++;
                        DichVuSanBong dichVuSanBongUpdated = getOneDichVuSanBong(dichVuSanBongIndex.getId());
                        dichVuSanBongUpdated.setIdDoThue(idDoThueNuocUong);
                        dichVuSanBongUpdated.setSoLuongDoThue(soLuongDoThueNuocUong);
                        dichVuSanBongUpdated.setIdHoaDonSanCa(idHoaDonSanCa);
                        dichVuSanBongUpdated.setTrangThai(0);
                        updateDonGiaDichVuTuDong(dichVuSanBongUpdated);
                        dichVuSanBongStaffRepository.save(dichVuSanBongUpdated);
                        updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 2);
                        break;
                    } else if (dichVuSanBongIndex.getIdDoThue().equals(idDoThueNuocUong)) {
                        count++;
                        DichVuSanBong dichVuSanBongUpdated = getOneDichVuSanBong(dichVuSanBongIndex.getId());
                        int soLuongCu = dichVuSanBongIndex.getSoLuongDoThue();
                        dichVuSanBongUpdated.setSoLuongDoThue(soLuongCu + soLuongDoThueNuocUong);
                        updateDonGiaDichVuTuDong(dichVuSanBongUpdated);
                        dichVuSanBongStaffRepository.save(dichVuSanBongUpdated);
                        updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 2);
                        break;
                    } else {
                        if (count == 0 && i == listDichVuSanBongs.size() - 1) {
                            DichVuSanBong dichVuSanBongNull = new DichVuSanBong();
                            DoThue doThue = getOneDoThue(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setIdDoThue(doThueNuocUongDichVuRequest.getId());
                            dichVuSanBongNull.setSoLuongDoThue(doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setDonGia(doThue.getDonGia() * doThueNuocUongDichVuRequest.getSoLuong());
                            dichVuSanBongNull.setId(null);
                            dichVuSanBongNull.setIdHoaDonSanCa(idHoaDonSanCa);
                            dichVuSanBongNull.setTrangThai(0);
                            updateDonGiaDichVuTuDong(dichVuSanBongNull);
                            dichVuSanBongStaffRepository.save(dichVuSanBongNull);
                            updateSoLuongDoThueNuocUong(idDoThueNuocUong, soLuongDoThueNuocUong, 2);
                        }
                    }
                }

            }
        }
        return ResponseEntity.ok(listDichVuSanBongs);
    }

    private String updateSoLuongDoThueNuocUong(String idDoThueNuocUong, int soluongTru, int trangThai) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (trangThai == 1) {
            NuocUong nuocUong = getOneNuocUong(idDoThueNuocUong);
            int soLuongBanDau = nuocUong.getSoLuong();
            nuocUong.setSoLuong(soLuongBanDau - soluongTru);
            nuocUongStaffService.save(nuocUong);
        } else if (trangThai == 2) {
            DoThue doThue = getOneDoThue(idDoThueNuocUong);
            int soLuongBanDau = doThue.getSoLuong();
            doThue.setSoLuong(soLuongBanDau - soluongTru);
            doThueStaffService.save(doThue);
        }
        return "OK";
    }

    @PostMapping("/xoa-dich-vu/{id}")
    public void deleteDichVu(@PathVariable(name = "id") String idHoaDonSanCa, @RequestBody DoThueNuocUongDichVuRequest doThueNuocUongDichVuRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);
        String idDichVuDoThue = doThueNuocUongDichVuRequest.getId();

        int trangThai = doThueNuocUongDichVuRequest.getTrangThai();

        int soLuongCongDon = doThueNuocUongDichVuRequest.getSoLuong();

        System.out.println(doThueNuocUongDichVuRequest.getId());

        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, 0);

        for (int i = 0; i < listDichVuSanBongs.size(); i++) {
            String idDichVuSanBong = listDichVuSanBongs.get(i).getId();
            DichVuSanBong dichVuSanBongUpdate = getOneDichVuSanBong(idDichVuSanBong);
            if (listDichVuSanBongs.get(i).getIdHoaDonSanCa().equals(idHoaDonSanCa)) {
                if (trangThai == 1) {
                    NuocUong nuocUong = getOneNuocUong(idDichVuDoThue);
                    if (listDichVuSanBongs.get(i).getIdDoThue() == null) {
                        if (listDichVuSanBongs.get(i).getIdNuocUong().equals(idDichVuDoThue)) {
                            dichVuSanBongStaffRepository.deleteById(idDichVuSanBong);
                            int soLuongCon = nuocUong.getSoLuong();
                            nuocUong.setSoLuong(soLuongCon + soLuongCongDon);
                            nuocUongStaffService.save(nuocUong);
                            break;
                        }
                    } else if (listDichVuSanBongs.get(i).getIdNuocUong() != null) {
                        if (listDichVuSanBongs.get(i).getIdNuocUong().equals(idDichVuDoThue)) {
                            dichVuSanBongUpdate.setIdNuocUong(null);
                            dichVuSanBongUpdate.setSoLuongNuocUong(null);
                            updateDonGiaDichVuTuDong(dichVuSanBongUpdate);
                            dichVuSanBongStaffRepository.save(dichVuSanBongUpdate);
                            int soLuongCon = nuocUong.getSoLuong();
                            nuocUong.setSoLuong(soLuongCon + soLuongCongDon);
                            nuocUongStaffService.save(nuocUong);
                            break;
                        }

                    }

                }
                if (trangThai == 2) {
                    DoThue doThue = doThueStaffService.getOneDoThue(idDichVuDoThue);
                    if (listDichVuSanBongs.get(i).getIdNuocUong() == null) {
                        if (listDichVuSanBongs.get(i).getIdDoThue().equals(idDichVuDoThue)) {
                            dichVuSanBongStaffRepository.deleteById(idDichVuSanBong);
                            int soLuongCon = doThue.getSoLuong();
                            doThue.setSoLuong(soLuongCon + soLuongCongDon);
                            doThueStaffService.save(doThue);
                            break;
                        }
                    } else {
                        if (listDichVuSanBongs.get(i).getIdDoThue() != null) {
                            if (listDichVuSanBongs.get(i).getIdDoThue().equals(idDichVuDoThue)) {
                                dichVuSanBongUpdate.setIdDoThue(null);
                                dichVuSanBongUpdate.setSoLuongDoThue(null);
                                updateDonGiaDichVuTuDong(dichVuSanBongUpdate);
                                dichVuSanBongStaffRepository.save(dichVuSanBongUpdate);
                                int soLuongCon = doThue.getSoLuong();
                                doThue.setSoLuong(soLuongCon + soLuongCongDon);
                                doThueStaffService.save(doThue);
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    @PostMapping("/cap-nhat-so-luong-dich-vu/{id}")
    public void updateSoLuongDichVu(@PathVariable(name = "id") String idHoaDonSanCa, @RequestBody DoThueNuocUongDichVuRequest doThueNuocUongDichVuRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        String idDichVuDoThue = doThueNuocUongDichVuRequest.getId();

        int trangThai = doThueNuocUongDichVuRequest.getTrangThai();

        int soLuongCapNhat = doThueNuocUongDichVuRequest.getSoLuong();

        listDichVuSanBongs = dichVuSanBongStaffService.findAllByIdHoaDonSanCaAndTrangThai(idHoaDonSanCa, TrangThaiDichVu.Dang_Su_Dung.ordinal());

        for (int i = 0; i < listDichVuSanBongs.size(); i++) {
            String idDichVuSanBong = listDichVuSanBongs.get(i).getId();
            DichVuSanBong dichVuSanBongUpdate = getOneDichVuSanBong(idDichVuSanBong);
            if (listDichVuSanBongs.get(i).getIdHoaDonSanCa().equals(idHoaDonSanCa)) {
                if (trangThai == 1) {
                    if (listDichVuSanBongs.get(i).getIdNuocUong() != null) {
                        NuocUong nuocUong = getOneNuocUong(idDichVuDoThue);
                        int soLuongBanDau = listDichVuSanBongs.get(i).getSoLuongNuocUong();
                        if (listDichVuSanBongs.get(i).getIdNuocUong().equals(idDichVuDoThue)) {
                            dichVuSanBongUpdate.setSoLuongNuocUong(soLuongCapNhat);
                            if (soLuongBanDau > soLuongCapNhat) {
                                int soLuongTraLai = soLuongBanDau - soLuongCapNhat;
                                nuocUong.setSoLuong(nuocUong.getSoLuong() + soLuongTraLai);
                            } else if (soLuongBanDau < soLuongCapNhat) {
                                int soLuongThemVao = soLuongCapNhat - soLuongBanDau;
                                nuocUong.setSoLuong(nuocUong.getSoLuong() - soLuongThemVao);
                            } else if (soLuongBanDau == soLuongCapNhat) {
                                nuocUong.setSoLuong(nuocUong.getSoLuong());
                            }
                            updateDonGiaDichVuTuDong(dichVuSanBongUpdate);
                            dichVuSanBongStaffRepository.save(dichVuSanBongUpdate);
                        }
                    }
                }
                if (trangThai == 2) {
                    if (listDichVuSanBongs.get(i).getIdDoThue() != null) {
                        DoThue doThue = getOneDoThue(idDichVuDoThue);
                        int soLuongBanDau = listDichVuSanBongs.get(i).getSoLuongDoThue();
                        if (listDichVuSanBongs.get(i).getIdDoThue().equals(idDichVuDoThue)) {
                            dichVuSanBongUpdate.setSoLuongDoThue(soLuongCapNhat);
                            if (soLuongBanDau > soLuongCapNhat) {
                                int soLuongTraLai = soLuongBanDau - soLuongCapNhat;
                                doThue.setSoLuong(doThue.getSoLuong() + soLuongTraLai);
                            } else if (soLuongBanDau < soLuongCapNhat) {
                                int soLuongThemVao = soLuongCapNhat - soLuongBanDau;
                                doThue.setSoLuong(doThue.getSoLuong() - soLuongThemVao);
                            } else if (soLuongBanDau == soLuongCapNhat) {
                                doThue.setSoLuong(doThue.getSoLuong());
                            }
                            updateDonGiaDichVuTuDong(dichVuSanBongUpdate);
                            dichVuSanBongStaffRepository.save(dichVuSanBongUpdate);
                        }
                    }
                }
            }
        }
    }

    public void updateDonGiaDichVuTuDong(DichVuSanBong dichVuSanBong) {
        String idNuocUong = dichVuSanBong.getIdNuocUong();
        String idDoThue = dichVuSanBong.getIdDoThue();
        int soLuongNuocUong = 0;
        int soLuongdoThue = 0;
        double donGiaDoThue = 0;
        double donGiaNuocUong = 0;
        if (idDoThue == null || idDoThue.isEmpty()) {
            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(idNuocUong);
            donGiaNuocUong = nuocUong.getDonGia();
            soLuongNuocUong = dichVuSanBong.getSoLuongNuocUong();
            double donGia =  (soLuongNuocUong * donGiaNuocUong);
            dichVuSanBong.setDonGia(donGia);
            dichVuSanBongStaffRepository.saveAndFlush(dichVuSanBong);
        } else if (idNuocUong == null || idNuocUong.isEmpty()) {
            DoThue doThue = doThueStaffService.getOneDoThue(idDoThue);
            soLuongdoThue = dichVuSanBong.getSoLuongDoThue();
            donGiaDoThue = doThue.getDonGia();
            double donGia = (soLuongdoThue * donGiaDoThue);
            dichVuSanBong.setDonGia(donGia);
            dichVuSanBongStaffRepository.saveAndFlush(dichVuSanBong);
        } else {
            NuocUong nuocUong = nuocUongStaffService.getOneNuocUong(idNuocUong);
            DoThue doThue = doThueStaffService.getOneDoThue(idDoThue);
            soLuongdoThue = dichVuSanBong.getSoLuongDoThue();
            soLuongNuocUong = dichVuSanBong.getSoLuongNuocUong();
            donGiaDoThue = doThue.getDonGia();
            donGiaNuocUong = nuocUong.getDonGia();
            double donGia = (soLuongdoThue * donGiaDoThue) + (soLuongNuocUong * donGiaNuocUong);
            dichVuSanBong.setDonGia(donGia);
            dichVuSanBongStaffRepository.saveAndFlush(dichVuSanBong);
        }
    }
}