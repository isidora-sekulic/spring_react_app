/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.config;

import jakarta.servlet.Filter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rs.ac.bg.fon.njt.strukturasp.security.AppUserDetailsService;
import rs.ac.bg.fon.njt.strukturasp.security.JwtAuthFilter;

/**
 *
 * @author Home PC
 */
@Configuration
public class SecurityConfig {
    
    private final JwtAuthFilter jwtFilter;
    private final AppUserDetailsService uds;

    public SecurityConfig(JwtAuthFilter jwtFilter, AppUserDetailsService uds) {
        this.jwtFilter = jwtFilter;
        this.uds = uds;
    }
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        
        http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth

            // auth endpoints
            .requestMatchers("/api/auth/**").permitAll()

            // swagger
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

            // javni GET primer
            .requestMatchers(HttpMethod.GET, "/api/studyprogram/**").permitAll()

            // sve ostalo traži JWT
            .anyRequest().authenticated()
        )
        .authenticationProvider(authenticationProvider())
        .addFilterBefore((Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
   
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider p=new DaoAuthenticationProvider((UserDetailsService) uds);
        p.setPasswordEncoder(PasswordEncoder());
        return p;
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager atAuthenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
