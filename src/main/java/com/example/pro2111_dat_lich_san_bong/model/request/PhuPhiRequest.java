package com.example.pro2111_dat_lich_san_bong.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuPhiRequest {

    @NotBlank(message = "Không được để trống tên phụ phí")
    private String tenPhuPhi;

    @NotNull(message = "Không được để trống giá phụ phí")
    private Double giaPhuPhi;

    @NotNull(message = "Không được để trống trạng thái")
    private Integer trangThai;
}
