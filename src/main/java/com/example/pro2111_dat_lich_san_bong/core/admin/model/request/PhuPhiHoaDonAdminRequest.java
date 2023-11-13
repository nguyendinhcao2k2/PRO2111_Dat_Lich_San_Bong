package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuPhiHoaDonAdminRequest {

    private String id;

    private String idHoaDonSanCa;

    private String idPhuPhi;

    private Timestamp thoiGianTao;

    private Integer trangThai;
}
