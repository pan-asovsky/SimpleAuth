package dev.panasovsky.module.auth.config;

import dev.panasovsky.module.auth.components.JwtFilter;
import dev.panasovsky.module.auth.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserService userService;

    
    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
//                .and().authorizeHttpRequests(
//                        auth -> auth
//                                .antMatchers("/api/auth/login", "/api/auth/token").permitAll()
//                                .anyRequest().authenticated()
//                                .and().addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class))
//                .build();
    }

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected DaoAuthenticationProvider authenticationProvider() {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(this.userService);
        return provider;
    }

}