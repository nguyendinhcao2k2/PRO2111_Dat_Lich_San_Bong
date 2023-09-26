package com.example.pro2111_dat_lich_san_bong.core.authen.repo;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author caodinh
 */
@Repository("AuthenAccountRepository")
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("select acc from Account acc where acc.taiKhoan =:userName")
    Account findByUsername(@Param("userName") String username);

}
