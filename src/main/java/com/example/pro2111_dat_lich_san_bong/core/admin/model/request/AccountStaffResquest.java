package com.example.pro2111_dat_lich_san_bong.core.admin.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
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
public class AccountStaffResquest {

    @NotNull(message = "Not null")
    @NotBlank
    @Email(message = "Email Error")
    private String email;

    @NotNull(message = "Not null")
    @NotBlank
    private String taiKhoan;

    @NotNull(message = "Not null")
    @NotBlank
    private String matKhau;

    @NotNull(message = "Not null")
    @NotBlank
    private String soDienThoai;

    @NotNull(message = "Not null")
    @NotBlank
    private String displayName;

}
