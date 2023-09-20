package com.example.pro2111_dat_lich_san_bong.model.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonRequest {
    private String tenTaiKhoan;
    private String tenKhachHang;
    private String soDienThoai;

    @NotNull(message = "Ngày thanh toán không được null")
    private Timestamp ngayThanhToan;

    @NotNull(message = "Đơn giá không được null")
    @Positive(message = "Đơn giá phải là số dương")
    private Double donGia;

    @NotNull(message = "Tổng tiền không được null")
    @Positive(message = "Tổng tiền phải là số dương")
    private Double tongTien;

    @Size(max = 255, message = "Ghi chú không được quá 255 ký tự")
    private String ghiChu;

    @NotNull(message = "Trạng thái không được null")
    private Integer trangThai;

}
