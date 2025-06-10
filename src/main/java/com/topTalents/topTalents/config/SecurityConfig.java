package com.topTalents.topTalents.config;

import com.topTalents.topTalents.security.CustomUserDetailsService;
import com.topTalents.topTalents.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            DaoAuthenticationProvider authProvider
    ) throws Exception {
        http
                .authenticationProvider(authProvider)
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints:
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").permitAll()

                        // TALENTS:
                        // 1) TALENT, SCOUT, or ADMIN may GET any talent:
                        .requestMatchers(HttpMethod.GET, "/api/talents/**")
                        .hasAnyRole("SCOUT","TALENT","ADMIN")
                        // 2) Only TALENT or ADMIN may write (POST/PUT/DELETE) under /api/talents/**:
                        .requestMatchers("/api/talents/**")
                        .hasAnyRole("TALENT","ADMIN")

                        // MATCH-CALENDAR / MATCH-HISTORY:
                        // 3) TALENT, SCOUT, or ADMIN may GET match-calendar/history:
                        .requestMatchers(HttpMethod.GET, "/api/match-calendars/**", "/api/match-history/**")
                        .hasAnyRole("SCOUT","TALENT","ADMIN")
                        // 4) Only TALENT or ADMIN may write under /api/match-calendars/**, /api/match-history/**:
                        .requestMatchers("/api/match-calendars/**", "/api/match-history/**")
                        .hasAnyRole("TALENT","ADMIN")

                        // TEAMS:
                        // 5) TALENT, SCOUT, or ADMIN may GET /api/teams/**:
                        .requestMatchers(HttpMethod.GET, "/api/teams/**")
                        .hasAnyRole("SCOUT","TALENT","ADMIN")
                        // 6) Only ADMIN may write under /api/teams/**:
                        .requestMatchers("/api/teams/**")
                        .hasRole("ADMIN")

                        // SCOUTS:
                        // 7a) TALENT, SCOUT, or ADMIN may GET /api/scouts/**:
                        .requestMatchers(HttpMethod.GET, "/api/scouts/**")
                        .hasAnyRole("SCOUT","TALENT","ADMIN")
                        // 7b) Only SCOUT or ADMIN may write (POST/PUT/DELETE) under /api/scouts/**:
                        .requestMatchers("/api/scouts/**")
                        .hasAnyRole("SCOUT","ADMIN")

                        // Everything else requires ADMIN:
                        .anyRequest().hasRole("ADMIN")
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
