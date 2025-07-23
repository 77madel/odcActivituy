package com.odk.securityConfig;

import com.odk.Service.Interface.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class Security {

    private JwtFilter jwtFilter;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .cors().and()
                        .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers("/auth/**").permitAll()  // Autoriser les routes d'authentification
/*
                                        .requestMatchers("/participant/**").hasAnyRole("PERSONNEL", "SUPERADMIN")  // Autoriser les routes d'authentification
*/
                                        //.requestMatchers("/participant/**").permitAll() // Autoriser les routes d'authentification
/*
                                        .requestMatchers("/utilisateur/{id}").hasAnyRole("PERSONNEL","SUPERADMIN")  // Autoriser les routes d'authentification
*/
/*
                                        .requestMatchers("/etape/{id}/participants/upload").hasRole("PERSONNEL")
*/
                                        //.requestMatchers("/etape/**").permitAll()
                                        //.requestMatchers("/liste/**").permitAll()
                                        //.requestMatchers(GET,"/etape/**").hasAnyRole("PERSONNEL","SUPERADMIN")
                                       // .requestMatchers("/activite/**").permitAll()
                                        /*.requestMatchers("/activite/**").hasAnyRole("PERSONNEL","SUPERADMIN")*/
/*
                                        .requestMatchers("/salle/**").hasAnyRole("PERSONNEL","SUPERADMIN")
*/
                                        //.requestMatchers("/salle/**").permitAll()
                                        //.requestMatchers(GET,"/activite/**").hasAnyRole("PERSONNEL","SUPERADMIN")
/*
                                        .requestMatchers("/activite/enCours").hasRole("SUPERADMIN")
*/
                                        //.requestMatchers("/critere/**").permitAll()
                                        //.requestMatchers(GET,"/critere/**").hasAnyRole("PERSONNEL","SUPERADMIN")
/*
                                        .requestMatchers(HttpMethod.POST,"/entite/**").hasRole("SUPERADMIN")
*/
                                        //.requestMatchers("/entite/**").permitAll()
/*
                                        .requestMatchers(GET,"/entite/**").hasAnyRole("SUPERADMIN","PERSONNEL")
*/
                                        .requestMatchers("/images/**").permitAll()
/*
                                        .requestMatchers("/typeActivite/**").hasAnyRole("PERSONNEL","SUPERADMIN")
*/
                                       // .requestMatchers("/typeActivite/**").permitAll()
/*
                                        .requestMatchers(GET,"/typeActivite/by-entite/**").hasAnyRole("SUPERADMIN","PERSONNEL")
*/
/*
                                        .requestMatchers("/utilisateur/**").hasRole("SUPERADMIN")
*/
                                       // .requestMatchers("/utilisateur/**").permitAll()
                                        .requestMatchers("/utilisateur/modifierMotDePasse").authenticated()
                                       // .requestMatchers("/reporting/**").permitAll()
                                        /*.requestMatchers("/reporting/**").authenticated()*/
                                        .requestMatchers("/role/**").hasRole("SUPERADMIN")
                                        //.requestMatchers("/role/**").permitAll()
                                        /*.requestMatchers("/blacklist/**").hasAnyRole("PERSONNEL","SUPERADMIN")*/
                                       // .requestMatchers("/blacklist/**").permitAll()
                                        //.requestMatchers(GET,"/blacklist/**").hasAnyRole("SUPERADMIN","PERSONNEL")
                                        .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
