package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LichSuChatRequest {

    @NotBlank(message = "Không được để trống!")
    private String noiDung;

    @NotNull(message = "Không được để trống!")
    private Timestamp thoiGianTao;

    @NotBlank(message = "Không được để trống!")
    private String idAccount;

}
