package dev.panasovsky.module.auth;

import dev.panasovsky.module.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/addrole").permitAll()
                .antMatchers("/adduser").permitAll()
                .antMatchers("/admin").hasAnyRole("admin")
                .anyRequest().authenticated().and().httpBasic();
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