package com.example.userservice.security;

import com.example.userservice.security.jwt.AuthEntryPointJwt;
import com.example.userservice.security.jwt.AuthTokenFilter;
import com.example.userservice.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 *  Glavna konfiguracijska klasa za Spring Security u vašoj aplikaciji.
 * 
 *  Omogućava sigurnost na nivou metoda (@EnableMethodSecurity) i definiše
 * 
 *  sigurnosni filter lanac za HTTP zahtjeve.
 * 
 */

@Configuration
@EnableMethodSecurity // Omogućava korištenje @PreAuthorize i @PostAuthorize anotacija za autorizaciju na nivou metoda
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService; // Servis za učitavanje korisničkih detalja iz baze

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Tačka ulaza za rukovanje neautorizovanim zahtjevima (HTTP 401)

    /**
     * Definiše Bean za JWT autentifikacioni filter.
     * Ovaj filter se koristi za parsiranje i validaciju JWT tokena iz HTTP zaglavlja.
     * @return instanca AuthTokenFilter-a.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Konfiguriše DaoAuthenticationProvider.
     * Ovo je srž Spring Security autentifikacije koja koristi
     * UserDetailsServiceImpl za pronalaženje korisničkih podataka
     * i PasswordEncoder za provjeru heširane lozinke.
     * Dodatno, postavlja 'hideUserNotFoundExceptions' na false kako bi
     * omogućio bacanje UsernameNotFoundException kada korisničko ime ne postoji.
     * @return instanca DaoAuthenticationProvider-a.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Postavlja servis za učitavanje korisničkih detalja
        authProvider.setPasswordEncoder(passwordEncoder()); // Postavlja enkoder lozinki
        authProvider.setHideUserNotFoundExceptions(false); // <--- KLJUČNA PROMJENA OVDJE
        return authProvider;
    }

    /**
     * Expose-uje AuthenticationManager Bean.
     * AuthenticationManager je odgovoran za proces autentifikacije korisnika.
     * @param authConfig Konfiguracija autentifikacije.
     * @return instanca AuthenticationManager-a.
     * @throws Exception u slučaju greške.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Definiše PasswordEncoder Bean.
     * Koristi BCryptPasswordEncoder za sigurno heširanje lozinki.
     * @return instanca BCryptPasswordEncoder-a.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Konfiguriše Spring Security filter lanac.
     * Definiše pravila pristupa, upravljanje sesijama i dodaje prilagođene filtere.
     * @param http HttpSecurity objekat za konfiguraciju.
     * @return izgrađeni SecurityFilterChain.
     * @throws Exception u slučaju greške.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Onemogući CSRF jer je nepotreban za stateless API-je sa JWT-om
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Rukovanje neautorizovanim pristupom
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sesije
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Javne autentifikacione rute
             .requestMatchers("/api/test/**").authenticated() // Zahtijeva autentifikaciju za test rute
            
                // Rute za administratore
              //  .requestMatchers("/api/test/**").permitAll() // Javne test rute
                .anyRequest().authenticated() // Sve ostalo zahtijeva autentifikaciju
            );

        http.authenticationProvider(authenticationProvider()); // Registruj AuthenticationProvider
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Dodaj JWT filter

        return http.build(); // Vrati konfigurisan filter lanac
    }
}
