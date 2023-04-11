package com.example.jblog.config;

import com.example.jblog.filter.JwtAuthFilter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;
    @Value("${frontend.host.url}")
    private String FRONTEND_URL;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();
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

//    @EventListener
//    public void handleContextRefresh(ContextRefreshedEvent event) {
//        ApplicationContext applicationContext = event.getApplicationContext();
//        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
//                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
//        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
//                .getHandlerMethods();
//        map.forEach((key, value) -> log.info("{} {}", key, value));
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin(FRONTEND_URL);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
