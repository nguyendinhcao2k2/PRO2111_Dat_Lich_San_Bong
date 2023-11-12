package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhuPhiAdminResponse {

    private String id;

    private String maPhuPhi;

    private String tenPhuPhi;

    private Double giaPhuPhi;

    private Integer trangThai;
}
