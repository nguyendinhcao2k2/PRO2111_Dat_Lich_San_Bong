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
@Table(name = "san_bong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanBong {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ten_san_bong")
    @Nationalized
    private String tenSanBong;

    @Column(name = "gia_san")
    private Double giaSan;

    @Column(name = "id_loai_san")
    private String idLoaiSan;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
