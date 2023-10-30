package com.example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(request -> request
                        .requestMatchers("/cashcards/**")
                        .hasRole("CARD-OWNER")) // enable RBAC by replacing .authenticated() call with .hasRole call
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService cardUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails vrish = users.username("vrish")
                .password(passwordEncoder.encode("test123"))
                .roles("CARD-OWNER")
                .build();
        UserDetails kini = users.username("kini")
                .password(passwordEncoder.encode("test456"))
                .roles("CARD-OWNER")
                .build();
        UserDetails mathew = users.username("mathew")
                .password(passwordEncoder.encode("test789"))
                .roles("CARD-OWNER")
                .build();
        UserDetails raj = users.username("raju")
                .password(passwordEncoder.encode("dummy123"))
                .roles("NON-OWNER")
                .build();
        return new InMemoryUserDetailsManager(vrish, kini, mathew, raj);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
