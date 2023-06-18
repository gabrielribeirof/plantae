/*
 * Projeto da disciplina de Introducao a Tecnologia Java
 * Membros: Bruno Augusto Furquim, Gabriel Ribeiro Ferreira, Karolyne Domiciano Marques, Willian Yoshio Murayama
 */
package com.plantae.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author willm
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/cadastro", "/css/**").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
