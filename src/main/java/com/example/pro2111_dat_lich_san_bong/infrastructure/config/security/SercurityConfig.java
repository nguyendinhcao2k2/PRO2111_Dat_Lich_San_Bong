package com.example.pro2111_dat_lich_san_bong.infrastructure.config.security;

import com.example.pro2111_dat_lich_san_bong.core.authen.service.AccountService;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.CustomOAuth2UserService;
import com.example.pro2111_dat_lich_san_bong.core.authen.service.UserService;
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
    private UserService userService;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountService();
    }

    @Value("${server.port}")
    private String port;

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
//                .requestMatchers("/authentication/**").permitAll()
//                .requestMatchers("/static/**").permitAll()
//                .requestMatchers("/api/v1/admin/**").hasAuthority(RoleConstant.roleAdmin)
//                .requestMatchers("/api/v1/staff/**").hasAuthority(RoleConstant.roleStaff)
//                .requestMatchers("/api/v1/user/**").hasAuthority(RoleConstant.roleUser)
//                .anyRequest().authenticated()
                //set tạm
                .anyRequest().permitAll()
                //set tạm
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
                    HttpSession session = request.getSession();
                    session.setAttribute(SessionConstant.sessionUser, customAccountDetails.getAccount());
                    redirectUrl(chucVuRepository.findById(customAccountDetails.getAccount().getIdChucVu()).get().getTenChucVu(), response);
                })
                .and()
                .oauth2Login()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback")
                .and()
                .successHandler((request, response, authentication) -> {
                    userService.processOAuthPostLogin(response);
                })
                .failureUrl("/authentication/login?error=true")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/authentication/login").permitAll()
                .invalidateHttpSession(true);
        return http.build();
    }

    public void redirectUrl(String role, HttpServletResponse response) throws IOException {
        if (role.equalsIgnoreCase(RoleConstant.roleAdmin)) {
            response.sendRedirect("/api/v1/admin/all");
        } else if (role.equalsIgnoreCase(RoleConstant.roleStaff)) {
            response.sendRedirect("/api/v1/staff/all");
        } else if (role.equalsIgnoreCase(RoleConstant.roleUser)) {
            response.sendRedirect("/api/v1/user/all");
        }
    }

}
