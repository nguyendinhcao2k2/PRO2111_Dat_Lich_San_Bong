package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lich_su_doi_lich")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichSuDoiLich {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "id_san_ca-moi", columnDefinition = "NVARCHAR(255)")
    private String idSanCaMoi;

    @Column(name = "id_san_ca_cu", columnDefinition = "NVARCHAR(255)")
    private String idSanCaCu;

    @Column(name = "id_nguoi_dung")
    private String idNguoiDung;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
