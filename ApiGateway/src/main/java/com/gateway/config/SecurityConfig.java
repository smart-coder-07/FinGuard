package com.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.gateway.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges -> exchanges
           //     .pathMatchers("/public/**").permitAll()   // Public routes, no auth required
                .pathMatchers("/userservice/**", "/userservice/**").permitAll() // we can access without authentication
                .pathMatchers(HttpMethod.GET,"/portfolioservice/portfolio/add", "/portfolioservice/portfolio/getInvestment/{id}", "/portfolioservice/portfolio/getInvestment").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.DELETE,  "/portfolioservice/portfolio/delete/{name}").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.GET,"/portfolioservice/portfolio/update","portfolioservice/portfolio/test","/portfolioservice/portfolio/all").hasRole("ROLE_admin")
                .pathMatchers(HttpMethod.GET,"/debt/debt/test", "/debt/debt/debts", "debt/debt/user/{userId}").hasRole("ROLE_admin")
                .pathMatchers(HttpMethod.GET,"/debt/debt/add", "/debt/debt/calculate-emi", "debt/debt/total-interest", "debt/debt/generate-schedule").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.GET,"/transaction/transaction/test", "/transaction/transaction/getAll/{userId}").hasRole("ROLE_admin")
                .pathMatchers(HttpMethod.GET,"/transaction/transaction/create", "/transaction/transaction/update/{transactionId}", "delete/{transactionId}").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.DELETE,"/transaction/transaction/delete/{transactionId}").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.GET,"/budgetservice/budget/test", "/budgetservice/budget/getbybudgetid/{id}", "/budgetservice/budget/getbyuserid", "/budgetservice/budget/getbybudgetcategory/{cat}", "/budgetservice/budget/getcategory", "/budgetservice/budget/deletebybudgetid/{budgetid}", "/budgetservice/budget/update").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.DELETE,"/budgetservice/budget/delete/{category}").hasRole("ROLE_user")
              //  .pathMatchers(HttpMethod.POST,"/order-service/order").hasRole("ROLE_admin")	
              //  .pathMatchers("/order-service/loadbalance/**").hasRole("ROLE_admin") // ADMIN role only for /admin/**
              //  .pathMatchers("/order-service/feign/**").hasRole("ROLE_user")   // USER role only for /user/**
                .anyExchange().authenticated()              // All other routes require authentication
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
            .csrf().disable()  // Disable CSRF for stateless APIs
            .build();
    }
}
