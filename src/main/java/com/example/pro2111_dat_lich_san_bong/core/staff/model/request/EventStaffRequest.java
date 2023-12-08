package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author caodinh
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventStaffRequest {
    private String idLoaiSan;

    private String ngayBatDau;
    private String ngayKetThuc;

}
