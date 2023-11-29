package com.example.pro2111_dat_lich_san_bong.core.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thepvph20110
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DanhSachCaDatRequest {

    private String idCa;
    private String ngayDa;
    private String giaSan;
}
