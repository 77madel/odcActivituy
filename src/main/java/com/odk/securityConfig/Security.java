package com.odk.securityConfig;

import com.odk.Service.Interface.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class Security {

    private JwtFilter jwtFilter;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Ajouter un filtre JWT avant le filtre d'authentification par formulaire
        // http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Configurer les autorisations
        http
                .cors().and().csrf().disable()  // Désactiver CSRF pour les APIs REST
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // On utilise des tokens, pas des sessions
                .and()
                .authorizeHttpRequests( requests -> requests
                        // Autoriser l'accès à la création d'organisation et d'utilisateur sans authentification
                        .requestMatchers("/auth/**").permitAll()  // Autoriser les routes d'authentification
                        .requestMatchers("/participant/**").permitAll()  // Autoriser les routes d'authentification
                        .requestMatchers("/personnel/**").permitAll()  // Autoriser les routes d'authentification
                        .requestMatchers("/api/import/**").permitAll()  // Autoriser les routes d'authentification
                        .requestMatchers("/etape/{id}/participants/upload").permitAll()
                        .requestMatchers("/etape/**").permitAll()
                        .requestMatchers("/activite/**").permitAll()
                        .requestMatchers("/activite/enCours").permitAll()
                        .requestMatchers("/vigile/**").permitAll()
                        .requestMatchers("/critere/**").permitAll()
                        .requestMatchers("/entite/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/typeActivite/**").permitAll()
                        .requestMatchers("/utilisateur/**").permitAll()
                        .requestMatchers("/utilisateur/change-password").authenticated()
                        .requestMatchers("/reporting/**").permitAll()
                        .requestMatchers("/role/**").permitAll()
                        // Autoriser les routes d'authentification
                      // Autoriser les routes d'authentification
                        .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                );
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }*/

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
                                        .requestMatchers("/participant/**").permitAll()  // Autoriser les routes d'authentification
                                        .requestMatchers("/personnel/**").permitAll()  // Autoriser les routes d'authentification
                                        .requestMatchers("/api/import/**").permitAll()  // Autoriser les routes d'authentification
                                        .requestMatchers("/etape/{id}/participants/upload").permitAll()
                                        .requestMatchers("/etape/**").permitAll()
                                        .requestMatchers("/activite/**").permitAll()
                                        .requestMatchers("/activite/enCours").permitAll()
                                        .requestMatchers("/vigile/**").permitAll()
                                        .requestMatchers("/critere/**").permitAll()
                                        .requestMatchers("/entite/**").permitAll()
                                        .requestMatchers("/images/**").permitAll()
                                        .requestMatchers("/typeActivite/**").permitAll()
                                        .requestMatchers("/utilisateur/**").permitAll()
                                        .requestMatchers("/utilisateur/change-password").permitAll()
                                        .requestMatchers("/reporting/**").permitAll()
                                        .requestMatchers("/role/**").permitAll()
                                        .requestMatchers("/blacklist/**").permitAll()
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
