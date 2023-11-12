package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhuPhiAdminRequest {

    private String id;

    @NotNull
    @NotBlank
    private String maPhuPhi;

    @NotNull
    @NotBlank
    private String tenPhuPhi;

    @NotNull
    private Double giaPhuPhi;

    @NotNull
    private Integer trangThai;
}
