package com.example.pro2111_dat_lich_san_bong.core.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonUserResponse {
    private String id;
    private String tenCaHT;
    private String loaiSanHT;
    private LocalDateTime ngayDatHT;
    private Double tongTienHT;
}
