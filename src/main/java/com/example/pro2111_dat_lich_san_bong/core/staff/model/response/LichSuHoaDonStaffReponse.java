package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiLuatSan;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LichSuHoaDonStaffReponse {
    private String id;

    private LocalDateTime ngayTao;

    private String soDienThoaiNguoiDat;

    private Double tienCoc;

    private Double tongTien;

    private String email;

    private String tenNguoiDat;

    private Integer trangThai;

    private List<LichSuHoaDonSanCaStaffReponse> hoaDonSanCaStaffList;

    public LichSuHoaDonStaffReponse(String id, LocalDateTime ngayTao, String soDienThoaiNguoiDat, Double tienCoc, Double tongTien, String email, String tenNguoiDat, Integer trangThai) {
        this.id = id;
        this.ngayTao = ngayTao;
        this.soDienThoaiNguoiDat = soDienThoaiNguoiDat;
        this.tienCoc = tienCoc;
        this.tongTien = tongTien;
        this.email = email;
        this.tenNguoiDat = tenNguoiDat;
        this.trangThai = trangThai;
    }

    public LichSuHoaDonStaffReponse(String id, LocalDateTime ngayTao, String soDienThoaiNguoiDat, Double tienCoc, Double tongTien, String email, String tenNguoiDat, Integer trangThai, List<LichSuHoaDonSanCaStaffReponse> hoaDonSanCaStaffList) {
        this.id = id;
        this.ngayTao = ngayTao;
        this.soDienThoaiNguoiDat = soDienThoaiNguoiDat;
        this.tienCoc = tienCoc;
        this.tongTien = tongTien;
        this.email = email;
        this.tenNguoiDat = tenNguoiDat;
        this.trangThai = trangThai;
        this.hoaDonSanCaStaffList = hoaDonSanCaStaffList;
    }
}
