package com.example.pro2111_dat_lich_san_bong.infrastructure.config.currency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.Locale;

@Configuration
public class CurrencyConfig {
    @Bean
    public NumberFormat formatCurrencyVN() {
//        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return NumberFormat.getNumberInstance();
    }
}
