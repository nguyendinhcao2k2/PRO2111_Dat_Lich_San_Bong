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
@Table(name = "hang_khach_hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HangKhachHang {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ten_hang")
    @Nationalized
    private String tenHang;

    @Column(name = "diem_tich_luy/")
    private Integer diemTichLuy;

    @Column(name = "trang_thai")
    private Integer trang_thai;

}
