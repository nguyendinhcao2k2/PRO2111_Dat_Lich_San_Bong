package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.core.staff.model.request.PhuPhiHoaDonRequest;
import com.example.pro2111_dat_lich_san_bong.entity.DichVuSanBong;
import com.example.pro2111_dat_lich_san_bong.enumstatus.LoaiHinhThanhToan;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LichSuHoaDonSanCaStaffReponse {
    private String id;

    private LocalDate ngayDenSan;

    private Time thoiGianCheckIn;

    private Double tienSan;

    private Double tongTienHoaDonSanCa;

    private String idHoaDon;

    private Integer trangThai;

    private Timestamp ngayThanhToan;

    private Integer countLich = 0;

    private Double tienCocThua = 0.0;

    private LoaiHinhThanhToan HinhThucThanhToan;

    private List<LichSuDichVuSuDungStaffReponse> dichVuSuDungStaffReponses;
    private List<PhuPhiHoaDonRequest> phuPhiHoaDonReponse;

    public LichSuHoaDonSanCaStaffReponse(String id, LocalDate ngayDenSan, Time thoiGianCheckIn, Double tienSan, Double tongTienHoaDonSanCa, String idHoaDon, Integer trangThai, Timestamp ngayThanhToan, Integer countLich, Double tienCocThua, LoaiHinhThanhToan hinhThucThanhToan) {
        this.id = id;
        this.ngayDenSan = ngayDenSan;
        this.thoiGianCheckIn = thoiGianCheckIn;
        this.tienSan = tienSan;
        this.tongTienHoaDonSanCa = tongTienHoaDonSanCa;
        this.idHoaDon = idHoaDon;
        this.trangThai = trangThai;
        this.ngayThanhToan = ngayThanhToan;
        this.countLich = countLich;
        this.tienCocThua = tienCocThua;
        HinhThucThanhToan = hinhThucThanhToan;
    }
}
