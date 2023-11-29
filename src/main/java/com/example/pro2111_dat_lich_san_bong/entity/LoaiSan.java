package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

/**
 * @author caodinh
 */

@Entity
@Table(name = "loai_san")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoaiSan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ten_loai_san")
    @Nationalized
    private String tenLoaiSan;

    @Column(name = "mo_ta")
    @Nationalized
    private String moTa;

    @Column(name = "gia_san")
    private Double giaSan;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
