package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanBongAdminCreateRequets {

    @NotNull(message = "Not null")
    @NotBlank
    private String tenSanBong;

    @NotNull(message = "Not null")
    private Double giaSan;

    @NotNull(message = "Not null")
    @NotBlank
    private String idLoaiSan;

}
