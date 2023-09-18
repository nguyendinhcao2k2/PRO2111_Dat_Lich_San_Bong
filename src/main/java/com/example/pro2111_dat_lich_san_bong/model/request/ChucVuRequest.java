package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChucVuRequest {

    @NotBlank(message = "Không được để trống tên chức vụ!")
    private String tenChucVu;

    @NotNull(message = "Không được để trống trạng thái")
    private Integer trangThai;
}
