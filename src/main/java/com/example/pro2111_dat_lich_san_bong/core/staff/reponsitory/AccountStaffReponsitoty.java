package com.example.pro2111_dat_lich_san_bong.core.staff.reponsitory;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.repository.AccountRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountStaffReponsitoty extends AccountRepository {

    Account findAccountById(String id);

    @Query(value = """
            SELECT a FROM Account a join ChucVu cv on a.idChucVu = cv.id WHERE  cv.tenChucVu like :roleNV
            """)
        //ROLE_STAFF role nhan viên
    List<Account> getAccountByChucVu(@Param(value = "roleNV") String role);
}
