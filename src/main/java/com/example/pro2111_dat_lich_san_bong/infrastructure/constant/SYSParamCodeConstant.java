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

    //code để lấy thời gian thực hết giao dịch (cột value) trong bảng sys_param
    public static String THOI_GIAN_HET_GD = "THOI_GIAN_HET_GD";

    //code để lấy thời gian gửi thông báo trước thời gian bắt đầu ca (cột value) trong bảng sys_param
    public static String THOI_GIAN_THONG_BAO = "THOI_GIAN_THONG_BAO";

    //code để lấy thời gian đổi lịch trước ngày đến sân (cột value) trong bảng sys_param
    public static String THOI_GIAN_DOI_LICH = "THOI_GIAN_DOI_LICH";


    public static String THOI_GIAN_CHO_PHEP_DAT_LICH = "THOI_GIAN_CHO_PHEP_DAT_LICH";

    //code để lấy thời gian cho phép check in (cột value) trong bảng sys_param
    public static String THOI_GIAN_DUOC_PHEP_CHECK_IN = "THOI_GIAN_DUOC_PHEP_CHECK_IN";


    //chuyen thành danh sach
    public static List<Object> LIST_PARAM_CODES = Arrays.asList(
            PHAN_TRAM_GIA_TIEN_COC,
            SO_COMBO_DAT,
            THOI_GIAN_HET_GD,
            THOI_GIAN_THONG_BAO,
            THOI_GIAN_DOI_LICH,
            THOI_GIAN_CHO_PHEP_DAT_LICH,
            THOI_GIAN_DUOC_PHEP_CHECK_IN
    );
}
