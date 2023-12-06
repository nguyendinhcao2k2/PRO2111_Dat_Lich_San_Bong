package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThongKeAdminReponse {

    private Double tongDoanhThu;
    private Integer tongSanDatOnline;
    private Integer tongSoLuotSanDatNho;
    private Integer tongSoLuotDa;
    private Integer tongSoLuotChuyenLich;
    private Integer tongSoHuyLich;
    private Double tongSoTienMat;
    private Double tongSoTienChuyenKhoan;
    private Double tongTienPhatSinhKhiGiaoCa;


}
