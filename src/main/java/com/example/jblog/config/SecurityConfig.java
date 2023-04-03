package com.example.jblog.config;

import com.example.jblog.filter.JwtAuthFilter;
import com.example.jblog.service.JwtService;
import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
//                .authenticationProvider(authenticationProvider())
//                .httpBasic(Customizer.withDefaults()).build();
        // .antMatchers(HttpMethod.DELETE)
        // .hasRole("ADMIN")
        // .antMatchers("/admin/**")
        // .hasAnyRole("ADMIN")
        // .antMatchers("/user/**")
        // .hasAnyRole("USER", "ADMIN")
        // .antMatchers("/login/**")
        // .anonymous()
        // .anyRequest()
        // .authenticated()
        // .and()
        // .httpBasic()
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // return http.build();
    }

}
