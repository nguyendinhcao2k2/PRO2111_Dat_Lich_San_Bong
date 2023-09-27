//package com.example.pro2111_dat_lich_san_bong.infrastructure.config.security;
//
//import com.example.pro2111_dat_lich_san_bong.core.authen.service.AccountService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
///**
// * @author caodinh
// */
//@Configuration
//@EnableWebSecurity
//public class SercurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new AccountService();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors().and().csrf().disable().authorizeHttpRequests()
//                .requestMatchers("/authentication/**").permitAll()
//                .requestMatchers("/static/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .successHandler((request, response, authentication) -> {
//                    CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
//                    HttpSession session = request.getSession();
//                    session.setAttribute("account", customAccountDetails);
//                    response.sendRedirect("/api/admin/home");
//                })
//                .failureUrl("/authentication/login?error=true")
//                .and()
//                .logout().logoutUrl("/logout")
//                .logoutSuccessUrl("/authentication/login").permitAll()
//                .invalidateHttpSession(true);
//
//
//        return http.build();
//    }
//}
