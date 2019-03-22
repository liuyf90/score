package com.example.demo;

import com.example.demo.service.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Created by liuyf on 2018/5/3.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()
                .antMatchers("/asssets/**", "/login", "/asssets-login/**")
                .permitAll()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .permitAll().and().logout().logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/error");
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

//        http.authorizeRequests()
//                .antMatchers("/asssets/**", "/login","/asssets-login/**").permitAll()
//                .antMatchers("/pool/**").hasRole("USER")
//                .antMatchers("/mytasks/**").hasRole("USER")
//                .antMatchers("/check/**").hasRole("OWNER")
//                .antMatchers("/test/**").hasRole("TEST")
//                .and()
//                .formLogin().loginPage("/login").failureUrl("/login-error")
//                .and()
//                .exceptionHandling().accessDeniedPage("/401");
//        http.logout().logoutSuccessUrl("/");
    }
}