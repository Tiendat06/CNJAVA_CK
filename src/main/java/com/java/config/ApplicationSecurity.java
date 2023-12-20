package com.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .formLogin(form -> form.loginPage("/log/login").permitAll())
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers("/css/**","/font/**","/img/**","/js/**").permitAll()
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/log/logout") // Specify the logout URL
                        .logoutSuccessUrl("/")     // Redirect to the home page after logout
                        .invalidateHttpSession(true) // Invalidate the HTTP session
                        .deleteCookies("JSESSIONID").permitAll()    ) // Remove the session cookie
                .build();

    }
}
