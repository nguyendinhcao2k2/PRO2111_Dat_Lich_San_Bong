package com.example.pro2111_dat_lich_san_bong.core.schedule.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author thepvph20110
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SanCaJobRequest {

    private String idCa;
    private Integer trangThai;
    private String ngayTao;
}
