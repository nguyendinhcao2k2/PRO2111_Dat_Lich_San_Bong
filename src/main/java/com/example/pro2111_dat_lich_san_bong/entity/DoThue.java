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

/**
 * @author caodinh
 */

@Entity
@Table(name = "do_thue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoThue {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;

    @Column(name = "ten_do_thue", length = 400)
    private String tenDoThue;

    @Column(name = "so_luong")
    private int soLuong;

    @Column(name = "don_gia")
    private double donGia;

    @Column(name = "trang_thai")
    private int trangThai;

}