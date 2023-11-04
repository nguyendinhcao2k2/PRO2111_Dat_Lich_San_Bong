package com.example.pro2111_dat_lich_san_bong.core.admin.reposiotry;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStaffRepository extends AccountRepository {

    @Query("""
    SELECT ac from Account ac join ChucVu cv on ac.idChucVu = cv.id where cv.tenChucVu = "ROLE_STAFF"
""")
    Page<Account> getAllByStaff(Pageable pageable);
}
