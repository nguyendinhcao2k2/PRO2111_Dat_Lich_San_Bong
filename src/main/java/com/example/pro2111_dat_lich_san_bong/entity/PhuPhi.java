package com.example.pro2111_dat_lich_san_bong.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author caodinh
 */
@Entity
@Table(name = "phu_phi")
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhuPhi {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "ma_phu_phi")
    private String maPhuPhi;

    @Column(name = "ten_phu_phi")
    private String tenPhuPhi;

    @Column(name = "gia_phu_phi")
    private Double giaPhuPhi;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
