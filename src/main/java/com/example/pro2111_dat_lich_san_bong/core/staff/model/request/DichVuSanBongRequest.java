package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class DichVuSanBongRequest {
    private String idHoaDonSanCa;
    private String idNuocUong;
    private String idDoThue;
    private String imageNuocUong;
    private String imageDoThue;
    private String tenNuocUong;
    private String tenDoThue;
    private Double giaNuocUong;
    private Double giaDoThue;
    private Double tongTien;
    private Long soLuongDoThue;
    private Long soLuongNuocUong;
    private Integer soLuongDoThueMax;
    private Integer soLuongNuocUongMax;


}
