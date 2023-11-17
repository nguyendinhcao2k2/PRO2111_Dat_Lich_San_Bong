package com.example.pro2111_dat_lich_san_bong.infrastructure.constant;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

/**
 * @author thepvph20110
 */
public class SYSParamCodeConstant {

    //code trong bảng để lấy số phần trăm (cột value) trong bảng sys_param
    public static String PHAN_TRAM_GIA_TIEN_COC = "PHAN_TRAM_GIA_TIEN_COC";

    public static String SO_COMBO_DAT = "SO_COMBO_DAT";


    //chuyen thành danh sach
    public static List<Object> LIST_PARAM_CODES = Arrays.asList(
            PHAN_TRAM_GIA_TIEN_COC,
            SO_COMBO_DAT
    );
}
