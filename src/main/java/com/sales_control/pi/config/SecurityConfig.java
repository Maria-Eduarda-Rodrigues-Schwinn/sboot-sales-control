package com.sales_control.pi.config;

import static com.sales_control.pi.enumeration.UserTypeEnum.ADMIN;
import static com.sales_control.pi.enumeration.UserTypeEnum.EMPLOYEE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.sales_control.pi.util.Md5PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Md5PasswordEncoderUtil();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/auth/**")
                    .permitAll()
                    .requestMatchers(
                        "/login.html",
                        "/main-menu.html",
                        "/register-product.html",
                        "/register-sale.html",
                        "/edit-product.html",
                        "/sales-report.html")
                    .permitAll()
                    .requestMatchers("/css/**", "/js/**", "/images/**")
                    .permitAll()
                    .requestMatchers("/products/**", "/sales/**")
                    .hasAnyAuthority(ADMIN.name(), EMPLOYEE.name())
                    .requestMatchers("/reports/**")
                    .hasAuthority(ADMIN.name())
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
