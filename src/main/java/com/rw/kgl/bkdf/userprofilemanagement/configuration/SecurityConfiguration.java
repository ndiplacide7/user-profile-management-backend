package com.rw.kgl.bkdf.userprofilemanagement.configuration;

import com.rw.kgl.bkdf.userprofilemanagement.filter.JwtAccessDeniedHandler;
import com.rw.kgl.bkdf.userprofilemanagement.filter.JwtAuthenticationEntryPoint;
import com.rw.kgl.bkdf.userprofilemanagement.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.rw.kgl.bkdf.userprofilemanagement.constant.SecurityConstant.PUBLIC_URLS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private JwtAuthorizationFilter jwtAuthorizationFilter;
  private JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private UserDetailsService userDetailsService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public SecurityConfiguration(
      JwtAuthorizationFilter jwtAuthorizationFilter,
      JwtAccessDeniedHandler jwtAccessDeniedHandler,
      JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
      UserDetailsService userDetailsService,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .requestMatchers(PUBLIC_URLS)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
