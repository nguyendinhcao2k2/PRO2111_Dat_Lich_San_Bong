package com.example.pro2111_dat_lich_san_bong.core.admin.model.response;

import com.example.pro2111_dat_lich_san_bong.entity.Ca;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

@Projection(types = {Ca.class})
public interface CaAdminReponse {

    @Value("#{target.id}")
     String getId();

    @Value("#{target.tenCa}")
     String getTenCa();

    @Value("#{target.thoiGianBatDau}")
     Time getThoiGianBatDau();

    @Value("#{target.thoiGianKetThuc}")
    Time getThoiGianKetThuc();

    @Value("#{target.giaCa}")
     Double getGiaCa();

    @Value("#{target.trangThai}")
     Integer getTrangThai();
}
