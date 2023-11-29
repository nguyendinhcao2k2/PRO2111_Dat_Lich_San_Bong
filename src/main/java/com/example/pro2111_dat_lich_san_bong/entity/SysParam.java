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
@Table(name = "sys_param")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysParam {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    protected String id;
    @Nationalized
    private String type;
    @Nationalized
    private String code;
    @Nationalized
    @Column(name = "chuc_nang")
    private String chucNang;
    @Nationalized
    private String name;
    @Nationalized
    private String note;
    @Nationalized
    private String value;
    @Column(name = "trang_thai")
    private Integer trangThai;

}
