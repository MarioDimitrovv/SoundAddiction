package com.soundaddiction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.soundaddiction")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/static/img/");
        registry.addResourceHandler("/pdfs/**").addResourceLocations("/static/pdf/");
        registry.addResourceHandler("/html/**").addResourceLocations("/static/html/");
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
    }

    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/jsp/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

}
