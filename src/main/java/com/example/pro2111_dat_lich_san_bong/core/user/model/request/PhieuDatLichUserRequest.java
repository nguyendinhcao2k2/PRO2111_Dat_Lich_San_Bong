package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author thepvph20110
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhieuDatLichUserRequest {

    private String hoVaTen;
    private String email;
    private String sdt;
    private String tongTienSan;
    private String idLoaiSan;
    private String ghiChu;
    private List<DanhSachCaDatRequest> danhSachCaDatRequests;
}
