package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

import com.example.pro2111_dat_lich_san_bong.enumstatus.TrangThaiGiaoCa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GiaoCaStaffResponse {

    private String id;
    private Timestamp thoiGianNhanCa;
    private Timestamp thoiGianHienTai;
    private AccountResponse accountResponse;
    private double tienBanDau;
    private Integer tongHoaDonDaThanhToan;
    private Integer tongHoaDonChuaThanhToan;
    private double tienPhatSinh;
    private String ghiChuPhatSinh;
    private List<AccountResponse> accountResponseList;
    private double tongTienMatTrongCa;
    private double tongTienThuTrongCa;
    private double tongTientThanhToanBangTienMat;
    private double tongTientThanhToanBangChuyenKhoan;

}
