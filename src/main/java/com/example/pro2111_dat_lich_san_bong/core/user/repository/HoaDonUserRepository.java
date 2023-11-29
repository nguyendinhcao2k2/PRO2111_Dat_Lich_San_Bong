package com.example.pro2111_dat_lich_san_bong.core.user.repository;

import com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoaDonUserResponse;
import com.example.pro2111_dat_lich_san_bong.entity.HoaDon;
import com.example.pro2111_dat_lich_san_bong.repository.HoaDonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author thepvph20110
 */
public interface HoaDonUserRepository extends HoaDonRepository {
    @Query(value = """
                    select new com.example.pro2111_dat_lich_san_bong.core.user.model.response.HoaDonUserResponse(hd.id,c.tenCa,
                    (select ls.tenLoaiSan from LoaiSan ls where ls.id = tgdl.idLoaiSan)as LoaiSan,
                    tgdl.ngayDat,((select ls1.giaSan  from LoaiSan ls1 where ls1.id = tgdl.idLoaiSan) + c.giaCa)  as TienSan)
                    from Ca c
                    		join ThoiGianDatLich tgdl on c.id  = tgdl.idCa
                    		join HoaDon hd on tgdl .idHoaDon  = hd.id
                    		join  Account a  on hd.idAccount  = a.id
                    where hd.trangThai  = 0 and a.id = :idAccountHT
                    order by tgdl.ngayDat  asc
            """)
    List<HoaDonUserResponse> findHoaDonByChoXacNhanAndAccHT(@Param("idAccountHT") String idAccount);

    Optional<HoaDon> findById(String idHoaDon);

    List findAllByIdAccountAndTrangThai(String idAccount,Integer trangThai);

    void deleteHoaDonById(String idHoaDon);
}
