package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThongKeCaNamCaTrongNgayResponse {
    private List<ThongKeTheoNamAdminResponse> thongKeTheoNamAdminResponses;
    private List<ThongKeTheoCaAdminResponse> thongKeTheoCaAdminResponses;
    private List<ThongKeTheoCaAdminResponse> thongKeNgayHomNay;
}
