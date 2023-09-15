package com.example.pro2111_dat_lich_san_bong.repository;

import com.example.pro2111_dat_lich_san_bong.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author caodinh
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
