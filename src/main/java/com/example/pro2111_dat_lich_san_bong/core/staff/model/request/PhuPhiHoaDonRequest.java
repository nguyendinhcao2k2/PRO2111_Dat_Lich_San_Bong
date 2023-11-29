package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PhuPhiHoaDonRequest {
    private String idPhuPhiHoaDon;
    private String idHoaDonSanCa;
    private String idPhuPhi;
    private String tenPhuPhi;
    private Double giaPhuPhi;
    private Integer trangThai;
}
