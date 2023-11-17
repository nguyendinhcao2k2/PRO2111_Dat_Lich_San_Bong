package com.example.pro2111_dat_lich_san_bong.infrastructure.config.img_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value("${pathUpload_Img}")
    private String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(

                "/static/img/**")
                .addResourceLocations("file:"+path);
    }
}
