package com.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

import com.gateway.filter.JwtAuthenticationFilter;

//@Configuration
public class WebFilterConfig {

    @Bean
    public WebFilter headerModificationFilter() {
        return new JwtAuthenticationFilter();
    }
}