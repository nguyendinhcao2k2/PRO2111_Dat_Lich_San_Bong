package com.example.pro2111_dat_lich_san_bong.infrastructure.config.security;

import com.example.pro2111_dat_lich_san_bong.core.authen.service.AccountService;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.UserService;
import com.example.pro2111_dat_lich_san_bong.entity.ChucVu;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.RoleConstant;
import com.example.pro2111_dat_lich_san_bong.infrastructure.constant.SessionConstant;
import com.example.pro2111_dat_lich_san_bong.repository.ChucVuRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

/**
 * @author caodinh
 */
@Configuration
@EnableWebSecurity
public class SercurityConfig {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Value("${server.port}")
    private String port;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountService();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeHttpRequests()
                .requestMatchers("/authentication/**", "/error").permitAll()
                .requestMatchers("/chi-tiet/hoa-don", "/api/v1/trang-chu").permitAll()
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/api/v1/profile").hasAnyAuthority(RoleConstant.roleAdmin, RoleConstant.roleStaff, RoleConstant.roleUser)
                .requestMatchers("/api/v1/admin/**").hasAuthority(RoleConstant.roleAdmin)
                .requestMatchers("/api/v1/staff/**").hasAuthority(RoleConstant.roleStaff)
                .requestMatchers("/api/v1/user/**").hasAuthority(RoleConstant.roleUser)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
                    ChucVu chucVu = chucVuRepository.findById(customAccountDetails.getAccount().getIdChucVu()).get();
                    if (processAccount(chucVu.getTenChucVu(), response) == false) {
                        HttpSession session = request.getSession();
                        session.setAttribute(SessionConstant.sessionUser, customAccountDetails.getAccount());
                        redirectUrl(chucVu.getTenChucVu(), response);
                    }
                })
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback")
                .and()
                .successHandler((request, response, authentication) -> {
                    userService.processOAuthPostLogin(response);
                })
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/authentication/home-login").permitAll()
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/authentication/403")
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("http://localhost:" + port + "/authentication/home-login");
                });
        return http.build();
    }

    public void redirectUrl(String role, HttpServletResponse response) throws IOException {
        if (role.equalsIgnoreCase(RoleConstant.roleAdmin)) {
            response.sendRedirect("/api/v1/admin/manage/list-manage");
//            response.sendRedirect("/test/thong-ke");
        } else if (role.equalsIgnoreCase(RoleConstant.roleStaff)) {
            //auto source /api/v1/staff/all
            response.sendRedirect("api/v1/staff/account/display");
        } else if (role.equalsIgnoreCase(RoleConstant.roleUser)) {
            response.sendRedirect("/api/v1/trang-chu");
        }
    }

    public boolean processAccount(String role, HttpServletResponse response) throws IOException {
        String obj = (String) session.getAttribute(SessionConstant.sessionRole);
        if (obj != null) {
            if (!role.equals(obj)) {
                if (obj.equals(RoleConstant.roleUser)) {
                    session.invalidate();
                    response.sendRedirect("/authentication/user-login?error=true");
                    return true;
                } else if (obj.equals(RoleConstant.roleAdmin)) {
                    session.invalidate();
                    response.sendRedirect("/authentication/admin-login?error=true");
                    return true;
                } else if (obj.equals(RoleConstant.roleStaff)) {
                    session.invalidate();
                    response.sendRedirect("/authentication/staff-login?error=true");
                    return true;
                }
            }
        }
        return false;
    }

}
