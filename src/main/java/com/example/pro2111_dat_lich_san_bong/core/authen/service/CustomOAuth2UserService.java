package com.example.pro2111_dat_lich_san_bong.core.authen.service;

import com.example.pro2111_dat_lich_san_bong.core.authen.repo.AccountRepository;
import com.example.pro2111_dat_lich_san_bong.entity.Account;
import com.example.pro2111_dat_lich_san_bong.entity.ViTien;
import com.example.pro2111_dat_lich_san_bong.infrastructure.config.security.CustomOAuth2User;
import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
import com.example.pro2111_dat_lich_san_bong.repository.ViTienRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author caodinh
 */

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private ViTienRepository viTienRepository;

    @Autowired
    private HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        CustomOAuth2User customUser = new CustomOAuth2User(user);
        Account existAccount = accountRepository.getAccountByUsername(customUser.getEmail());
        Account newAccount = new Account();
        if (existAccount == null) {
            newAccount.setEmail(customUser.getEmail());
            newAccount.setDisplayName(customUser.getName());
            newAccount.setImage(customUser.getPicture());
            newAccount.setIdChucVu(chucVuRepository.findByTenChucVu("ROLE_USER").getId());
            newAccount.setTrangThai(0);
            ViTien viTien = viTienRepository.save(new ViTien(null, "$12$7gE1Gmxw86zKOsv1HE6EWu5fADdikTRzbAYrdeHNYjMwso9G3cko2", Timestamp.valueOf("2023-09-17 10:09:00"), Double.valueOf(0), "VND", 0, null));
            newAccount.setIdViTien(viTien.getId());
            customUser.setRole(chucVuRepository.findById(accountRepository.save(newAccount).getIdChucVu()).get().getTenChucVu());
            session.setAttribute("info", newAccount);
        } else {
            existAccount.setEmail(customUser.getEmail());
            existAccount.setDisplayName(customUser.getName());
            existAccount.setImage(customUser.getPicture());
            accountRepository.save(existAccount);
            customUser.setRole(chucVuRepository.findById(existAccount.getIdChucVu()).get().getTenChucVu());
            session.setAttribute("info", existAccount);
        }
        return customUser;
    }

}
