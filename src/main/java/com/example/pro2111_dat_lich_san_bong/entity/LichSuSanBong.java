package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "lich_su_san_bong")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LichSuSanBong {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(name = "ngay_thuc_hien")
    private LocalDateTime ngayThucHien;
    @Column(name = "trang_thai")
    private Integer trangThai;
}
