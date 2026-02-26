package com.kenfi.gestionpersonnel.config;

import com.kenfi.gestionpersonnel.service.ServiceUtilisateur;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecurite {

    private final ServiceUtilisateur serviceUtilisateur;

    public ConfigSecurite(ServiceUtilisateur serviceUtilisateur) {
        this.serviceUtilisateur = serviceUtilisateur;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(serviceUtilisateur);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/rh/**").hasAnyRole("ADMIN", "RH")
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "RH", "MANAGER")
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/connexion")
                .defaultSuccessUrl("/tableau-de-bord", true)
                .permitAll()
        )
        .logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/deconnexion"))
                .logoutSuccessUrl("/connexion?deconnexion")
                .permitAll()
        );

        return http.build();
    }
}
