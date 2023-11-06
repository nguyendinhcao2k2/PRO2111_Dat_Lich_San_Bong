package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HoaDonUserRequest {
    private String id;
    private String ngayDaCu;
    private String caCu;
    private String loaiSanCu;
    private String ngayNew;
    private String caNew;
    private String loaiSanNew;
    private String giaNew;
}
