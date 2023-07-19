package com.whatsapp.whatsappclone.Config;


import com.whatsapp.whatsappclone.CustomMapper.UserToUserDto;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.JwtAuthFilter;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppConfig {


    @Autowired
    private JwtAuthFilter authFilter;

    @Autowired
    private UserServiceDetail userServiceDetail;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration websocketConfig = new CorsConfiguration();
        websocketConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        websocketConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        websocketConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        CorsConfiguration apiConfig = new CorsConfiguration();
        apiConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        apiConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        apiConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Allow WebSocket connections
        source.registerCorsConfiguration("http://localhost:3000", websocketConfig);
        // Allow other API requests
        source.registerCorsConfiguration("/api/v1/**", apiConfig);

        return new CorsFilter(source);
    }


    @Bean
    public UserToUserDto userToUserDto() {
        return new UserToUserDto();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
