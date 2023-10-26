package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author caodinh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThongTinNguoiDatRequest {

    private String hoVaTen;
    private String soDienThoai;
    private String email;
    private String ghiChu;
    private List<ThongTinLichDatRequest> thongTinLichDatRequests;

}
