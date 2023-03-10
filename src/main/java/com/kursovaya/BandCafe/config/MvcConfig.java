package com.kursovaya.BandCafe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imgprof/**")
                .addResourceLocations("file:/" + uploadPath + "/ProfileImages/");
        registry.addResourceHandler("/imgcover/**")
                .addResourceLocations("file:/" + uploadPath + "/CoverImages/");
        registry.addResourceHandler("/imgorder/**")
                .addResourceLocations("file:/" + uploadPath + "/MerchImages/");
        registry.addResourceHandler("/forumpics/**")
                .addResourceLocations("file:/" + uploadPath + "/ForumIcons/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("file:/" + uploadPath + "/CSS/");
        registry.addResourceHandler("/bacs/**")
                .addResourceLocations("file:/" + uploadPath + "/MainImages/");

    }
}
