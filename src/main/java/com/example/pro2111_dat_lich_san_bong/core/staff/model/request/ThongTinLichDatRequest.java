package com.example.pro2111_dat_lich_san_bong.core.staff.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author caodinh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThongTinLichDatRequest {

    @NotNull
    @NotEmpty
    private String id;
    @NotNull
    @NotEmpty
    private String ngay;
    @NotNull
    @NotEmpty
    private String price;
    @NotNull
    @NotEmpty
    private String tenCa;
    @NotNull
    @NotEmpty
    private String tenSan;
    @NotNull
    @NotEmpty
    private String time;

}
