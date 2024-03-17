package com.uniovi.sdi2324entrega181;

import com.uniovi.sdi2324entrega181.handlers.CustomLoginErrorHandler;
import com.uniovi.sdi2324entrega181.handlers.CustomLoginSuccessHandler;
import com.uniovi.sdi2324entrega181.handlers.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/css/**", "/images/**", "/script/**", "/", "/signup", "/login/**").permitAll()
                    .antMatchers("/user/list").authenticated()
                    .antMatchers("/user/administratorList").hasRole("ADMIN")
                    .antMatchers("/user/deleteAll").hasRole("ADMIN")
                    .antMatchers("/post/updateState/**").hasRole("ADMIN")
                    .antMatchers("/recommendation/*").authenticated()
                    .antMatchers("/post/*").authenticated()
                    .antMatchers("/home").authenticated()
                    .antMatchers("/friendship/list").authenticated()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                .defaultSuccessUrl("/user/sendFriendshipList")
                    .permitAll()
                .successHandler(myAuthenticationSuccessHandler())
                .failureHandler(customLoginErrorHandler())
                .and()
                .logout()
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler()) // Configuración del LogoutSuccessHandler personalizado
                .and();
    }


    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customLoginErrorHandler() {
        return new CustomLoginErrorHandler();
    }


}