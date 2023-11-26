package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.LoaiSan;
import com.example.pro2111_dat_lich_san_bong.repository.LoaiSanRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author thepvph20110
 */
public interface LoaiSanStaffRepository extends LoaiSanRepository {

    @Query(value = """
                select ls.id,ls.mo_ta,
                ls.ten_loai_san,ls.trang_thai
                ,ls.gia_san
                from loai_san ls
                where ls.trang_thai = 0
                order by ls.gia_san asc
            """, nativeQuery = true)
    List<LoaiSan> getAllLoaiSan();
}
