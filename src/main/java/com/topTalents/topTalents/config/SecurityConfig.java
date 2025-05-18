package com.topTalents.topTalents.config;

import com.topTalents.topTalents.security.CustomUserDetailsService;
import com.topTalents.topTalents.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // TALENTS
                        .requestMatchers(HttpMethod.GET, "/api/scouts/**")
                        .hasAnyRole("TALENT", "SCOUT", "ADMIN")

                        .requestMatchers("/api/talents/**",
                                "/api/match-calendar/**",
                                "/api/match-history/**")
                        .hasAnyRole("TALENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/teams/**")
                        .hasAnyRole("TALENT", "ADMIN")

                        // SCOUTS
                        .requestMatchers("/api/scouts/**")
                        .hasAnyRole("SCOUT", "ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/api/match-calendar/**",
                                "/api/match-history/**",
                                "/api/teams/**")
                        .hasAnyRole("SCOUT", "ADMIN")

                        .anyRequest().hasRole("ADMIN")
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
