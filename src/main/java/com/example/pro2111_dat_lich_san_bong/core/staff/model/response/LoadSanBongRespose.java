package com.example.pro2111_dat_lich_san_bong.core.staff.model.response;

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
public class LoadSanBongRespose {
    private String idSanBong;
    private String idLoaiSan;
    private String tenSanBong;
    private Double giaSan;
    private String loaiSan;
    private List<LoadCaResponse> loadCaResponses;
}
