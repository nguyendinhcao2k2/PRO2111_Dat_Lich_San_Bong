//package com.example.pro2111_dat_lich_san_bong.infrastructure.config.security;
//
//import com.example.pro2111_dat_lich_san_bong.entity.Account;
//import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * @author caodinh
// */
//
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//public class CustomAccountDetails implements UserDetails {
//
//     Account account;
//
//    @Autowired
//    private ChucVuRepository chucVuRepository;
//
//    public CustomAccountDetails(Account account) {
//        this.account = account;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(chucVuRepository.findById(account.getId()).get().getTenChucVu()));
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
